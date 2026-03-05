package ma.ismagi.customerservice.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ma.ismagi.customerservice.dto.CustomerRequestDto;
import ma.ismagi.customerservice.dto.CustomerResponseDto;
import ma.ismagi.customerservice.entity.Customer;
import ma.ismagi.customerservice.repository.CustomerRespository;

@Service
@RequiredArgsConstructor
public class CustomerService {
  private final CustomerRespository customerRepository;

  public CustomerResponseDto createCustomer(@NotNull CustomerRequestDto request) {
    return mapToResponse(customerRepository.save(
          Customer.builder()
              .name(request.name())
              .address(request.address())
              .email(request.email())
              .phone(request.phone())
              .purchaseHistory(new ArrayList<>())
              .build()));
  }

  public CustomerResponseDto getCustomerById(@NotNull UUID id) {
    Customer client = customerRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find a customer with the id %s", id)));
    return mapToResponse(client);
  }

  public boolean existsById(@NotNull UUID id) {
    return customerRepository.existsById(id);
  }

  @Transactional
  public Customer addSaleToHistory(UUID clientId, UUID saleId) {
    Customer customer = customerRepository.findById(clientId)
        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

    customer.getPurchaseHistory().add(saleId);
    return customerRepository.save(customer);
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
