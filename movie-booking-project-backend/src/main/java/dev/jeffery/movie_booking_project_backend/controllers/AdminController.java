package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.data.ConcreteMovie;
import dev.jeffery.movie_booking_project_backend.data.PaymentCard;
import dev.jeffery.movie_booking_project_backend.data.Show;
import dev.jeffery.movie_booking_project_backend.data.User;
import dev.jeffery.movie_booking_project_backend.services.AdminService;
import dev.jeffery.movie_booking_project_backend.services.ConcreteMovieService;
import dev.jeffery.movie_booking_project_backend.services.MovieService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ConcreteMovieService movieService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminLoginRequest request) {
        try {
            boolean authenticated = adminService.authenticateUser(request.getUserID(), request.getPassword());
            if (authenticated) {
                return ResponseEntity.ok("Admin login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid admin credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/add-movie")
    public ResponseEntity<?> createMovie(@RequestBody ConcreteMovie movie) {
        try {
            ConcreteMovie newMovie = movieService.createMovie(movie.getTitle(), movie.getGenre(), movie.getPoster(),
                    movie.getTrailer(), movie.getDescription(), movie.getRating(), movie.getIsRunning());

            return ResponseEntity.ok(newMovie);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating movie: " + e.getMessage());
        }
    }

    @PostMapping("/add-showtimes")
    public ResponseEntity<String> addShowtimes(@RequestBody List<Show> showtimes) {
        try {
            String response = movieService.addShowtimes(showtimes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding showtimes: " + e.getMessage());
        }
    }

    @Data
    @AllArgsConstructor
    static class AdminLoginRequest {
        private String userID;
        private String password;


        public String getUserID() {return userID;}

        public String getPassword() {return password;}
    }
}
