package ma.ismagi.orderservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.ismagi.orderservice.model.Status;
import ma.ismagi.orderservice.util.BaseEntity;

/** Order */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column private UUID customerId;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Column private BigDecimal total;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
  private List<OrderLineItem> orderLineItems;
}
