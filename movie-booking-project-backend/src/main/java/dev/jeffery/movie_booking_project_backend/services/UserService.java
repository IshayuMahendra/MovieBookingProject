package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.PaymentCard;
import dev.jeffery.movie_booking_project_backend.data.User;
import dev.jeffery.movie_booking_project_backend.repositories.UserRepository;
import dev.jeffery.movie_booking_project_backend.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.jeffery.movie_booking_project_backend.dto.ChangePasswordRequest;
import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    not sure if this is needed, may be able to just create a UserRepository object in frontend and call queries from there
//    public User getUser(String email){
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return user;
//    }

    public User createNewUser(String userID, String password, String email, String firstName, String lastName,
                              String street, String city, String state, String zipCode, boolean promotions) {

        if (userRepository.findByEmail(email.trim()).isPresent()) {
            throw new RuntimeException("Email already registered.");
        }

        User user = new User(
                userID,
                passwordEncoder.encode(password),
                email.trim(),
                firstName,
                lastName,
                User.accountStatus.Inactive,
                street, city, state, zipCode,
                promotions
        );

        userRepository.save(user);
        return user;
    }

    public boolean authenticateUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public void updateUserInformation(User updatedInfoTemplate) {
        String email = updatedInfoTemplate.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedInfoTemplate.getFirstName() != null && !updatedInfoTemplate.getFirstName().isBlank()) {
            user.setFirstName(updatedInfoTemplate.getFirstName());
        }
        if (updatedInfoTemplate.getLastName() != null && !updatedInfoTemplate.getLastName().isBlank()) {
            user.setLastName(updatedInfoTemplate.getLastName());
        }
        if (updatedInfoTemplate.getStreet() != null && !updatedInfoTemplate.getStreet().isBlank()) {
            user.setStreet(updatedInfoTemplate.getStreet());
        }
        if (updatedInfoTemplate.getCity() != null && !updatedInfoTemplate.getCity().isBlank()) {
            user.setCity(updatedInfoTemplate.getCity());
        }
        if (updatedInfoTemplate.getState() != null && !updatedInfoTemplate.getState().isBlank()) {
            user.setState(updatedInfoTemplate.getState());
        }
        if (updatedInfoTemplate.getZipCode() != null && !updatedInfoTemplate.getZipCode().isBlank()) {
            user.setZipCode(updatedInfoTemplate.getZipCode());
        }
        user.setPromotions(updatedInfoTemplate.getPromotions());
        userRepository.save(user);
    }

    public void changePassword(ChangePasswordRequest req) {
        if (req.getEmail() == null || req.getCurrentPassword() == null || req.getNewPassword() == null) {
            throw new IllegalArgumentException("Missing fields");
        }
        var user = userRepository.findByEmail(req.getEmail().trim())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (passwordEncoder.matches(req.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from current");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }

}
