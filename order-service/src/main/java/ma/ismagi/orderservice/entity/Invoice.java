package ma.ismagi.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ma.ismagi.orderservice.model.Status;
import ma.ismagi.orderservice.util.BaseEntity;

/** Invoice */
@Getter
@Setter
@Entity
@Builder
@Table(name = "invoices")
public class Invoice extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private UUID saleId;

  @Column(nullable = false)
  private LocalDateTime billingDate;

  @Column(nullable = false)
  private BigDecimal totalAmount;

  @Enumerated(EnumType.STRING)
  private Status paymentStatus;

  @Lob private byte[] pdfContent;
}
