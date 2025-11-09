package dev.jeffery.movie_booking_project_backend.dto;

import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@NoArgsConstructor
public class ConcreteMovieDTO implements MovieDTO{
    private String id;
    private String title;
    private List<String> genre;
    private String poster;
    private String trailer;
    private String description;
    private int rating;
    private boolean running;
    private List<String> showtimes;



    public ConcreteMovieDTO(String id, String title, List<String> genre, String poster,
                    String trailer, String description, int rating,
                    boolean running, List<String> showtimes) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.poster = poster;
        this.trailer = trailer;
        this.description = description;
        this.rating = rating;
        this.running = running;
        this.showtimes = showtimes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getGenre() { return genre; }
    public void setGenre(List<String> genre) { this.genre = genre; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    public String getTrailer() { return trailer; }
    public void setTrailer(String trailer) { this.trailer = trailer; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public boolean isRunning() { return running; }
    public void setRunning(boolean running) { this.running = running; }

    public List<String> getShowtimes() { return showtimes; }
    public void setShowtimes(List<String> showtimes) { this.showtimes = showtimes; }
}
