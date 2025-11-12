package dev.jeffery.movie_booking_project_backend.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Show")
@Data
@NoArgsConstructor
public class Show {
    @Id
    private ObjectId id = new ObjectId();
    private Date showTime;
    private int duration;
    private ObjectId showroomID;
    private ObjectId movieID;

    public Show(Date showTime, int duration, ObjectId showroomID, ObjectId movieID) {
        this.showTime = showTime;
        this.duration = duration;
        this.showroomID = showroomID;
        this.movieID = movieID;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ObjectId getShowroomID() {
        return showroomID;
    }

    public ObjectId getMovieID() {
        return movieID;
    }
}
