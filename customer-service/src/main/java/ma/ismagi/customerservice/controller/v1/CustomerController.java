package ma.ismagi.customerservice.controller.v1;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import ma.ismagi.customerservice.dto.CustomerRequestDto;
import ma.ismagi.customerservice.dto.CustomerResponseDto;
import ma.ismagi.customerservice.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** CustomerController */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
  private final CustomerService customerService;

  @GetMapping
  public ResponseEntity<List<CustomerResponseDto>> getAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String phone,
      @RequestParam(required = false) String address) {
    return ResponseEntity.ok(customerService.searchAndFilterCustomers(name, email, phone, address));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponseDto> get(@PathVariable UUID id) {
    return ResponseEntity.ok(customerService.getCustomerById(id));
  }

  @PostMapping("/create")
  public ResponseEntity<CustomerResponseDto> create(
      @Valid @RequestBody CustomerRequestDto customerRequestDto) {
    return ResponseEntity.ok(customerService.createCustomer(customerRequestDto));
  }

  @GetMapping("/{id}/exists")
  public ResponseEntity<Boolean> checkCustomerExists(@PathVariable UUID id) {
    return ResponseEntity.ok(customerService.existsById(id));
  }

  @PutMapping("/{id}/add-to-history")
  public ResponseEntity<Void> updateHistory(@PathVariable UUID id, @RequestParam UUID saleId) {
    customerService.addSaleToHistory(id, saleId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }
}
