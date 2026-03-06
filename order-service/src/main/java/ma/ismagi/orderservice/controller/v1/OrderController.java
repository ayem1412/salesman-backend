package ma.ismagi.orderservice.controller.v1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.ismagi.orderservice.entity.Order;
import ma.ismagi.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

/**
 * OrderController
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
  private final OrderService orderService;

  @GetMapping("/history/{customerId}")
  public ResponseEntity<List<Order>> getHistory(@PathVariable UUID customerId) {
    return ResponseEntity.ok(orderService.getCustomerHistory(customerId));
  }

  @GetMapping("/reports")
  public ResponseEntity<Map<String, BigDecimal>> getReports(
      @RequestParam int year,
      @RequestParam(required = false) Integer month,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime day) {
    return ResponseEntity.ok(orderService.getSalesReport(year, month, day));
  }

  @GetMapping("/invoice")
  public ResponseEntity<byte[]> getInvoicePdf(@PathVariable UUID orderId) {
    byte[] pdfContent = orderService.getInvoicePdf(orderId);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.attachment().filename(String.format("invoice_%s.pdf", orderId)).build());

    return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
  }
}
