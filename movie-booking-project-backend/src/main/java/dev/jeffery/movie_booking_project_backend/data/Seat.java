package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Seat")
@Data
@NoArgsConstructor
public class Seat {
    @Id
    private ObjectId id = new ObjectId();
    private String row;
    private int number;
    private ObjectId showroomID;

    public Seat(String row, int number, ObjectId showroomID) {
        this.row = row;
        this.number = number;
        this.showroomID = showroomID;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ObjectId getShowroomID() {
        return showroomID;
    }
}
