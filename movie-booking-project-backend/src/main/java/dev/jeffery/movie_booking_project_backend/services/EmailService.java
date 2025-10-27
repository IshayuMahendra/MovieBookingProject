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
    private final String path = "/user/verify"; 
    private static final String BRAND = "Movie Booker";

    public void sendVerificationEmail(String email, String verificationToken) {
        try {
            
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

     public void sendProfileUpdatedEmail(String email) {
        try {
            String subject = "Your profile was updated";
            String content = """
                <div style="font-family: Arial, sans-serif; max-width:600px;margin:auto;padding:20px;border-radius:8px;background:#f9f9f9;">
                  <h2 style="margin:0 0 10px 0;color:#333;">%s</h2>
                  <p style="font-size:15px;color:#444;">We wanted to let you know your profile information was updated.</p>
                  <p style="font-size:13px;color:#666;">If you didn’t make this change, please reply to this email or reset your password immediately.</p>
                  <p style="font-size:12px;color:#999;margin-top:20px;">This is an automated message.</p>
                </div>
            """.formatted(BRAND);

            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(msg, true, "UTF-8");
            h.setTo(email);
            h.setSubject(subject);
            h.setFrom(from);
            h.setText(content, true);

            mailSender.send(msg);
            System.out.println("Profile update email sent to " + email);
        } catch (Exception e) {
            System.err.println("Failed to send profile update email: " + e.getMessage());
        }
    }

    public void sendResetPasswordEmail(String email, String token) {
    try {
        // Link to your frontend reset password page with the token
        String resetUrl = "http://127.0.0.1:5500/movie-booking-project-frontend/resetPass/resetPass.html?token=" + token;

        String subject = "CES Movies Password Reset";
        String content = """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin:auto; padding: 20px; background:#f9f9f9; text-align:center; border-radius:8px;">
                <h2 style="color:#333;">Password Reset Request</h2>
                <p style="font-size:16px; color:#555;">Click the button below to reset your password:</p>
                <a href="%s" style="display:inline-block; margin:20px 0; padding:10px 20px; font-size:16px; color:#fff; background-color:#007bff; text-decoration:none; border-radius:5px;">Reset Password</a>
                <p style="font-size:14px; color:#777;">Or copy this link into your browser:</p>
                <p style="font-size:14px; color:#007bff;">%s</p>
                <p style="font-size:12px; color:#aaa;">This is an automated message. Please do not reply.</p>
            </div>
        """.formatted(resetUrl, resetUrl);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setText(content, true);

        mailSender.send(message);
        System.out.println("Password reset email sent to " + email);
    } catch (Exception e) {
        System.err.println("Failed to send password reset email: " + e.getMessage());
    }
}


}