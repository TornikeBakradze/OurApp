package ge.ourApp.controller;

import ge.ourApp.dto.SignUpDto;
import ge.ourApp.dto.UserDto;
import ge.ourApp.service.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserCrudService userCrudService;

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable Long userId) {
        userCrudService.removeUser(userId);
        return ResponseEntity.ok("Removed user " + userId);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userCrudService.getUser(userId));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody @Valid SignUpDto signUpDto) {
        return ResponseEntity.ok(userCrudService.updateUser(userId, signUpDto));
    }
}
