package ma.ismagi.orderservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import ma.ismagi.orderservice.dto.OrderLineItemDto;
import ma.ismagi.orderservice.dto.OrderRequestDto;
import ma.ismagi.orderservice.dto.OrderResponseDto;
import ma.ismagi.orderservice.entity.Invoice;
import ma.ismagi.orderservice.entity.Order;
import ma.ismagi.orderservice.entity.OrderLineItem;
import ma.ismagi.orderservice.feignclient.CustomerClient;
import ma.ismagi.orderservice.feignclient.InventoryClient;
import ma.ismagi.orderservice.model.Status;
import ma.ismagi.orderservice.repository.OrderLineItemRepository;
import ma.ismagi.orderservice.repository.OrderRepository;
import ma.ismagi.orderservice.util.InvoiceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** OrderService */
@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final CustomerClient customerClient;
  private final InventoryClient inventoryClient;
  private final InvoiceService invoiceService;
  private final OrderLineItemRepository orderLineItemRepository;
  private final InvoiceGenerator invoiceGenerator;

  @Transactional(readOnly = true)
  public List<Order> getCustomerHistory(UUID customerId) {
    return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
  }

  @Transactional(readOnly = true)
  public Map<String, BigDecimal> getSalesReport(int year, Integer month, LocalDateTime day) {
    Map<String, BigDecimal> report = new HashMap<>();

    if (day != null) report.put("daily", orderRepository.calculateDailyTotal(day));

    if (month != null) report.put("monthly", orderRepository.calculateMonthlyTotal(month, year));

    report.put("yearly", orderRepository.calculateYearlyTotal(year));
    return report;
  }

  @Transactional
  public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
    validateSale(orderRequestDto);

    BigDecimal total = calculateTotal(orderRequestDto.items());

    Order order =
        Order.builder()
            .customerId(orderRequestDto.customerId())
            .status(Status.PENDING)
            .total(total)
            .build();

    List<OrderLineItem> items = mapItems(orderRequestDto.items(), order);

    order.setOrderLineItems(items);
    orderRepository.save(order);

    generateInvoice(order);

    orderRequestDto
        .items()
        .forEach(item -> inventoryClient.reduceStock(item.productId(), item.quantity()));

    customerClient.updateClientHistory(orderRequestDto.customerId(), order.getId());

    return mapToOrderResponseDto(order);
  }

  @Transactional(readOnly = true)
  private void validateSale(OrderRequestDto orderRequestDto) {
    boolean customerExists = customerClient.checkCustomerExists(orderRequestDto.customerId());
    if (!customerExists)
      throw new EntityNotFoundException(
          String.format("Couldn't find customer with the id: %s", orderRequestDto.customerId()));

    for (var item : orderRequestDto.items()) {
      boolean inStock = inventoryClient.isInStock(item.productId(), item.quantity());

      if (!inStock)
        throw new RuntimeException(String.format("Product %s is out of stock", item.productId()));
    }
  }

  private BigDecimal calculateTotal(List<OrderLineItemDto> orderLineItems) {
    return orderLineItems.stream()
        .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  @Transactional(readOnly = true)
  public byte[] getInvoicePdf(UUID orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Sale not found with the id: %s", orderId)));

    List<OrderLineItem> items = orderLineItemRepository.findByOrderId(orderId);

    return invoiceGenerator.generateInvoicePdf(order, items);
  }

  @Transactional
  private Invoice generateInvoice(Order order) {
    return invoiceService.createInvoice(
        Invoice.builder()
            .saleId(order.getId())
            .billingDate(LocalDateTime.now())
            .totalAmount(order.getTotal())
            .paymentStatus(Status.PENDING)
            .pdfContent(invoiceGenerator.generateInvoicePdf(order, order.getOrderLineItems()))
            .build());
  }

  private List<OrderLineItem> mapItems(List<OrderLineItemDto> items, @NotNull Order order) {
    return items.stream()
        .map(
            item ->
                OrderLineItem.builder()
                    .productId(item.productId())
                    .quantity(item.quantity())
                    .price(item.price())
                    .order(order)
                    .build())
        .toList();
  }

  private OrderLineItemDto mapToOrderLineItemDto(OrderLineItem orderLineItem) {
    return new OrderLineItemDto(
        orderLineItem.getProductId(), orderLineItem.getQuantity(), orderLineItem.getPrice());
  }

  private OrderResponseDto mapToOrderResponseDto(Order order) {
    return new OrderResponseDto(
        order.getId(),
        order.getCustomerId(),
        order.getStatus(),
        order.getTotal(),
        order.getOrderLineItems().stream().map(this::mapToOrderLineItemDto).toList(),
        order.getCreatedAt(),
        order.getUpdatedAt());
  }
}
