package com.zonatech.app.infrastructure.adapters;

import com.zonatech.app.domain.ports.output.EmailServiceExternalServicePort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
public class EmailServiceAdapter implements EmailServiceExternalServicePort {

    private final JavaMailSender mailSender;

    @Override
    public CompletableFuture<Void> mandarCorreo
            (String Subject, String FromEmail, String ToEmail, String plainText, String htmlText) {
        return CompletableFuture.runAsync(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setSubject(Subject);
                helper.setFrom(FromEmail);
                helper.setTo(ToEmail);
                helper.setText(plainText, htmlText);
                mailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
