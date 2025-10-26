package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.User;
import dev.jeffery.movie_booking_project_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;


@Service
public class VerificationService {
    @Autowired private UserRepository userRepository;

    private static final SecureRandom RNG = new SecureRandom();
    private static final Base64.Encoder B64 = Base64.getUrlEncoder().withoutPadding();
    private static final long TTL = 15 * 60_000L; // 15 minutes

    private String generateToken() {
        byte[] b = new byte[32]; // 256-bit
        RNG.nextBytes(b);
        return B64.encodeToString(b);
    }

    /** Save token+expiry on user, return raw token */
    public String issueToken(User u) {
        String token = generateToken();
        u.setEmailVerificationToken(token);
        u.setEmailVerificationExpiresAt(System.currentTimeMillis() + TTL);
        u.setEmailVerified(false);
        userRepository.save(u);
        return token;
    }

    /** Lookup by token, check expiry, activate user */
    public boolean verifyAndActivate(String rawToken) {
        var uOpt = userRepository.findByEmailVerificationToken(rawToken);
        if (uOpt.isEmpty()) return false;

        var u = uOpt.get();
        Long exp = u.getEmailVerificationExpiresAt();
        if (exp == null || System.currentTimeMillis() > exp) return false;

        u.setEmailVerified(true);
        u.setCustomerStatus(User.accountStatus.Active);
        u.setEmailVerificationToken(null);
        u.setEmailVerificationExpiresAt(null);
        userRepository.save(u);
        return true;
    }
}
        