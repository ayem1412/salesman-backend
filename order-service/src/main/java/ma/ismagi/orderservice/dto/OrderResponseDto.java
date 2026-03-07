package ma.ismagi.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import ma.ismagi.orderservice.model.Status;

/** OrderResponseDto */
public record OrderResponseDto(
    UUID id,
    UUID customerId,
    Status status,
    BigDecimal total,
    List<OrderLineItemDto> items,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
