package ma.ismagi.identityservice.seeder;

import lombok.RequiredArgsConstructor;
import ma.ismagi.identityservice.entity.User;
import ma.ismagi.identityservice.model.Role;
import ma.ismagi.identityservice.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "elmahdad.anasyoussef00@proton.me";
        
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = User.builder()
                    .name("Anas Youssef El Mahdad")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            
            System.out.println(String.format("Default admin user created: ", adminEmail));
        } else {
            System.out.println("Admin user already exists. Skipping seeding.");
        }
    }
}
