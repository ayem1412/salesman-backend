package ma.ismagi.orderservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.ismagi.orderservice.dto.OrderLineItemDto;
import ma.ismagi.orderservice.dto.OrderRequestDto;
import ma.ismagi.orderservice.entity.Invoice;
import ma.ismagi.orderservice.entity.Order;
import ma.ismagi.orderservice.entity.OrderLineItem;
import ma.ismagi.orderservice.feignclient.CustomerClient;
import ma.ismagi.orderservice.feignclient.InventoryClient;
import ma.ismagi.orderservice.model.Status;
import ma.ismagi.orderservice.repository.OrderLineItemRepository;
import ma.ismagi.orderservice.repository.OrderRepository;
import ma.ismagi.orderservice.util.InvoiceGenerator;

/**
 * OrderService
 */
@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final CustomerClient customerClient;
  private final InventoryClient inventoryClient;
  private final InvoiceService invoiceService;
  private final OrderLineItemRepository orderLineItemRepository;
  private final InvoiceGenerator invoiceGenerator;

  public String placeOrder(OrderRequestDto orderRequestDto) {
    validateSale(orderRequestDto);

    BigDecimal total = calculateTotal(orderRequestDto.items());

    Order order = orderRepository.save(Order.builder()
        .orderNumber(UUID.randomUUID().toString())
        .customerId(orderRequestDto.customerId())
        .saleDate(LocalDateTime.now())
        .status(Status.PENDING)
        .total(total)
        .orderLineItems(mapItems(orderRequestDto.items()))
        .build());

    generateInvoice(order);

    orderRequestDto.items().forEach(item -> inventoryClient.reduceStock(item.productId(), item.quantity()));

    customerClient.updateClientHistory(orderRequestDto.customerId(), order.getId());

    return String.format("Sale %s completed and invoice generated", order.getOrderNumber());
  }

  private void validateSale(OrderRequestDto orderRequestDto) {
    boolean customerExists = customerClient.checkCustomerExists(orderRequestDto.customerId());
    if (!customerExists)
        throw new EntityNotFoundException(String.format("Couldn't find customer with the id: %s", orderRequestDto.customerId()));

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

  public byte[] getInvoicePdf(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Sale not found with the id: %s", orderId)));

        List<OrderLineItem> items = orderLineItemRepository.findByOrderId(orderId);

        return invoiceGenerator.generateInvoicePdf(order, items);
    }

  private Invoice generateInvoice(Order order) {
    return invoiceService.createInvoice(Invoice.builder()
        .saleId(order.getId())
        .billingDate(LocalDateTime.now())
        .totalAmount(order.getTotal())
        .paymentStatus(Status.PENDING)
        .pdfContent(generatePdfContentPlaceholder(order))
        .build());
  }

  private byte[] generatePdfContentPlaceholder(Order order) {
    return String.format("Invoice for sale: ", order.getOrderNumber()).getBytes();
  }
  
  private List<OrderLineItem> mapItems(List<OrderLineItemDto> items) {
    return items.stream()
        .map(item -> OrderLineItem.builder()
            .productId(item.productId())
            .quantity(item.quantity())
            .price(item.price())
            .build())
        .toList();
  }
}
