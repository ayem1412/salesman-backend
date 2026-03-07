package ma.ismagi.identityservice.controller.v1;

import lombok.RequiredArgsConstructor;
import ma.ismagi.identityservice.dto.AuthRequestDto;
import ma.ismagi.identityservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** AuthController */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody AuthRequestDto authRequestDto) {
    String token = authService.login(authRequestDto);
    return ResponseEntity.ok(token);
  }
}
