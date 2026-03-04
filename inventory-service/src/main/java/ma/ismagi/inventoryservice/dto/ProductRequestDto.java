package ma.ismagi.inventoryservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ProductRequestDto
 */
public record ProductRequestDto(
  @NotBlank String name,
  @NotBlank String description,

  @NotNull
  @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greated than 0")
  BigDecimal price,

  @NotNull
  @Min(value = 0, message = "The quantity cannot be negative")
  Integer quantity) {}
