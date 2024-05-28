package ge.ourApp.controller;

import ge.ourApp.dto.SignUpDto;
import ge.ourApp.dto.UserDto;
import ge.ourApp.service.ImageService;
import ge.ourApp.service.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserCrudService userCrudService;
    private final ImageService imageService;

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

    @PutMapping("/image/update/{userId}")
    public ResponseEntity<String> updateUserImage(@PathVariable("userId") Long userId,
                                                  @RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok(imageService.updateImage(file, userId));
    }

    @PostMapping("/image/upload/{userId}")
    public ResponseEntity<String> uploadUserMessage(@PathVariable Long userId,
                                                    @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.uploadImage(file, userId));
    }

    @GetMapping("/image/download/{imageName}")
    public ResponseEntity<?> download(@PathVariable String imageName) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .contentType(MediaType.valueOf("image/png"))
                .contentType(MediaType.valueOf("image/gif"))
                .body(imageService.downloadImage(imageName));
    }
}
