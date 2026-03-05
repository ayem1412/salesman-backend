package ma.ismagi.orderservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ma.ismagi.orderservice.model.Status;
import ma.ismagi.orderservice.util.BaseEntity;

/**
 * Order
 */
@Entity
@Getter
@Setter
@Builder
@Table(name = "orders")
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String orderNumber;

  private LocalDateTime saleDate;

  private Status status;

  private BigDecimal total;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id")
  private List<OrderLineItem> orderLineItems;

  private UUID customerId;
}
