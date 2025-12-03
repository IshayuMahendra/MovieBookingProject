package dev.jeffery.movie_booking_project_backend.builders;

import java.util.ArrayList;
import java.util.List;

import dev.jeffery.movie_booking_project_backend.dto.ConcreteMovieDTO;
import dev.jeffery.movie_booking_project_backend.dto.ShowtimeDTO;

public class ConcreteMovieDTOBuilder implements MovieDTOBuilder {
    private String id;
    private String title;
    private List<String> genre = new ArrayList<>();
    private String poster;
    private String trailer;
    private String description;
    private int rating;
    private boolean running;
    private List<ShowtimeDTO> showtimes = new ArrayList<>();

    public ConcreteMovieDTOBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ConcreteMovieDTOBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ConcreteMovieDTOBuilder genre(List<String> genre) {
        this.genre = genre;
        return this;
    }

    public ConcreteMovieDTOBuilder poster(String poster) {
        this.poster = poster;
        return this;
    }

    public ConcreteMovieDTOBuilder trailer(String trailer) {
        this.trailer = trailer;
        return this;
    }

    public ConcreteMovieDTOBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ConcreteMovieDTOBuilder rating(int rating) {
        this.rating = rating;
        return this;
    }

    public ConcreteMovieDTOBuilder isRunning(boolean running) {
        this.running = running;
        return this;
    }

    public ConcreteMovieDTOBuilder showtimes(List<ShowtimeDTO> showtimes) {
        this.showtimes = showtimes;
        return this;
    }

    public ConcreteMovieDTO build() {
        ConcreteMovieDTO dto = new ConcreteMovieDTO();
        dto.setId(id);
        dto.setTitle(title);
        dto.setGenre(genre);
        dto.setPoster(poster);
        dto.setTrailer(trailer);
        dto.setDescription(description);
        dto.setRating(rating);
        dto.setRunning(running);
        dto.setShowtimes(showtimes);
        return dto;
    }
}
