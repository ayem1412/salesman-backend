package ma.ismagi.identityservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** AuthRequestDto */
public record AuthRequestDto(@NotBlank @Email String email, @NotBlank String password) {}
