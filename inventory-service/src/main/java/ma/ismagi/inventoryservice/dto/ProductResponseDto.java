package ma.ismagi.inventoryservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ProductResponseDto
 */
public record ProductResponseDto(
  @NotNull UUID id,
  @NotBlank String name,
  @NotBlank String description,
  @NotNull BigDecimal price,
  @NotNull Integer quantity,
  @NotNull LocalDateTime createdAt,
  LocalDateTime updatedAt) {}
