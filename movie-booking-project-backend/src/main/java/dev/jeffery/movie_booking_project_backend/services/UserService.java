package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.PaymentCard;
import dev.jeffery.movie_booking_project_backend.data.User;
import dev.jeffery.movie_booking_project_backend.repositories.UserRepository;
import dev.jeffery.movie_booking_project_backend.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(String userID, String password, boolean isAdmin, String email, String firstName, String lastName,
                              List<PaymentCard> cards, String street, String city, String state, String zipCode) {

        User user = new User(userID, passwordEncoder.encode(password), isAdmin, email, firstName, lastName,
                User.accountStatus.Active, cards, street, city, state, zipCode);
        userRepository.save(user);

        return user;
    }

    public boolean authenticateUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
