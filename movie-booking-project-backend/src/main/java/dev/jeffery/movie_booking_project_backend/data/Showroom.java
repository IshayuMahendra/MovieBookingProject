package dev.jeffery.movie_booking_project_backend.data;

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

    public Showroom(int setaCount, ObjectId theaterID) {
        this.seatCount = setaCount;
        this.theaterID = theaterID;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getSetaCount() {
        return seatCount;
    }

    public void setSetaCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public ObjectId getTheaterID() {
        return theaterID;
    }
}
