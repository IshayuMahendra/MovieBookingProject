package dev.jeffery.movie_booking_project_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Value;
import jakarta.mail.internet.MimeMessage;




@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    private final String subject = "Email Verification";
    private final String path = "/user/verify"; // <-- this is your backend endpoint

    public void sendVerificationEmail(String email, String verificationToken) {
        try {
            // Builds full URL like: http://localhost:8080/user/verify?token=abc123
            String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(path)
                    .queryParam("token", verificationToken)
                    .toUriString();

            String content = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border-radius: 8px; background-color: #f9f9f9; text-align: center;">
                    <h2 style="color: #333;">Email Verification</h2>
                    <p style="font-size: 16px; color: #555;">Click the button below to verify your email address:</p>
                    <a href="%s" style="display: inline-block; margin: 20px 0; padding: 10px 20px; font-size: 16px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;">Verify Email</a>
                    <p style="font-size: 14px; color: #777;">Or copy and paste this link into your browser:</p>
                    <p style="font-size: 14px; color: #007bff;">%s</p>
                    <p style="font-size: 12px; color: #aaa;">This is an automated message. Please do not reply.</p>
                </div>
                """.formatted(actionUrl, actionUrl);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setText(content, true);

            mailSender.send(message);
            System.out.println("Verification email sent to " + email);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
            System.err.println(email);
        }
    }
}