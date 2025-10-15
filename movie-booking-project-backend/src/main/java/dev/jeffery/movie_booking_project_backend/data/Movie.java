package dev.jeffery.movie_booking_project_backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    private ObjectId id;
    private String title;
    private List<String> genre;
    private String poster;
    private String trailer;
    private List<String> showtimes;
    private String description;
    private int rating;
    // private List<String> cast;
    // private String director;
    // private String producer;
    // private String maturityRating;
    private boolean isRunning;

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenre() {
        return genre;
    }

    public String getPoster() {
        return poster;
    }

    public String getTrailer() {
        return trailer;
    }

    public List<String> getShowtimes() {
        return showtimes;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
