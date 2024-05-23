package ge.ourApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String message() {
        return "Admin level access";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String messagePost() {
        return "User level access";
    }

    @GetMapping("/yes")
    public void yes() {
        System.out.println("yes");
    }

    @GetMapping("/no")
    public void no() {
        System.out.println("no");
    }
}
