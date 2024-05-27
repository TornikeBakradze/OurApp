package ge.ourApp.controller;

import ge.ourApp.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> upload(@PathVariable Long userId,
                                         @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(imageService.uploadImage(file, userId));
    }

    @GetMapping("/download/{imageName}")
    public ResponseEntity<?> download(@PathVariable String imageName) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .contentType(MediaType.valueOf("image/png"))
                .contentType(MediaType.valueOf("image/gif"))
                .body(imageService.downloadImage(imageName));
    }
}
