package ge.ourApp.service;

import ge.ourApp.dto.LoginDto;
import ge.ourApp.dto.SendMailDto;
import ge.ourApp.dto.SignUpDto;
import ge.ourApp.dto.UserDto;
import ge.ourApp.entity.Role;
import ge.ourApp.entity.User;
import ge.ourApp.enums.Gender;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final MailService mailService;


    public String register(SignUpDto userDto) {

        Role role = roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new AppException("Role does not exist", HttpStatus.BAD_REQUEST));

        Optional<User> checkUser = userRepository.findByEmail(userDto.getEmail());
        if (checkUser.isPresent()) {
            throw new AppException("%s email is already exist".formatted(userDto.getEmail()), HttpStatus.BAD_REQUEST);
        }

        Set<Role> authorities = new HashSet<>();
        authorities.add(role);

        UUID uuid = UUID.randomUUID();

        User user = User.builder()
                .authorities(authorities)
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .gender(userDto.getGender()
                        .equalsIgnoreCase("MALE") ? Gender.MALE : Gender.FEMALE)
                .confirmUUID(uuid)
                .isEnable(false)
                .build();

        mailService.sendMail(SendMailDto.builder()
                .userName(userDto.getFirstname())
                .userEmail(userDto.getEmail())
                .confirmLinkUUID(uuid)
                .build());
        userRepository.save(user);
        return "User registered successfully. Please check your email to verify your account";
    }

    public String confirmUser(String uuid) {

        User user = userRepository.findByConfirmUUID(UUID.fromString(uuid)).orElseThrow(() -> new AppException("This user uuid does not exist", HttpStatus.BAD_REQUEST));

        user.setConfirmUUID(null);
        user.setEnable(true);

        return "%s account created successfully".formatted(user.getFirstname());
    }

    public UserDto loginUser(LoginDto loginDto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            User user = (User) auth.getPrincipal();

            String token = tokenService.generateJwt(auth);

            return UserDto.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .gender(user.getGender())
                    .token(token)
                    .build();
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
