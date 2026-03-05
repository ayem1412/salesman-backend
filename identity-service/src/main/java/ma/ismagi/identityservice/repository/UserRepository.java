package ma.ismagi.identityservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import ma.ismagi.identityservice.entity.User;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(@NotBlank @Email String email);
}
