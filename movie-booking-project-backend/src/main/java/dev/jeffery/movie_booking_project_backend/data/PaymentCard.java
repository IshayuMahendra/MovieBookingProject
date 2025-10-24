package dev.jeffery.movie_booking_project_backend.data;

import dev.jeffery.movie_booking_project_backend.security.SecurityConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PaymentCard")
@Data
@NoArgsConstructor
public class PaymentCard {
    @Id
    private ObjectId id = new ObjectId();
    private String cardNumber;
    private String expirationDate;
    private String billingAddress;
    private ObjectId userObjectID;

    public PaymentCard(String cardNumber, String expirationDate, String billingAddress, ObjectId userObjectID) {
        try {
            this.cardNumber = cardNumber;
            this.expirationDate = expirationDate;
            this.billingAddress = billingAddress;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.userObjectID = userObjectID;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public ObjectId getUserObjectID() {
        return userObjectID;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
}
