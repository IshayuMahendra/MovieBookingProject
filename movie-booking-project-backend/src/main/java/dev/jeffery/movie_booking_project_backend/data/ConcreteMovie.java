package dev.jeffery.movie_booking_project_backend.data;

import dev.jeffery.movie_booking_project_backend.data.Movie;
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
@NoArgsConstructor
public class ConcreteMovie implements Movie {
    @Id
    private ObjectId id = new ObjectId();
    private String title;
    private List<String> genre;
    private String poster;
    private String trailer;
    private String description;
    private int rating;
    private boolean isRunning;
    // private List<String> cast;
    // private String director;
    // private String producer;
    // private String maturityRating;

    public void initialize(String title,
                 List<String> genre,
                 String poster,
                 String trailer,
                 String description,
                 int rating,
                 boolean isRunning) {
        this.title = title;
        this.genre = genre;
        this.poster = poster;
        this.trailer = trailer;
        this.description = description;
        this.rating = rating;
        this.isRunning = isRunning;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public boolean getIsRunning() {
        return isRunning;
    }
}
