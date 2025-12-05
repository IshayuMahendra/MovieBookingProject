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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PaymentCardService {
    @Autowired
    private PaymentCardRepository paymentCardRepository;

    public PaymentCard createNewPaymentCard(String cardNumber, String expirationDate, String billingAddress, ObjectId userID) {

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

    
    public List<PaymentCard> getCardsByUser(ObjectId userObjectID) {
        List<PaymentCard> cards = paymentCardRepository.findByUserObjectID(userObjectID);

        for(PaymentCard c : cards){
            try {
                c.setCardNumber(SecurityConfig.decrypt(c.getCardNumber()));
                c.setExpirationDate(SecurityConfig.decrypt(c.getExpirationDate()));
                c.setBillingAddress(SecurityConfig.decrypt(c.getBillingAddress()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return cards;
    }

    public void updatePaymentInformation(List<PaymentCard> newCards, ObjectId userObjectID){
        List<PaymentCard> currentCards = getCardsByUser(userObjectID);
        int count = 0;
        int incoming = (newCards != null) ? newCards.size() : 0;
        if (incoming > 3) {
            throw new IllegalArgumentException("Cannot store more than 3 payment cards total.");
        }
        
        Set<String> seen = new HashSet<>();
        for (PaymentCard card : newCards) {
            String key = card.getCardNumber() + "|" + card.getExpirationDate();
            if (!seen.add(key)) {
                throw new IllegalArgumentException("Cannot store multiple payment cards with the same card number and expiration date");
            }
        }

        boolean unassociateUser;
        for (PaymentCard currentCard : currentCards){
            unassociateUser = true;
            for (PaymentCard newCard : newCards){
                if(newCard.getCardNumber().equals(currentCard.getCardNumber()) &&
                        newCard.getBillingAddress().equals(currentCard.getBillingAddress()) &&
                        newCard.getExpirationDate().equals(currentCard.getExpirationDate())){
                    unassociateUser = false;
                    break;
                }
            }
            if(unassociateUser){
                try {
                    currentCard.setBillingAddress(SecurityConfig.encrypt(currentCard.getBillingAddress()));
                    currentCard.setCardNumber(SecurityConfig.encrypt(currentCard.getCardNumber()));
                    currentCard.setExpirationDate(SecurityConfig.encrypt(currentCard.getExpirationDate()));
                    currentCard.setUserObjectID(null);
                    paymentCardRepository.save(currentCard);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        currentCards = getCardsByUser(userObjectID);

        for (PaymentCard newCard : newCards){
            if(count >= currentCards.size()){
                try {
                    createNewPaymentCard(newCard.getCardNumber(), newCard.getExpirationDate(),
                            newCard.getBillingAddress(), userObjectID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                PaymentCard c = currentCards.get(count);
                try {
                    c.setCardNumber(SecurityConfig.encrypt(newCard.getCardNumber()));
                    c.setExpirationDate(SecurityConfig.encrypt(newCard.getExpirationDate()));
                    c.setBillingAddress(SecurityConfig.encrypt(newCard.getBillingAddress()));
                    paymentCardRepository.save(c);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            count++;
        }
    }
}
