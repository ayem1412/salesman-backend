package ma.ismagi.customerservice.controller.v1;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.ismagi.customerservice.dto.CustomerRequestDto;
import ma.ismagi.customerservice.dto.CustomerResponseDto;
import ma.ismagi.customerservice.service.CustomerService;

/**
 * CustomerController
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
  private final CustomerService customerService;

  @PostMapping("/create")
  public ResponseEntity<CustomerResponseDto> create(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
    return ResponseEntity.ok(customerService.createCustomer(customerRequestDto));
  }

  @GetMapping("/{id}/exists")
  public ResponseEntity<Boolean> checkCustomerExists(@PathVariable UUID id) {
    return ResponseEntity.ok(customerService.existsById(id));
  }

  @PatchMapping("/{id}/add-to-history")
  public ResponseEntity<Void> updateHistory(@PathVariable UUID id, @RequestParam UUID saleId) {
    customerService.addSaleToHistory(id, saleId);
    return ResponseEntity.ok().build();
  }
}
