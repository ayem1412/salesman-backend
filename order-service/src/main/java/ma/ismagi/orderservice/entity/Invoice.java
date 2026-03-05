package ma.ismagi.orderservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ma.ismagi.orderservice.util.BaseEntity;
import ma.ismagi.orderservice.model.Status;

/**
 * Invoice
 */
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

  @Lob
  private byte[] pdfContent;
}
