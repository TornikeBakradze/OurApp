package ge.ourApp.controller;


import ge.ourApp.dto.LoginDto;
import ge.ourApp.dto.SignUpDto;
import ge.ourApp.dto.UserDto;
import ge.ourApp.entity.User;
import ge.ourApp.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/regster")
    public ResponseEntity<User> register(@RequestBody @Valid SignUpDto user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }


    @PostMapping("/login")
    public UserDto loginUser(@RequestBody LoginDto loginDto) {
        return authenticationService.loginUser(loginDto);
    }
}
