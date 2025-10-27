package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;
import dev.jeffery.movie_booking_project_backend.data.Show;
import dev.jeffery.movie_booking_project_backend.repositories.MovieRepository;
import dev.jeffery.movie_booking_project_backend.repositories.ShowRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowRepository showRepository;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    // New method to return movies with showtimes
    public List<MovieDTO> allMoviesWithShowtimes() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> {
            List<Show> shows = showRepository.findByMovieID(movie.getId());
            List<String> showtimes = shows.stream()
                    .map(show -> timeFormat.format(show.getShowTime()))
                    .collect(Collectors.toList());

            return new MovieDTO(
                    movie.getId().toHexString(),
                    movie.getTitle(),
                    movie.getGenre(),
                    movie.getPoster(),
                    movie.getTrailer(),
                    movie.getDescription(),
                    movie.getRating(),
                    movie.getIsRunning(),
                    showtimes
            );
        }).collect(Collectors.toList());
    }

    // Existing search methods can stay, but if you want showtimes included, create similar versions returning MovieDTO
    public List<MovieDTO> searchMoviesWithShowtimes(String input, String genre) {
        List<MovieDTO> all = allMoviesWithShowtimes();

        // Filter by title
        if (input != null && !input.trim().isEmpty()) {
            all = all.stream()
                    .filter(m -> m.getTitle().toLowerCase().contains(input.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Filter by genre
        if (genre != null && !genre.equalsIgnoreCase("Any")) {
            all = all.stream()
                    .filter(m -> m.getGenre().stream().anyMatch(g -> g.equalsIgnoreCase(genre)))
                    .collect(Collectors.toList());
        }

        return all;
    }

    public Optional<Movie> getMovieById(String id) {
        return movieRepository.findById(new ObjectId(id));
    }

    public Optional<Movie> getMovieByTimestamp(long timestamp) {
        List<Movie> all = allMovies(); // fetch all movies
        for (Movie m : all) {
            if (m.getId().getTimestamp() == timestamp) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }
}
