package ma.ismagi.identityservice.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ma.ismagi.identityservice.dto.AuthRequestDto;
import ma.ismagi.identityservice.entity.User;
import ma.ismagi.identityservice.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public String login(@NotNull AuthRequestDto authRequestDto) {
    User user =
        userRepository
            .findByEmail(authRequestDto.email())
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        String.format(
                            "Could not find user with the email: %s", authRequestDto.email())));

    if (!passwordEncoder.matches(authRequestDto.password(), user.getPassword()))
      throw new AccessDeniedException("Invalid credentials");

    return jwtService.generateToken(user.getEmail(), user.getRole().name());
  }
}
