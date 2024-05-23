package ge.ourApp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    private static final String EMAIL = "tornike.bakradze758@ens.tsu.edu.ge";

    @Value("$(toko)")
    private String fromEmail;

    public void sendMail() {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(EMAIL);
            helper.setSubject("Confirm link");
            helper.setText("<!DOCTYPE html><html><head><title>Hello Toko</title></head><body><h1>Hello Toko</h1><a href=\"http://localhost:8080/yes\">Yes</a><img src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-NSEUHWmQsGxt4SfVM3f8VMW7vN8JsHnL-CnVII5E4A&s\" alt=\"Image\"></body></html>\n", true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(message);
    }
}
