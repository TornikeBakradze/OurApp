package ge.ourApp.service;

import ge.ourApp.dto.SignUpDto;
import ge.ourApp.dto.UserDto;
import ge.ourApp.entity.User;
import ge.ourApp.enums.Gender;
import ge.ourApp.exceptions.AppException;
import ge.ourApp.mappers.UserMapper;
import ge.ourApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCrudServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserCrudService userCrudService;

    private User user;
    private SignUpDto signUpDto;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@gmail.com");

        signUpDto = SignUpDto.builder()
                .firstname("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("password")
                .phoneNumber("123456789")
                .gender("MALE")
                .build();

        userDto = UserDto.builder()
                .email("test@gmail.com")
                .lastname("test")
                .firstname("test")
                .build();
    }

    @Test
    void testRemoveUser_UserExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        String result = userCrudService.removeUser(1L);

        assertEquals("test@gmail.com deleted", result);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testRemoveUser_UserNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        AppException appException = assertThrows(AppException.class,
                () -> userCrudService.removeUser(1L));
        assertEquals("User not found", appException.getMessage());
        verify(userRepository, never()).delete(any());
    }

    @Test
    void testGetUser_UserExistAndEnabled() {
        user.setEnable(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto result = userCrudService.getUser(1L);

        assertEquals(userDto, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUser_UserNotExistOrNotEnabled() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        AppException result = assertThrows(AppException.class, () -> userCrudService.getUser(1L));
        assertEquals("User not found", result.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUser_UserExistButNotEnabled() {
        user.setEnable(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        AppException result = assertThrows(AppException.class, () -> userCrudService.getUser(1L));
        assertEquals("User is not enabled", result.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser_UserExistAndEnabled() {
        user.setEnable(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User savedUser = new User();
        savedUser.setFirstname(signUpDto.getFirstname());
        savedUser.setLastname(signUpDto.getLastName());
        savedUser.setEmail(signUpDto.getEmail());
        savedUser.setPassword(signUpDto.getPassword());
        savedUser.setPhoneNumber(signUpDto.getPhoneNumber());
        savedUser.setGender(Gender.MALE);
        when(userRepository.save(user)).thenReturn(savedUser);

        when(userMapper.userToUserDto(savedUser)).thenReturn(userDto);

        UserDto result = userCrudService.updateUser(1L, signUpDto);

        assertEquals(userDto, result);
        verify(passwordEncoder, times(1)).encode(signUpDto.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser_UserNotExistOrNotEnabled() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        AppException result = assertThrows(AppException.class,
                () -> userCrudService.updateUser(1L, signUpDto));
        assertEquals("User not found", result.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser_UserExistButNotEnabled() {
        user.setEnable(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        AppException result = assertThrows(AppException.class,
                () -> userCrudService.updateUser(1L, signUpDto));
        assertEquals("User is not enabled", result.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }
}
