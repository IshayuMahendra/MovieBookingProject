package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.data.Show;
import dev.jeffery.movie_booking_project_backend.data.Showroom;
import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    public Movie createMovie(Movie movie);

    public List<Showroom> getShowrooms();

    public String addShowtime(Show showtime);

    public boolean conflicts(Show existingShow, Show newShow);

    public List<? extends Movie> allMovies();

    public List<? extends MovieDTO> allMoviesWithShowtimes();

    public List<? extends MovieDTO> searchMoviesWithShowtimes(String input, String genre);

    public Optional<? extends Movie> getMovieById(String id);

    public Optional<? extends Movie> getMovieByTimestamp(long timestamp);
}
