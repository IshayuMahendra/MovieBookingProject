package dev.jeffery.movie_booking_project_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowtimeDTO {
    private String id;   
    private String time;
}