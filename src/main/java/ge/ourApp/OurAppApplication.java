package ge.ourApp;

import ge.ourApp.entity.Role;
import ge.ourApp.entity.User;
import ge.ourApp.repository.RoleRepository;
import ge.ourApp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OurAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(OurAppApplication.class, args);
    }


    @Bean
    CommandLineRunner run(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role roleAdmin = Role.builder()
                    .authority("ADMIN")
                    .build();

            Role roleUSer = Role.builder()
                    .authority("User")
                    .build();

            roleRepository.save(roleAdmin);
            roleRepository.save(roleUSer);

            User user = User.builder()
                    .firstName("Toko")
                    .lastName("Toko")
                    .login("Toko")
                    .password(passwordEncoder.encode("Toko"))
                    .role(roleAdmin)
                    .build();

            userRepository.save(user);
        };
    }
}
