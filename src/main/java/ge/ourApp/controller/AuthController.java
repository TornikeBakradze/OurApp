package ge.ourApp.controller;

import ge.ourApp.dto.LoginDto;
import ge.ourApp.dto.SignUpDto;
import ge.ourApp.dto.UserDto;
import ge.ourApp.entity.User;
import ge.ourApp.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/confirm/{userUUID}")
    public ResponseEntity<String> confirmUser(@PathVariable String userUUID) {
        return ResponseEntity.ok(authenticationService.confirmUser(userUUID));
    }
}
