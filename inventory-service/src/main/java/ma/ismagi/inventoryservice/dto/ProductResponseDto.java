package ma.ismagi.inventoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/** ProductResponseDto */
public record ProductResponseDto(
    @NotNull UUID id,
    @NotBlank String name,
    @NotBlank String description,
    @NotNull BigDecimal price,
    @NotNull Integer quantity,
    String imageUrl,
    @NotNull LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
