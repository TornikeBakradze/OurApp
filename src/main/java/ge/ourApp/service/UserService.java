package ge.ourApp.service;

import ge.ourApp.dto.SignUpDto;
import ge.ourApp.entity.Role;
import ge.ourApp.entity.User;
import ge.ourApp.repository.RoleRepository;
import ge.ourApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(SignUpDto userDto) {

        Role role = roleRepository.findByAuthority("USER");

        User user = User.builder()
                .role(role)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .login(userDto.getLogin())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");
        return userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
