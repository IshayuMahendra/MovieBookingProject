package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.data.PaymentCard;
import dev.jeffery.movie_booking_project_backend.data.User;
import dev.jeffery.movie_booking_project_backend.dto.ForgotPasswordRequest;
import dev.jeffery.movie_booking_project_backend.repositories.UserRepository;
import dev.jeffery.movie_booking_project_backend.services.PasswordResetService;
import dev.jeffery.movie_booking_project_backend.services.PaymentCardService;
import dev.jeffery.movie_booking_project_backend.services.UserService;
import dev.jeffery.movie_booking_project_backend.dto.LoginRequest;
import dev.jeffery.movie_booking_project_backend.dto.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PaymentCardService paymentCardService;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.createNewUser(
                    user.getUserID(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getStreet(),
                    user.getCity(),
                    user.getState(),
                    user.getZipCode(),
                    user.getPromotions()
            );
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        boolean ok = userService.authenticateUser(req.getEmail(), req.getPassword());
        if (!ok) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logout successful. Session ended.");
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@RequestBody User updatedInfoTemplate) {
        try {
            userService.updateUserInformation(updatedInfoTemplate);
            return ResponseEntity.ok("Successfully edited profile.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error editing user information: " + e.getMessage());
        }
    }


    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest req) {
        System.out.println("CTRL /user/forgot HIT with email=" + req.getEmail());
        passwordResetService.createResetToken(req.getEmail());
        return ResponseEntity.ok("If that email exists, we’ve sent a reset link.");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @RequestBody dev.jeffery.movie_booking_project_backend.dto.ResetPasswordRequest req) {

        // Basic check: new matches confirm
        if (req.getNewPassword() == null || !req.getNewPassword().equals(req.getConfirm())) {
            return ResponseEntity.badRequest().body("Passwords do not match.");
        }

        var outcome = passwordResetService.resetWithToken(req.getToken(), req.getNewPassword());
        switch (outcome) {
            case SUCCESS:
                return ResponseEntity.ok("Password reset successful. You can log in now.");
            case INVALID_TOKEN:
                return ResponseEntity.status(400).body("Invalid reset token.");
            case EXPIRED_TOKEN:
                return ResponseEntity.status(400).body("Reset token has expired.");
            case USED_TOKEN:
                return ResponseEntity.status(400).body("Reset token has already been used.");
            case USER_NOT_FOUND:
            default:
                return ResponseEntity.status(404).body("User not found for this token.");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest req) {
        try {
            userService.changePassword(req);
            return ResponseEntity.ok("Password changed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/edit-cards")
    public ResponseEntity<String> editCards(@RequestParam String email, @RequestBody List<PaymentCard> cards) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        paymentCardService.updatePaymentInformation(cards, user.getId());
        return ResponseEntity.ok("Cards updated.");
    }

}
