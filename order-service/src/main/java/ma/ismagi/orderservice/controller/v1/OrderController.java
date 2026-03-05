package ma.ismagi.orderservice.controller.v1;

import java.util.UUID;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

  public ResponseEntity<byte[]> getInvoicePdf(@PathVariable UUID orderId) {
    byte[] pdfContent = orderService.getInvoicePdf(orderId);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.attachment().filename(String.format("invoice_%s.pdf", orderId)).build());

    return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
  }
}
