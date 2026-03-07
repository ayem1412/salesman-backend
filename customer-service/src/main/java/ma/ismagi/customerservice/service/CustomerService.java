package ma.ismagi.customerservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import ma.ismagi.customerservice.dto.CustomerRequestDto;
import ma.ismagi.customerservice.dto.CustomerResponseDto;
import ma.ismagi.customerservice.entity.Customer;
import ma.ismagi.customerservice.repository.CustomerRespository;
import ma.ismagi.customerservice.specification.CustomerSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
  private final CustomerRespository customerRepository;

  @Transactional(readOnly = true)
  public List<CustomerResponseDto> searchAndFilterCustomers(
      String name, String email, String phone, String address) {
    Specification<Customer> specification =
        CustomerSpecification.filterCustomers(name, email, phone, address);
    return customerRepository.findAll(specification).stream().map(this::mapToResponse).toList();
  }

  @Transactional
  public CustomerResponseDto createCustomer(@NotNull CustomerRequestDto request) {
    return mapToResponse(
        customerRepository.save(
            Customer.builder()
                .name(request.name())
                .address(request.address())
                .email(request.email())
                .phone(request.phone())
                .purchaseHistory(new ArrayList<>())
                .build()));
  }

  @Transactional(readOnly = true)
  public List<CustomerResponseDto> getAllCustomers() {
    return customerRepository.findAll().stream().map(this::mapToResponse).toList();
  }

  @Transactional(readOnly = true)
  public CustomerResponseDto getCustomerById(@NotNull UUID id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Couldn't find a customer with the id %s", id)));
    return mapToResponse(customer);
  }

  @Transactional(readOnly = true)
  public boolean existsById(@NotNull UUID id) {
    return customerRepository.existsById(id);
  }

  @Transactional
  public Customer addSaleToHistory(UUID id, UUID saleId) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

    customer.getPurchaseHistory().add(saleId);
    return customerRepository.save(customer);
  }

  @Transactional
  public void deleteCustomer(UUID id) {
    customerRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

    customerRepository.deleteById(id);
  }

  private CustomerResponseDto mapToResponse(@NotNull Customer client) {
    return new CustomerResponseDto(
        client.getId(),
        client.getName(),
        client.getAddress(),
        client.getEmail(),
        client.getPhone(),
        client.getPurchaseHistory());
  }
}
