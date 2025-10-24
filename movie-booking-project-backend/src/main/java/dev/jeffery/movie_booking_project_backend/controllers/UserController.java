package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.data.PaymentCard;
import dev.jeffery.movie_booking_project_backend.data.User;
import dev.jeffery.movie_booking_project_backend.services.PaymentCardService;
import dev.jeffery.movie_booking_project_backend.services.UserService;
import dev.jeffery.movie_booking_project_backend.dto.LoginRequest;
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
    public ResponseEntity<String> editProfile(@RequestBody User updatedInfoTemplate, @RequestBody List<PaymentCard> cards) {
        try {
            userService.updateUserInformation(updatedInfoTemplate);
            paymentCardService.updatePaymentInformation(cards, updatedInfoTemplate.getId());
            return ResponseEntity.ok("Successfully edited profile.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error editing user information: " + e.getMessage());
        }
    }

}
