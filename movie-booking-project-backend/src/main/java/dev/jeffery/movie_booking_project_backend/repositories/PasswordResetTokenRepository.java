package dev.jeffery.movie_booking_project_backend.repositories;

import dev.jeffery.movie_booking_project_backend.data.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findFirstByUserEmailAndUsedFalseOrderByExpiresAtDesc(String userEmail);
    void deleteByUserEmail(String userEmail);
}
