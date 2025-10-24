package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.PaymentCard;
import dev.jeffery.movie_booking_project_backend.data.User;
import dev.jeffery.movie_booking_project_backend.repositories.PaymentCardRepository;
import dev.jeffery.movie_booking_project_backend.repositories.UserRepository;
import dev.jeffery.movie_booking_project_backend.security.SecurityConfig;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentCardService {
    @Autowired
    private PaymentCardRepository paymentCardRepository;

    public PaymentCard createNewPaymentCard(String cardNumber, String expirationDate, String billingAddress, ObjectId userID) {

        // encryption is done in the contructor.
        PaymentCard newCard = null;
        try {
            newCard = new PaymentCard(SecurityConfig.encrypt(cardNumber), SecurityConfig.encrypt(expirationDate),
                    SecurityConfig.encrypt(billingAddress), userID);
            paymentCardRepository.save(newCard);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return newCard;
    }

    public void updatePaymentInformation(List<PaymentCard> newCards, ObjectId userObjectID){
        List<PaymentCard> currentCards = paymentCardRepository.findByUserObjectID(userObjectID);
        int count = 0;

        for (PaymentCard newCard : newCards){
            if(count >= currentCards.size()){
                try {
                    createNewPaymentCard(SecurityConfig.encrypt(newCard.getCardNumber()), SecurityConfig.encrypt(newCard.getExpirationDate()),
                            SecurityConfig.encrypt(newCard.getBillingAddress()), userObjectID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                PaymentCard c = currentCards.get(count);
                try {
                    c.setCardNumber(SecurityConfig.encrypt(newCard.getCardNumber()));
                    c.setExpirationDate(SecurityConfig.encrypt(newCard.getExpirationDate()));
                    c.setBillingAddress(SecurityConfig.encrypt(newCard.getBillingAddress()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            count++;
        }
    }
}
