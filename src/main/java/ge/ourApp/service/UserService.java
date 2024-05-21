package ge.ourApp.service;

import ge.ourApp.exceptions.AppException;
import ge.ourApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    }
}
