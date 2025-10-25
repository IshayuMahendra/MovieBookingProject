package dev.jeffery.movie_booking_project_backend.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    private String id;
    private String userEmail;
    private String token;
    private Instant expiresAt;
    private boolean used;

    public PasswordResetToken() {}

    public PasswordResetToken(String userEmail, String token, Instant expiresAt, boolean used) {
        this.userEmail = userEmail;
        this.token = token;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    public String getId() { return id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }
}