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
import java.util.List;

@Service
public class PaymentCardService {
    @Autowired
    private PaymentCardRepository paymentCardRepository;

    public PaymentCard createNewPaymentCard(String cardNumber, String expirationDate, String billingAddress, ObjectId userID) {

        PaymentCard newCard = null;
        try {
            String encrypted1 = SecurityConfig.encrypt("cardNumber");
            String decrypted1 = SecurityConfig.decrypt(encrypted1);

            System.out.println("Original:  " + cardNumber);
            System.out.println("Encrypted: " + encrypted1);
            System.out.println("Decrypted: " + decrypted1);

            String encrypted = SecurityConfig.encrypt(cardNumber);
            String decrypted = SecurityConfig.decrypt(encrypted);

            System.out.println("Original:  " + cardNumber);
            System.out.println("Encrypted: " + encrypted);
            System.out.println("Decrypted: " + decrypted);
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
        List<PaymentCard> decryptedCards = new ArrayList<>();

        for(PaymentCard c : cards){
            try {
                decryptedCards.add(new PaymentCard(SecurityConfig.decrypt(c.getCardNumber()),
                        SecurityConfig.decrypt(c.getExpirationDate()), SecurityConfig.decrypt(c.getBillingAddress()), userObjectID));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return decryptedCards;
    }

    public void updatePaymentInformation(List<PaymentCard> newCards, ObjectId userObjectID){
        List<PaymentCard> currentCards = getCardsByUser(userObjectID);
        int count = 0;
        int incoming = (newCards != null) ? newCards.size() : 0;
        System.out.println("Incoming" + incoming);
        if (incoming > 3) {
            throw new IllegalArgumentException("Cannot store more than 3 payment cards total.");
        }
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
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            count++;
        }
    }
}
