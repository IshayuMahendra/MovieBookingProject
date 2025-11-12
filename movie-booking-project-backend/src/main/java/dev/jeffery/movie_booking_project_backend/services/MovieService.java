package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    public List<? extends Movie> allMovies();

    public List<? extends MovieDTO> allMoviesWithShowtimes();

    public List<? extends MovieDTO> searchMoviesWithShowtimes(String input, String genre);

    public Optional<? extends Movie> getMovieById(String id);

    public Optional<? extends Movie> getMovieByTimestamp(long timestamp);
}
