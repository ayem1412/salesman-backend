package ma.ismagi.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import ma.ismagi.inventoryservice.dto.ProductRequestDto;
import ma.ismagi.inventoryservice.dto.ProductResponseDto;
import ma.ismagi.inventoryservice.entity.Product;
import ma.ismagi.inventoryservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** ProductService */
@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  @Transactional(readOnly = true)
  public List<ProductResponseDto> getAllProducts() {
    return productRepository.findAll().stream().map(this::mapToProductResponseDto).toList();
  }

  @Transactional(readOnly = true)
  public ProductResponseDto getProductById(@NotNull UUID id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Couldn't find product with the id: %s", id)));

    return mapToProductResponseDto(product);
  }

  @Transactional(readOnly = true)
  public boolean isProductInStock(@NotNull UUID productId, @NotNull Integer quantity) {
    return productRepository
        .findById(productId)
        .map(product -> product.getQuantity() >= quantity)
        .orElse(false);
  }

  @Transactional
  public void reduceProductQuantity(@NotNull UUID productId, @NotNull Integer quantity) {
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Couldn't find product with the id: %s", productId)));

    if (product.getQuantity() < quantity)
      throw new IllegalStateException(
          String.format("Insufficient stock for product: %s", productId));

    product.setQuantity(product.getQuantity() - quantity);
  }

  @Transactional
  public ProductResponseDto createProduct(@NotNull ProductRequestDto productRequestDto) {
    return mapToProductResponseDto(
        productRepository.save(
            Product.builder()
                .name(productRequestDto.name())
                .description(productRequestDto.description())
                .price(productRequestDto.price())
                .quantity(productRequestDto.quantity())
                .imageUrl(productRequestDto.imageUrl())
                .build()));
  }

  @Transactional
  public ProductResponseDto updateProduct(UUID id, @NotNull ProductRequestDto productRequestDto) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Couldn't find product with the id: %s", id)));

    product.setName(productRequestDto.name());
    product.setDescription(productRequestDto.description());
    product.setPrice(productRequestDto.price());
    product.setQuantity(productRequestDto.quantity());
    product.setImageUrl(productRequestDto.imageUrl());

    return mapToProductResponseDto(productRepository.save(product));
  }

  @Transactional
  public void deleteProduct(@NotNull UUID id) {
    productRepository
        .findById(id)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("Couldn't find product with the id: %s", id)));

    productRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public List<ProductResponseDto> getAllLowStockProducts(int threshold) {
    return productRepository.findLowStockProducts(threshold).stream()
        .map(this::mapToProductResponseDto)
        .toList();
  }

  private ProductResponseDto mapToProductResponseDto(@NotNull Product product) {
    return new ProductResponseDto(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getQuantity(),
        product.getImageUrl(),
        product.getCreatedAt(),
        product.getUpdatedAt());
  }
}
