package dev.jeffery.movie_booking_project_backend.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class PaymentCard {
    private String cardNumber;
    private String billingAddress;
    private String expirationDate;

    public PaymentCard() {
    }

    public PaymentCard(String cardNumber, String billingAddress, String expirationDate) {
        this.cardNumber = cardNumber;
        this.billingAddress = billingAddress;
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
