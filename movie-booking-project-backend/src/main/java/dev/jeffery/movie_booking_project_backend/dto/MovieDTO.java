package dev.jeffery.movie_booking_project_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieDTO {
    private String id;
    private String title;
    private List<String> genre;
    private String poster;
    private String trailer;
    private String description;
    private int rating;
    private boolean isRunning;
    private List<String> showtimes; // formatted showtimes
}
