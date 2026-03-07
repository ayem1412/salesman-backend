package ma.ismagi.inventoryservice.controller.v1;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ma.ismagi.inventoryservice.dto.ProductRequestDto;
import ma.ismagi.inventoryservice.dto.ProductResponseDto;
import ma.ismagi.inventoryservice.service.ProductService;
import org.springframework.http.HttpStatus;
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

/** ProductController */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> all() {
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDto> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @GetMapping("/check-stock")
  public ResponseEntity<Boolean> isInStock(
      @RequestParam UUID productId, @RequestParam Integer quantity) {
    return ResponseEntity.ok(productService.isProductInStock(productId, quantity));
  }

  @PutMapping("/reduce-stock")
  public ResponseEntity<Void> reduceStock(
      @RequestParam UUID productId, @RequestParam Integer quantity) {
    productService.reduceProductQuantity(productId, quantity);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/create")
  public ResponseEntity<ProductResponseDto> create(
      @Valid @NonNull @RequestBody ProductRequestDto productRequestDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(productService.createProduct(productRequestDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDto> update(
      @PathVariable UUID id, @Valid @NonNull @RequestBody ProductRequestDto productRequestDto) {
    return ResponseEntity.ok(productService.updateProduct(id, productRequestDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/alerts")
  public ResponseEntity<List<ProductResponseDto>> lowStockProducts(
      @RequestParam(defaultValue = "5") int threshold) {
    return ResponseEntity.ok(productService.getAllLowStockProducts(threshold));
  }
}
