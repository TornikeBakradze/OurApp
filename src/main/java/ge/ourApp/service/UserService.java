package ge.ourApp.service;

import ge.ourApp.dto.SignUpDto;
import ge.ourApp.entity.User;
import ge.ourApp.enums.Role;
import ge.ourApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(SignUpDto userDto) {
        User user = User.builder()
                .role(Role.USER)
                .firstName(userDto.getLogin())
                .lastName(userDto.getLastName())
                .login(userDto.getFirstName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");
        return userRepository.findByFirstName(username).orElseThrow(() -> new RuntimeException("user not found"));
    }
}