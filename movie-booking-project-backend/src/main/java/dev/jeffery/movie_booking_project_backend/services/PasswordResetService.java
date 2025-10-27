package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.PasswordResetToken;
import dev.jeffery.movie_booking_project_backend.data.User;
import dev.jeffery.movie_booking_project_backend.repositories.PasswordResetTokenRepository;
import dev.jeffery.movie_booking_project_backend.repositories.UserRepository;
import dev.jeffery.movie_booking_project_backend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    private static final long EXPIRY_SECONDS = 60L * 60L; // 1 hour

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private PasswordResetTokenRepository tokenRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    // **Add this**
    @Autowired
    private EmailService emailService;

    public enum ResetOutcome { SUCCESS, INVALID_TOKEN, EXPIRED_TOKEN, USED_TOKEN, USER_NOT_FOUND }

    public Optional<String> createResetToken(String email) {
        System.out.println("FORGOT called for " + email);
        Optional<User> userOpt = userRepository.findByEmail(email);
        System.out.println("USER FOUND? " + userOpt.isPresent());
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        tokenRepository.deleteByUserEmail(email);

        String token = UUID.randomUUID().toString();
        System.out.println("PWD-RESET token for " + email + " = " + token);
        Instant expiresAt = Instant.now().plusSeconds(EXPIRY_SECONDS);

        PasswordResetToken prt = new PasswordResetToken(email, token, expiresAt, false);
        tokenRepository.save(prt);

        // now you can call emailService safely
        emailService.sendResetPasswordEmail(email, token);

        return Optional.of(token);
    }

    // ... rest of your methods



    public ResetOutcome resetWithToken(String token, String newPassword) {
        Optional<PasswordResetToken> prtOpt = tokenRepository.findByToken(token);
        if (prtOpt.isEmpty()) return ResetOutcome.INVALID_TOKEN;

        PasswordResetToken prt = prtOpt.get();
        if (prt.isUsed()) return ResetOutcome.USED_TOKEN;
        if (prt.getExpiresAt().isBefore(Instant.now())) return ResetOutcome.EXPIRED_TOKEN;

        Optional<User> userOpt = userRepository.findByEmail(prt.getUserEmail());
        if (userOpt.isEmpty()) return ResetOutcome.USER_NOT_FOUND;

        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        prt.setUsed(true);
        tokenRepository.save(prt);

        return ResetOutcome.SUCCESS;
    }
}
