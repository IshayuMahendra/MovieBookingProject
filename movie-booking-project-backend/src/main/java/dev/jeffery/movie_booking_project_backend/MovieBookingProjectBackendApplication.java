package dev.jeffery.movie_booking_project_backend;

import dev.jeffery.movie_booking_project_backend.data.*;
import dev.jeffery.movie_booking_project_backend.repositories.*;
import dev.jeffery.movie_booking_project_backend.services.AdminService;
import dev.jeffery.movie_booking_project_backend.services.PaymentCardService;
import dev.jeffery.movie_booking_project_backend.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@SpringBootApplication
public class MovieBookingProjectBackendApplication // implements CommandLineRunner
{

	public static void main(String[] args) {
        SpringApplication.run(MovieBookingProjectBackendApplication.class, args);
	}

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private MovieRepository movieRepository;
//
//    @Autowired
//    private CinemaRepository cinemaRepository;
//
//    @Autowired
//    private TheaterRepository theaterRepository;
//
//    @Autowired
//    private TicketRepository ticketRepository;
//
//    @Autowired
//    private ShowRepository showRepository;
//
//    @Autowired
//    private SeatRepository seatRepository;
//
//    @Autowired
//    private ShowroomRepository showroomRepository;
//
//    @Autowired
//    private PromotionRepository promotionRepository;
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private AdminService adminService;
//
//    @Autowired
//    private PaymentCardService paymentCardService;
//
//    public void run(String... args) throws Exception {
//

//        Cinema AMC = new Cinema("AMC");
//        cinemaRepository.save(AMC);
//
//        Theater atlantaTheater = new Theater("Atlanta TheaterRepository", "909 Ponce De Leon",
//                "404-111-1111", AMC.getId());
//
//        theaterRepository.save(atlantaTheater);
//
//        Showroom twenty = new Showroom(20, atlantaTheater.getId());
//        showroomRepository.save(twenty);
//
//        List<Seat> seats = List.of(
//                // Row A
//                new Seat("A", 1, twenty.getId()),
//                new Seat("A", 2, twenty.getId()),
//                new Seat("A", 3, twenty.getId()),
//                new Seat("A", 4, twenty.getId()),
//                new Seat("A", 5, twenty.getId()),
//
//                // Row B
//                new Seat("B", 1, twenty.getId()),
//                new Seat("B", 2, twenty.getId()),
//                new Seat("B", 3, twenty.getId()),
//                new Seat("B", 4, twenty.getId()),
//                new Seat("B", 5, twenty.getId()),
//
//                // Row C
//                new Seat("C", 1, twenty.getId()),
//                new Seat("C", 2, twenty.getId()),
//                new Seat("C", 3, twenty.getId()),
//                new Seat("C", 4, twenty.getId()),
//                new Seat("C", 5, twenty.getId()),
//
//                // Row D
//                new Seat("D", 1, twenty.getId()),
//                new Seat("D", 2, twenty.getId()),
//                new Seat("D", 3, twenty.getId()),
//                new Seat("D", 4, twenty.getId()),
//                new Seat("D", 5, twenty.getId())
//        );
//        seatRepository.saveAll(seats);
//
//        Movie godfather = new Movie("The Godfather", List.of("Crime", "Drama"), "https://media.themoviedb.org/t/p/original/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
//                "https://www.youtube.com/watch?v=UaVTIH8mujA", "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
//                87, true);
//        movieRepository.save(godfather);
//
//        Calendar cal = Calendar.getInstance();
//        cal.set(2025, Calendar.OCTOBER, 25, 20, 0, 0); // Year, Month (0-based), Day, Hour, Minute, Second
//        cal.set(Calendar.MILLISECOND, 0);
//        Show godfatherAt8 = new Show(cal.getTime(), 175, twenty.getId(), godfather.getId());
//        showRepository.save(godfatherAt8);
//
//        User user = userService.createNewUser("jfelshaw", "jf123", "jefferyfelshaw777@gmail.com",
//                "jeffery", "felshaw", "123 broad street", "marietta", "ga",
//                "30068", true);
//
//        PaymentCard newCard = paymentCardService.createNewPaymentCard("123456789", "10/29",
//                "123 braod street", user.getId());

//        Admin admin = adminService.createNewAdmin("admin3", "123");
// 	   Admin shuAdmin = adminService.createNewAdmin("shuAdmins", "shu");
//
//
//        Booking godfatherBooking = new Booking(new Date(), "Card", 24, user.getId(), newCard.getId());
//
//        Ticket jeffTicket = new Ticket(Ticket.TicketType.ADULT, godfatherBooking.getId(), seats.get(12).getId(),
//                godfatherAt8.getId());
//        ticketRepository.save(jeffTicket);
//
//        Ticket abbyTicket = new Ticket(Ticket.TicketType.ADULT, godfatherBooking.getId(), seats.get(13).getId(),
//                godfatherAt8.getId());
//        ticketRepository.save(abbyTicket);


//    }
} 		