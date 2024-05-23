package ge.ourApp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    private static final String EMAIL = "tornike.bakradze758@ens.tsu.edu.ge";

    @Value("$(toko)")
    private String fromEmail;

    public void sendMail()  {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromEmail);
//        message.setTo(EMAIL);
//        message.setSubject("Confim link");
//        message.setText("Hello toko from youApp " +
//                "if you like this click http://localhost:8080/no");
//        javaMailSender.send(message);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);helper.setFrom(fromEmail);
            helper.setTo(EMAIL);
            helper.setSubject("Confirm link");
            helper.setText("<html><body><a href=\"http://localhost:8080/yes\">Yes</a></body></html>", true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(message);
    }
}
