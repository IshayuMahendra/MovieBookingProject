package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;
import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.services.ConcreteMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class MovieController {

    @Autowired
    private ConcreteMovieService movieService;

    // Get all movies with showtimes
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return new ResponseEntity<>((List<MovieDTO>) movieService.allMoviesWithShowtimes(), HttpStatus.OK);
    }

    // Search by title (with showtimes)
    @GetMapping("/title/{input}")
    public ResponseEntity<List<MovieDTO>> getMovieByTitle(@PathVariable String input) {
        List<MovieDTO> result = movieService.searchMoviesWithShowtimes(input, null);
        return new ResponseEntity<>((List<MovieDTO>) result, HttpStatus.OK);
    }

    // Search by genre (with showtimes)
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDTO>> getMovieByGenre(@PathVariable String genre) {
        List<MovieDTO> result = movieService.searchMoviesWithShowtimes(null, genre);
        return new ResponseEntity<>((List<MovieDTO>) result, HttpStatus.OK);
    }

    // Search by title + genre
    @GetMapping("/{input}/{genre}")
    public ResponseEntity<List<MovieDTO>> getMovie(@PathVariable String input, @PathVariable String genre) {
        List<MovieDTO> result = movieService.searchMoviesWithShowtimes(input, genre);
        return new ResponseEntity<>((List<MovieDTO>) result, HttpStatus.OK);
    }
}
