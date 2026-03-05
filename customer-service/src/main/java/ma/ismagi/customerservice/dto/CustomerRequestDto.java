package ma.ismagi.customerservice.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * CustomerRequestDto
 */
public record CustomerRequestDto(
  @NotBlank String name,
  @NotBlank String address,
  @NotBlank String email,
  @NotBlank String phone) {}
