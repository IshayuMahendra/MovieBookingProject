package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.data.*;
import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;
import dev.jeffery.movie_booking_project_backend.services.AdminService;
import dev.jeffery.movie_booking_project_backend.services.MovieService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    @Qualifier("concreteMovieService")
    private MovieService movieService;

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
    public ResponseEntity<?> createMovie(@RequestBody @JsonDeserialize(as = ConcreteMovie.class) ConcreteMovie movie) {
        try {
            Movie newMovie = movieService.createMovie(movie);

            return ResponseEntity.ok(newMovie);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating movie: " + e.getMessage());
        }
    }

    @PostMapping("/get-showrooms")
    public ResponseEntity<?> getShowrooms() {
        try {
            List<Showroom> showrooms = movieService.getShowrooms();
            return ResponseEntity.ok(showrooms);
        } catch (Exception e) {
            System.out.println("Error getting showrooms: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error getting showrooms: " + e.getMessage());
        }
    }

    @PostMapping("/add-showtime")
    public ResponseEntity<String> addShowtime(@RequestBody Show showtime) {
        try {
            String response = movieService.addShowtime(showtime);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding showtimes: " + e.getMessage());
        }
    }

    @PostMapping("/add-promotion")
    public ResponseEntity<?> addPromotion(@RequestBody Promotion promotion) {
        try {
            Promotion response = adminService.addPromotion(promotion);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding promotion: " + e.getMessage());
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
