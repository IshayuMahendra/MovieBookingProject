package dev.jeffery.movie_booking_project_backend.data;

import dev.jeffery.movie_booking_project_backend.security.SecurityConfig;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PaymentCard {
    private String cardNumber;
    private String nameOnCard;
    private String expirationDate;
    private String ccv;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PaymentCard() {
    }

    public PaymentCard(String cardNumber, String nameOnCard, String expirationDate, String ccv) {
        this.nameOnCard = passwordEncoder.encode(cardNumber);
        this.nameOnCard = passwordEncoder.encode(nameOnCard);
        this.expirationDate = passwordEncoder.encode(expirationDate);
        this.ccv = passwordEncoder.encode(ccv);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public String getExpirationDate() {return expirationDate;}

    public String getCcv() { return ccv; }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = passwordEncoder.encode(cardNumber);
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = passwordEncoder.encode(nameOnCard);
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = passwordEncoder.encode(expirationDate);
    }

    public void setCcv(String ccv){
        this.ccv = passwordEncoder.encode(ccv);
    }

}
