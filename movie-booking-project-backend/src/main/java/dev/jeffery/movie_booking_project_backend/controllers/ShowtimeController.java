package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.dto.SeatAvailabilityDTO;
import dev.jeffery.movie_booking_project_backend.services.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class ShowtimeController {
 
       private final BookingService bookingService;

    public ShowtimeController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{showId}/seat-map")
    public List<SeatAvailabilityDTO> getSeatMapByShow(@PathVariable String showId) {
        return bookingService.getSeatMapByShow(showId);
    }
    

}

