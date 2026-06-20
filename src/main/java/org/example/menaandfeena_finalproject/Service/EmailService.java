package org.example.menaandfeena_finalproject.Service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.menaandfeena_finalproject.Api.ApiException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String message) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public void sendEmailWithAttachment(String to, String subject, String body, byte[] attachmentBytes, String fileName) {
        try {
            jakarta.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(fileName, new ByteArrayResource(attachmentBytes));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new ApiException("Could not send email attachment");
        }
    }
}
