package ma.ismagi.orderservice.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ma.ismagi.orderservice.util.BaseEntity;

/**
 * OrderLineItem
 */
@Entity
@Getter
@Setter
@Builder
@Table(name = "order_line_items")
public class OrderLineItem extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID productId; // product_id

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private Integer quantity;
}
