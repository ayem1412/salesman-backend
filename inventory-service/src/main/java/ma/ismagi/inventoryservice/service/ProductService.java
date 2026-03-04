package ma.ismagi.inventoryservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ma.ismagi.inventoryservice.dto.ProductRequestDto;
import ma.ismagi.inventoryservice.dto.ProductResponseDto;
import ma.ismagi.inventoryservice.entity.Product;
import ma.ismagi.inventoryservice.repository.ProductRepository;

/**
 * ProductService
 */
@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  @Transactional(readOnly = true)
  public List<ProductResponseDto> getAllProducts() {
    return productRepository.findAll()
        .stream()
        .map(this::mapToProductResponseDto)
        .toList();
  }

  @Transactional(readOnly = true)
  public ProductResponseDto getProductById(@NonNull UUID id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find product with the id: %s", id)));

    return mapToProductResponseDto(product);
  }

  @Transactional
  public ProductResponseDto createProduct(@NonNull ProductRequestDto productRequestDto) {
    return mapToProductResponseDto(productRepository.save(
          Product.builder()
              .name(productRequestDto.name())
              .description(productRequestDto.description())
              .price(productRequestDto.price())
              .quantity(productRequestDto.quantity())
              .build()));
  }

  private ProductResponseDto mapToProductResponseDto(@NonNull Product product) {
    return new ProductResponseDto(
      product.getId(),
      product.getName(),
      product.getDescription(),
      product.getPrice(),
      product.getQuantity(),
      product.getCreatedAt(),
      product.getUpdatedAt());
  }
}
