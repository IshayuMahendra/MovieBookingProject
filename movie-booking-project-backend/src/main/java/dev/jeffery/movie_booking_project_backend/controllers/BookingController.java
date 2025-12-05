package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.data.Booking;
import dev.jeffery.movie_booking_project_backend.data.Seat;
import dev.jeffery.movie_booking_project_backend.dto.OrderHistoryDTO;
import dev.jeffery.movie_booking_project_backend.dto.SeatAvailabilityDTO;
import dev.jeffery.movie_booking_project_backend.dto.SelectSeatsRequest;
import dev.jeffery.movie_booking_project_backend.dto.StartBookingRequest;
import dev.jeffery.movie_booking_project_backend.services.BookingService;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/start")
    public Map<String, String> startBooking(@RequestBody StartBookingRequest request) {
        String bookingId = bookingService.startBooking(request);
        return Map.of("bookingId", bookingId);
    }

    @GetMapping("/{bookingId}/seats")
    public List<Seat> getAvailableSeats(@PathVariable String bookingId) {
        return bookingService.getAvailableSeats(bookingId);
    }

    @PostMapping("/{bookingId}/select-seats")
    public Map<String, String> selectSeats(
            @PathVariable String bookingId,
            @RequestBody SelectSeatsRequest request
    ) {
        bookingService.selectSeats(bookingId, request.getSeatIds());
        Booking booking = bookingService.getBooking(bookingId);
        return Map.of("status", booking.getStatus());
    }

    @GetMapping("/{bookingId}")
    public Booking getBooking(@PathVariable String bookingId) {
        return bookingService.getBooking(bookingId);
    }

    @GetMapping("/{bookingId}/seat-map")
    public List<SeatAvailabilityDTO> getSeatMap(@PathVariable String bookingId) {
        return bookingService.getSeatMap(bookingId);
    }

    @GetMapping("/{userId}/order-history")
    public List<Booking> getBookingsByUser(@PathVariable String userId) {
        return bookingService.getBookingsByUser(userId);
    }

    @GetMapping("/email/{email}/order-history")
    public List<OrderHistoryDTO> getBookingsByEmail(@PathVariable String email) {
        return bookingService.getBookingsByEmail(email);
    }


}


