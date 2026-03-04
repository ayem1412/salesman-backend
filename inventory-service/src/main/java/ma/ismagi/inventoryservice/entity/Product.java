package ma.ismagi.inventoryservice.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.ismagi.inventoryservice.util.BaseEntity;

/**
 * Product
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true, comment = "The product name.")
  private String name;

  @Column(nullable = false, comment = "The product description.")
  private String description;

  @Column(nullable = false, comment = "The product price.")
  private BigDecimal price;

  @Column(nullable = false, comment = "The product quantity.")
  private Integer quantity;
}
