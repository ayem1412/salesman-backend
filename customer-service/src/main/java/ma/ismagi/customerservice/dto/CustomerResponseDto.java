package ma.ismagi.customerservice.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * CustomerResponseDto
 */
public record CustomerResponseDto(
 @NotNull UUID id,
 @NotBlank String name,
 @NotBlank String address,
 @NotBlank String email,
 @NotBlank String phone,
 List<UUID> purchaseHistory) {}
