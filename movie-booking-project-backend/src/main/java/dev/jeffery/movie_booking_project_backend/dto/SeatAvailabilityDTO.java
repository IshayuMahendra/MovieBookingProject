package dev.jeffery.movie_booking_project_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatAvailabilityDTO {
    private String id;
    private String row;
    private int number;
    private boolean available;
}