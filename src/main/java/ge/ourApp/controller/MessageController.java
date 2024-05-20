package ge.ourApp.controller;

import ge.ourApp.exceptions.AppException;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/message")
    public String message() {
        return "Hello from message controller";
    }

    @PostMapping("/messagee/{id}")
    public String messagePost(@PathVariable(name = "id") String id) {
        if ("1".equals(id)) {
            throw new AppException("Incorect", HttpStatus.NOT_ACCEPTABLE);
        }
        return "Hello from message controller post method";
    }
}
