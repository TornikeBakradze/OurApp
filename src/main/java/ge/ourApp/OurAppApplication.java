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

import java.util.HashSet;
import java.util.Set;

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
                    .authority("USER")
                    .build();

            roleRepository.save(roleAdmin);
            roleRepository.save(roleUSer);

            Set<Role> authorities = new HashSet<>();
            authorities.add(roleAdmin);

            User user = User.builder()
                    .firstName("Toko")
                    .lastName("Toko")
                    .login("Toko")
                    .password(passwordEncoder.encode("Toko"))
                    .authorities(authorities)
                    .build();

            userRepository.save(user);
        };
    }
}
