package ge.ourApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/message")
    public String message() {
        return "Hello from message controller";
    }

    @PostMapping("/messagee")
    public String messagePost() {
        return "Hello from message controller post method";
    }
}
