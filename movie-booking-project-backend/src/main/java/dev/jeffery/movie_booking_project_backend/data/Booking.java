package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

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
    private ObjectId showId;
    private List<String> selectedSeats;
    private List<String> ageCategories;
    private String status;
    private List<ObjectId> ticketIds;
    private String email;



    public Booking(Date dateCreated, String typeOfPayment, double total, ObjectId userObjectID, ObjectId paymentCardID) {
        this.dateCreated = dateCreated;
        this.typeOfPayment = typeOfPayment;
        this.total = total;
        this.userObjectID = userObjectID;
        this.paymentCardID = paymentCardID;
    }

    public List<ObjectId> getTicketIds() { return ticketIds; }

    public void setTicketIds(List<ObjectId> ticketIds) { this.ticketIds = ticketIds; }

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

    public ObjectId getShowId() {return showId; }

    public void setShowId(ObjectId showId) { this.showId = showId; }

    public List<String> getSelectedSeats() { return selectedSeats; }

    public void setSelectedSeats(List<String> selectedSeats) { this.selectedSeats = selectedSeats; }

    public List<String> getAgeCategories() { return ageCategories; }

    public void setAgeCategories(List<String> ageCategories) { this.ageCategories = ageCategories; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status;}

    public void setUserObjectID(ObjectId userObjectID) {
        this.userObjectID = userObjectID;
    }

    public void setPaymentCardID(ObjectId paymentCardID) {
        this.paymentCardID = paymentCardID;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

}
