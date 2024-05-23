package ge.ourApp.service;

import ge.ourApp.dto.LoginDto;
import ge.ourApp.dto.SignUpDto;
import ge.ourApp.dto.UserDto;
import ge.ourApp.entity.Role;
import ge.ourApp.entity.User;
import ge.ourApp.exceptions.AppException;
import ge.ourApp.repository.RoleRepository;
import ge.ourApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;


    public User register(SignUpDto userDto) {
        Role role = roleRepository.findByAuthority("USER");

        Set<Role> authorities = new HashSet<>();
        authorities.add(role);

        User user = User.builder()
                .authorities(authorities)
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        return userRepository.save(user);

    }

    public UserDto loginUser(LoginDto loginDto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );

            User user = (User) auth.getPrincipal();

            String token = tokenService.generateJwt(auth);
            return UserDto.builder()
                    .firstName(user.getFirstname())
                    .token(token)
                    .build();
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
