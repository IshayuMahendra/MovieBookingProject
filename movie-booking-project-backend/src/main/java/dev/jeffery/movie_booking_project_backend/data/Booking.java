package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    private ObjectId id;

    private String dateCreated;
    private String typeOfPayment;
    private double total;

    public Booking(String dateCreated, String typeOfPayment, double total) {
        this.dateCreated = dateCreated;
        this.typeOfPayment = typeOfPayment;
        this.total = total;
    }


    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
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
}
