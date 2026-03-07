package ma.ismagi.orderservice.controller.v1;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import ma.ismagi.orderservice.dto.OrderRequestDto;
import ma.ismagi.orderservice.dto.OrderResponseDto;
import ma.ismagi.orderservice.entity.Order;
import ma.ismagi.orderservice.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** OrderController */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
  private final OrderService orderService;

  @PostMapping("/create")
  public ResponseEntity<OrderResponseDto> placeOrder(
      @Valid @RequestBody OrderRequestDto orderRequestDto) {
    return new ResponseEntity<>(orderService.placeOrder(orderRequestDto), HttpStatus.CREATED);
  }

  @GetMapping("/history/{customerId}")
  public ResponseEntity<List<Order>> getHistory(@PathVariable UUID customerId) {
    return ResponseEntity.ok(orderService.getCustomerHistory(customerId));
  }

  @GetMapping("/reports")
  public ResponseEntity<Map<String, BigDecimal>> getReports(
      @RequestParam int year,
      @RequestParam(required = false) Integer month,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime day) {
    return ResponseEntity.ok(orderService.getSalesReport(year, month, day));
  }

  @GetMapping(value = "/{orderId}/invoice", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> getInvoicePdf(@PathVariable UUID orderId) {
    byte[] pdfContent = orderService.getInvoicePdf(orderId);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(
        ContentDisposition.attachment().filename(String.format("invoice_%s.pdf", orderId)).build());
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

    return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
  }
}
