package ge.ourApp.service;

import ge.ourApp.dto.SendMailDto;
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

    @Value("$(OurApp)")
    private String fromMail;

    public void sendMail(SendMailDto sendMailDto) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromMail);
            helper.setTo(sendMailDto.userEmail());

            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<!DOCTYPE html><html><head>");
            htmlBuilder.append("<style>");
            htmlBuilder.append("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
            htmlBuilder.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
            htmlBuilder.append("h1 { color: #007bff; }");
            htmlBuilder.append("p { color: #444; }");
            htmlBuilder.append("a { color: #007bff; text-decoration: none;font-weight: bold;}");
            htmlBuilder.append("</style>");
            htmlBuilder.append("</head><body>");
            htmlBuilder.append("<div class=\"container\">");
            htmlBuilder.append("<h1>Hello %s</h1>".formatted(sendMailDto.userName()));
            htmlBuilder.append("<p>Now you try to register on OurApp</p>");
            htmlBuilder.append("<p>If you try registration follow the link</p>");
            htmlBuilder.append("<a href=\"http://localhost:8080/confirm/%s\">Confirm link</a>".formatted(sendMailDto.confirmLinkUUID()));
            htmlBuilder.append("</div>");
            htmlBuilder.append("</body></html>");

            helper.setSubject("Confirm link");
            helper.setText(htmlBuilder.toString(),true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(message);
    }
}
