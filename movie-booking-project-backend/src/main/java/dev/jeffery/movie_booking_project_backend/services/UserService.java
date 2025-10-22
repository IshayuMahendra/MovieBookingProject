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

    public User createNewUser(String userID, String password, String email, String firstName, String lastName,
                              List<PaymentCard> cards, String street, String city, String state, String zipCode, boolean promotions) {

//        //encrypt all payment info
//        for(PaymentCard c : cards){
//            c.setCardNumber(passwordEncoder.encode(c.getCardNumber()));
//            c.setNameOnCard(passwordEncoder.encode(c.getNameOnCard()));
//            c.setExpirationDate(passwordEncoder.encode(c.getExpirationDate()));
//            c.setCcv();
//        }

        //set and save user info (encrypts password in contructor)
        User user = new User(userID, passwordEncoder.encode(password), email, firstName, lastName,
                User.accountStatus.Inactive, cards, street, city, state, zipCode, promotions);
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
        User user = UserRepository.findByEmail(email);

        user.setFirstName(updatedInfoTemplate.getFirstName());
        user.setLastName(updatedInfoTemplate.getLastName());
        user.setStreet(updatedInfoTemplate.getStreet());
        user.setCity(updatedInfoTemplate.getCity());
        user.setState(updatedInfoTemplate.getState());
        user.setZipCode(updatedInfoTemplate.getZipCode());
        user.setPromotions(updatedInfoTemplate.getPromotions());

        String newPassword = passwordEncoder.encode(updatedInfoTemplate.getPassword());
        user.setPassword(newPassword);

        for (PaymentCard paymentCard : updatedInfoTemplate.getCards()) {
            paymentCard.setCardNumber(passwordEncoder.encode(paymentCard.getCardNumber()));
            paymentCard.setNameOnCard(passwordEncoder.encode(paymentCard.getNameOnCard));
            paymentCard.setExpirationDate(passwordEncoder.encode(paymentCard.getExpirationDate));
            paymentCard.setCcv(passwordEncoder.encode(paymentCard.getCcv));
        }
        if updatedInfoTemplate.getCards().size() > 4 {
            throw new RuntimeException("Cannot store more than 4 payment cards")
        }
        else {
            user.setCards(updatedInfoTemplate.getCards());
        }
    }
}
