package ge.ourApp.service;

import ge.ourApp.entity.User;
import ge.ourApp.exceptions.AppException;
import ge.ourApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final UserRepository userRepository;

    @Value("${user.image.path}")
    private String imagePath;

    public String uploadImage(MultipartFile file, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User Not Found", HttpStatus.BAD_REQUEST));

        String folderPath = imagePath;

        UUID uuid = UUID.randomUUID();
        String fileName =
                uuid + "_" + file.getOriginalFilename().replace("[()\\s]", "");

        String filePath = folderPath + File.separator + fileName;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return "Image uploaded successfully";
    }

    public byte[] downloadImage(String name) {
        String folderPath = Paths.get(imagePath).toString();
        String filePath = folderPath + File.separator + name;

        Path path = Paths.get(filePath);

        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
