package dev.jeffery.movie_booking_project_backend.builders;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import dev.jeffery.movie_booking_project_backend.builders.MovieBuilder;
import dev.jeffery.movie_booking_project_backend.data.ConcreteMovie;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ConcreteMovieBuilder implements MovieBuilder{
    @Id
    private ObjectId id = new ObjectId();
    private String title;
    private List<String> genre = new ArrayList<>();
    private String poster;
    private String trailer;
    private String description;
    private int rating;
    private boolean isRunning;

    @Override
    public ConcreteMovieBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public ConcreteMovieBuilder genre(List<String> genre) {
        this.genre = genre;
        return this;
    }

    public ConcreteMovieBuilder poster(String poster) {
        this.poster = poster;
        return this;
    }

    public ConcreteMovieBuilder trailer(String trailer) {
        this.trailer = trailer;
        return this;
    }

    public ConcreteMovieBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ConcreteMovieBuilder rating(int rating) {
        this.rating = rating;
        return this;
    }

    public ConcreteMovieBuilder isRunning(boolean isRunning) {
        this.isRunning = isRunning;
        return this;
    }

    public ConcreteMovie build() {
        ConcreteMovie movie = new ConcreteMovie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setPoster(poster);
        movie.setTrailer(trailer);
        movie.setDescription(description);
        movie.setRating(rating);
        movie.setRunning(isRunning);
        return movie;
    }
}
