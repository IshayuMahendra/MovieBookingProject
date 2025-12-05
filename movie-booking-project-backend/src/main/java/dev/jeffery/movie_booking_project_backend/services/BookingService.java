package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.*;
import dev.jeffery.movie_booking_project_backend.dto.OrderHistoryDTO;
import dev.jeffery.movie_booking_project_backend.dto.SeatAvailabilityDTO;
import dev.jeffery.movie_booking_project_backend.dto.StartBookingRequest;
import dev.jeffery.movie_booking_project_backend.repositories.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MovieRepository movieRepository;

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String startBooking(StartBookingRequest request) {
        Booking booking = new Booking();

        booking.setDateCreated(new Date());
        booking.setShowId(new ObjectId(request.getShowId().toString()));
        booking.setSelectedSeats(new ArrayList<>());
        booking.setStatus("STARTED");

        booking.setAgeCategories(
                request.getTickets()
                        .stream()
                        .map(StartBookingRequest.TicketRequest::getAgeCategory)
                        .collect(Collectors.toList())
        );

        bookingRepository.save(booking);

        return booking.getId().toHexString();
    }

    public List<Seat> getAvailableSeats(String bookingId) {

        Booking booking = bookingRepository.findById(new ObjectId(bookingId))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        ObjectId showId = booking.getShowId();
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        ObjectId showroomId = show.getShowroomID();

        List<Seat> allSeats = seatRepository.findAll()
                .stream()
                .filter(s -> s.getShowroomID().equals(showroomId))
                .toList();

        List<ObjectId> takenSeatIds = ticketRepository.findAll()
                .stream()
                .filter(t -> t.getShowID().equals(showId))
                .map(t -> t.getSeatID())
                .toList();

        return allSeats.stream()
                .filter(seat -> !takenSeatIds.contains(seat.getId()))
                .toList();
    }

    public Booking getBooking(String bookingId) {
        return bookingRepository.findById(new ObjectId(bookingId))
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public void selectSeats(String bookingId, List<String> seatIds) {

        Booking booking = bookingRepository.findById(new ObjectId(bookingId))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Show show = showRepository.findById(booking.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        ObjectId showroomId = show.getShowroomID();

        List<Seat> showroomSeats = seatRepository.findAll()
                .stream()
                .filter(s -> s.getShowroomID().equals(showroomId))
                .toList();

        List<String> validSeatIds = showroomSeats.stream()
                .map(s -> s.getId().toHexString())
                .toList();

        for (String seatId : seatIds) {
            if (!validSeatIds.contains(seatId)) {
                throw new RuntimeException("Invalid seat selection: seat does not belong to showroom");
            }
        }

        List<String> takenSeatIds = ticketRepository.findAll()
                .stream()
                .filter(t -> t.getShowID().equals(show.getId()))
                .map(t -> t.getSeatID().toHexString())
                .toList();

        for (String seatId : seatIds) {
            if (takenSeatIds.contains(seatId)) {
                throw new RuntimeException("Seat already taken: " + seatId);
            }
        }

        booking.setSelectedSeats(seatIds);
        booking.setStatus("SEATS_SELECTED");

        List<ObjectId> createdTicketIds = new ArrayList<>();

        for (int i = 0; i < seatIds.size(); i++) {
            String seatIdStr = seatIds.get(i);
            ObjectId seatId = new ObjectId(seatIdStr);

            String ageCat = booking.getAgeCategories().get(i);

            Ticket.TicketType ticketType = Ticket.TicketType.valueOf(ageCat);

            Ticket ticket = new Ticket(
                    ticketType,
                    booking.getId(),
                    seatId,
                    booking.getShowId()
            );

            ticketRepository.save(ticket);
            createdTicketIds.add(ticket.getId());
        }

        booking.setTicketIds(createdTicketIds);

        booking.setStatus("TICKETS_CREATED");

        bookingRepository.save(booking);
    }

    public List<SeatAvailabilityDTO> getSeatMap(String bookingId) {
        Booking booking = bookingRepository.findById(new ObjectId(bookingId))
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        ObjectId showId = booking.getShowId();
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        ObjectId showroomId = show.getShowroomID();


        List<Seat> allSeats = seatRepository.findAll()
                .stream()
                .filter(s -> s.getShowroomID().equals(showroomId))
                .toList();


        List<String> takenSeatIds = ticketRepository.findAll()
                .stream()
                .filter(t -> t.getShowID().equals(showId))
                .map(t -> t.getSeatID().toHexString())
                .toList();

        return allSeats.stream()
                .map(seat -> new SeatAvailabilityDTO(
                        seat.getId().toHexString(),
                        seat.getRow(),
                        seat.getNumber(),
                        !takenSeatIds.contains(seat.getId().toHexString())
                ))
                .toList();
    }

    public List<SeatAvailabilityDTO> getSeatMapByShow(String showIdStr) {
        ObjectId showId = new ObjectId(showIdStr);

            Show show = showRepository.findById(showId)
                    .orElseThrow(() -> new RuntimeException("Show not found"));
        ObjectId showroomId = show.getShowroomID();

        List<Seat> allSeats = seatRepository.findAll()
                .stream()
                .filter(s -> s.getShowroomID().equals(showroomId))
                .toList();

        List<String> takenSeatIds = ticketRepository.findAll()
                .stream()
                .filter(t -> t.getShowID().equals(showId))
                .map(t -> t.getSeatID().toHexString())
                .toList();

        return allSeats.stream()
                .map(seat -> new SeatAvailabilityDTO(
                        seat.getId().toHexString(),
                        seat.getRow(),
                        seat.getNumber(),
                        !takenSeatIds.contains(seat.getId().toHexString())
                ))
                .toList();
    }

    public List<Booking> getBookingsByUser(String userID) {
        return bookingRepository.findByUserObjectID(new ObjectId(userID));
    }

    public List<OrderHistoryDTO> getBookingsByEmail(String email) {
        List<Booking> bookings = bookingRepository.findByEmail(email);

        List<OrderHistoryDTO> orders = new ArrayList<>();
        for(Booking b : bookings){
            OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO();
            orderHistoryDTO.setBookingID(b.getId().toString());
            orderHistoryDTO.setTicketCount(b.getTicketIds().size());
            orderHistoryDTO.setTotal(b.getTotal());
            Optional<Show> show = showRepository.findById(b.getShowId());
            orderHistoryDTO.setShowTime(show.get().getShowTime().toString());
            Optional<ConcreteMovie> movie = movieRepository.findById(show.get().getMovieID());
            orderHistoryDTO.setMovieTitle(movie.get().getTitle());
            orders.add(orderHistoryDTO);
        }
        return orders;
    }

}
