package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Booking")
@Data
@NoArgsConstructor
public class Booking {
    @Id
    private ObjectId id = new ObjectId();
    private Date dateCreated;
    private String typeOfPayment;
    private double total;
    private ObjectId userObjectID;
    private ObjectId paymentCardID;

    public Booking(Date dateCreated, String typeOfPayment, double total, ObjectId userObjectID, ObjectId paymentCardID) {
        this.dateCreated = dateCreated;
        this.typeOfPayment = typeOfPayment;
        this.total = total;
        this.userObjectID = userObjectID;
        this.paymentCardID = paymentCardID;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ObjectId getUserObjectID() {
        return userObjectID;
    }

    public ObjectId getPaymentCardID() {
        return paymentCardID;
    }
}
