package ge.ourApp.controller;


import ge.ourApp.dto.SignUpDto;
import ge.ourApp.entity.User;
import ge.ourApp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/regster")
    public ResponseEntity<User> register(@RequestBody @Valid SignUpDto user) {
        return ResponseEntity.ok(userService.register(user));
    }


}
