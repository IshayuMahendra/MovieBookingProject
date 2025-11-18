package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Promotion")
@Data
@NoArgsConstructor
public class Promotion {
    @Id
    private ObjectId id = new ObjectId();
    private int discountPercentage;
    private Date expirationDate;

    public Promotion(int discountPercentage, Date expirationDate){
        this.discountPercentage = discountPercentage;
        this.expirationDate = expirationDate;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
