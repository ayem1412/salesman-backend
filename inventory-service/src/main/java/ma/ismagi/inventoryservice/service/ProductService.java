package ma.ismagi.inventoryservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
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
  public ProductResponseDto getProductById(@NotNull UUID id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find product with the id: %s", id)));

    return mapToProductResponseDto(product);
  }

  @Transactional(readOnly = true)
  public boolean isProductInStock(@NotNull UUID productId, @NotNull Integer quantity) {
    return productRepository.findById(productId)
        .map(product -> product.getQuantity() >= quantity)
        .orElse(false);
  }

  @Transactional
  public ProductResponseDto reduceProductQuantity(@NotNull UUID productId, @NotNull Integer quantity) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find product with the id: %s", productId)));

    if (product.getQuantity() < quantity)
      throw new RuntimeException(String.format("Insufficient stock for product: %s", productId));

    product.setQuantity(product.getQuantity() - quantity);
    return mapToProductResponseDto(product);
  }

  @Transactional
  public ProductResponseDto createProduct(@NotNull ProductRequestDto productRequestDto) {
    return mapToProductResponseDto(productRepository.save(
          Product.builder()
              .name(productRequestDto.name())
              .description(productRequestDto.description())
              .price(productRequestDto.price())
              .quantity(productRequestDto.quantity())
              .build()));
  }

  private ProductResponseDto mapToProductResponseDto(@NotNull Product product) {
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
