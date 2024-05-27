package ge.ourApp.service;

import ge.ourApp.dto.SignUpDto;
import ge.ourApp.dto.UserDto;
import ge.ourApp.entity.User;
import ge.ourApp.exceptions.AppException;
import ge.ourApp.mappers.UserMapper;
import ge.ourApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCrudService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public String removeUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.BAD_REQUEST));
        userRepository.delete(user);
        return "%s deleted".formatted(user.getEmail());
    }

    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.BAD_REQUEST));

        if(!user.isEnable()){
            throw new AppException("User is not enabled", HttpStatus.BAD_REQUEST);
        }

        return userMapper.userToUserDto(user);
    }

    public UserDto updateUser(Long userId, SignUpDto signUpDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.BAD_REQUEST));

        if(!user.isEnable()){
            throw new AppException("User is not enabled", HttpStatus.BAD_REQUEST);
        }

        user.setFirstname(signUpDto.getFirstname());
        user.setLastname(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setPhoneNumber(signUpDto.getPhoneNumber());

        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

}
