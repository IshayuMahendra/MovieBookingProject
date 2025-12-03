package dev.jeffery.movie_booking_project_backend.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Showroom")
@Data
@NoArgsConstructor
public class Showroom {
    @Id
    private ObjectId id = new ObjectId();
    private int seatCount;
    private ObjectId theaterID;

    public Showroom(int seatCount, ObjectId theaterID) {
        this.seatCount = seatCount;
        this.theaterID = theaterID;
    }

    @JsonProperty("id")
    public String getIdAsString() {
        return id.toHexString();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public ObjectId getTheaterID() {
        return theaterID;
    }
}
