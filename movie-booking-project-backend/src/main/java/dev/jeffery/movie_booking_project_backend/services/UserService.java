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

//    not sure if this is needed, may be able to just create a UserRepository object in frontend and call queries from there
//    public User getUser(String email){
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return user;
//    }

    public User createNewUser(String userID, String password, String email, String firstName, String lastName,
                              String street, String city, String state, String zipCode, boolean promotions) {

        //set and save user info
        User user = new User(userID, passwordEncoder.encode(password), email, firstName, lastName,
                User.accountStatus.Inactive, street, city, state, zipCode, promotions);
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
                .orElseThrow(() -> new RuntimeException("User not found"));;

        user.setFirstName(updatedInfoTemplate.getFirstName());
        user.setLastName(updatedInfoTemplate.getLastName());
        user.setStreet(updatedInfoTemplate.getStreet());
        user.setCity(updatedInfoTemplate.getCity());
        user.setState(updatedInfoTemplate.getState());
        user.setZipCode(updatedInfoTemplate.getZipCode());
        user.setPromotions(updatedInfoTemplate.getPromotions());
        user.setPassword(passwordEncoder.encode(updatedInfoTemplate.getPassword()));
    }
}
