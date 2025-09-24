package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.services.MovieService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://127.0.0.1:5500")

public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK);
    }

    @GetMapping("/title/{input}")
    public ResponseEntity<List<Movie>> getMovieByTitle(@PathVariable String input) {
        List<Movie> result = movieService.searchMoviesByTitle(movieService.allMovies(), input);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre) {
        List<Movie> result = movieService.searchMoviesByGenre(movieService.allMovies(), genre);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @GetMapping("/{input}/{genre}")
    public ResponseEntity<List<Movie>> getMovie(@PathVariable String input, @PathVariable String genre){
        return new ResponseEntity<List<Movie>>(movieService.searchMovies(input, genre), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Movie>> getMovieById(@PathVariable String id){
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @GetMapping("/timestamp/{timestamp}")
    public ResponseEntity<Movie> getMovieByTimestamp(@PathVariable long timestamp) {
        Optional<Movie> movie = movieService.getMovieByTimestamp(timestamp);
        return movie.map(m -> ResponseEntity.ok(m)) 
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
}
