package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Promotion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    @Id
    private ObjectId id;
    private String description;
    private String expirationDate;

    public Promotion(String description, String expirationDate){
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public ObjectId getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
