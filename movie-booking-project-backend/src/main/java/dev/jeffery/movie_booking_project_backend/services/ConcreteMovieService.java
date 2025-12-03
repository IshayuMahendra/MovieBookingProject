package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.*;
import dev.jeffery.movie_booking_project_backend.repositories.SeatRepository;
import dev.jeffery.movie_booking_project_backend.repositories.ShowroomRepository;
import dev.jeffery.movie_booking_project_backend.builders.ConcreteMovieBuilder;
import dev.jeffery.movie_booking_project_backend.builders.ConcreteMovieDTOBuilder;
import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;
import dev.jeffery.movie_booking_project_backend.dto.ShowtimeDTO;
import dev.jeffery.movie_booking_project_backend.dto.ConcreteMovieDTO;
import dev.jeffery.movie_booking_project_backend.factories.MovieDomainFactory;
import dev.jeffery.movie_booking_project_backend.repositories.MovieRepository;
import dev.jeffery.movie_booking_project_backend.repositories.ShowRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcreteMovieService implements MovieService{

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm EEE MM/dd/yyyy");

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowroomRepository showroomRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    @Qualifier("concreteMovieDomainFactory")
    public MovieDomainFactory factory;

    @Autowired
    private AdminService adminService;


    // Create new movie based on admin input
    public ConcreteMovie createMovie(Movie movie){
        ConcreteMovie concreteMovie = (ConcreteMovie) movie;
        ConcreteMovieBuilder builder = (ConcreteMovieBuilder) factory.createMovieBuilder();
        ConcreteMovie newMovie = builder.title(concreteMovie.getTitle())
                            .genre(concreteMovie.getGenre())
                            .poster(concreteMovie.getPoster())
                            .trailer(concreteMovie.getTrailer())
                            .description(concreteMovie.getDescription())
                            .rating(concreteMovie.getRating())
                            .isRunning(concreteMovie.getIsRunning())
                            .build();
        movieRepository.save(newMovie);
        return newMovie;
    }

    // get all showrooms
    public List<Showroom> getShowrooms(){
        List<Showroom> allRooms = showroomRepository.findAll();
        return allRooms;
    }

    // Add showtimes for a movie based on admin input
    public String addShowtime(Show showtime){
        if (movieRepository.findById(showtime.getMovieID()).isEmpty()) {
            return "This movie id does not exist. Please enter a valid movie id.";
        }
        List<Show> otherShowtimes = showRepository.findByShowroomID(showtime.getShowroomID());
        boolean conflicts = false;
        for (Show s : otherShowtimes){
            if(conflicts(s, showtime)){
                conflicts = true;
                break;
            }
        }

        if(conflicts){
            return "This time conflicts with another movie!";
        } else{
            System.out.println(showtime.getMovieID());
            showRepository.save(showtime);
            return "Showtime has been added!";
        }
    }

    // checks to see if a newShow being added conflicts with an existing show
    public boolean conflicts(Show existingShow, Show newShow) {

        Instant existingStart = existingShow.getShowTime().toInstant();
        Instant existingEnd = existingStart.plus(existingShow.getDuration(), ChronoUnit.MINUTES);

        Instant newStart = newShow.getShowTime().toInstant();
        Instant newEnd = newStart.plus(newShow.getDuration(), ChronoUnit.MINUTES);

        return newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
    }

    @Override
    public List<ConcreteMovie> allMovies(){
        return movieRepository.findAll();
    }

    // New method to return movies with showtimes
    @Override
    public List<MovieDTO> allMoviesWithShowtimes() {
        //this bit was for testing
//        createMovie("The Wolf of Wall Street", List.of("Crime", "Drama", "Comedy"), "https://media.themoviedb.org/t/p/w600_and_h900_bestv2/kW9LmvYHAaS9iA0tHmZVq8hQYoq.jpg",
//                "https://www.youtube.com/watch?v=Slj4-Sv-YNA", "A New York stockbroker refuses to cooperate in a large securities fraud case involving corruption on Wall Street, corporate banking world and mob infiltration. Based on Jordan Belfort's autobiography.",
//                80, true);

//        try{
//            adminService.addPromotion(new Promotion(20, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2025-12-30 18:00")));
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        try{
//            addShowtime(new Show(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2025-12-30 18:00"), 179,
//                    new ObjectId("68fbc15ada87bc90f1edbffa"), new ObjectId("69150b7912aa7ea4f453466d")));
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }

//        List<Seat> seats = List.of(
//               // Row A
//               new Seat("A", 1, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("A", 2, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("A", 3, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("A", 4, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("A", 5, new ObjectId("6924f93b983df7b84237fff7")),
//
//               // Row B
//               new Seat("B", 1, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("B", 2, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("B", 3, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("B", 4, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("B", 5, new ObjectId("6924f93b983df7b84237fff7")),
//
//               // Row C
//               new Seat("C", 1, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("C", 2, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("C", 3, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("C", 4, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("C", 5, new ObjectId("6924f93b983df7b84237fff7")),
//
//               // Row D
//               new Seat("D", 1, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("D", 2, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("D", 3, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("D", 4, new ObjectId("6924f93b983df7b84237fff7")),
//               new Seat("D", 5, new ObjectId("6924f93b983df7b84237fff7"))
//       );
//       seatRepository.saveAll(seats);
//
//        List<Seat> seats1 = List.of(
//                // Row A
//                new Seat("A", 1, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("A", 2, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("A", 3, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("A", 4, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("A", 5, new ObjectId("6924f93b983df7b84237fff6")),
//
//                // Row B
//                new Seat("B", 1, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("B", 2, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("B", 3, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("B", 4, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("B", 5, new ObjectId("6924f93b983df7b84237fff6")),
//
//                // Row C
//                new Seat("C", 1, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("C", 2, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("C", 3, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("C", 4, new ObjectId("6924f93b983df7b84237fff6")),
//                new Seat("C", 5, new ObjectId("6924f93b983df7b84237fff6"))
//        );
//        seatRepository.saveAll(seats1);
//
//        List<Seat> seats2 = List.of(
//                // Row A
//                new Seat("A", 1, new ObjectId("6924f93b983df7b84237fff5")),
//                new Seat("A", 2, new ObjectId("6924f93b983df7b84237fff5")),
//                new Seat("A", 3, new ObjectId("6924f93b983df7b84237fff5")),
//                new Seat("A", 4, new ObjectId("6924f93b983df7b84237fff5")),
//                new Seat("A", 5, new ObjectId("6924f93b983df7b84237fff5")),
//
//                // Row B
//                new Seat("B", 1, new ObjectId("6924f93b983df7b84237fff5")),
//                new Seat("B", 2, new ObjectId("6924f93b983df7b84237fff5")),
//                new Seat("B", 3, new ObjectId("6924f93b983df7b84237fff5")),
//                new Seat("B", 4, new ObjectId("6924f93b983df7b84237fff5")),
//                new Seat("B", 5, new ObjectId("6924f93b983df7b84237fff5"))
//        );
//        seatRepository.saveAll(seats2);

        List<ConcreteMovie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> {
            List<Show> shows = showRepository.findByMovieID(movie.getId());
            List<ShowtimeDTO> showtimes = shows.stream()
                            .map(show -> new ShowtimeDTO(
                                    show.getId().toHexString(),
                                    timeFormat.format(show.getShowTime())
                            ))
                            .collect(Collectors.toList());

            ConcreteMovieDTOBuilder builder = (ConcreteMovieDTOBuilder) factory.createMovieDTOBuilder();
            ConcreteMovieDTO result = builder.id(movie.getId().toHexString())           
                                .title(movie.getTitle())
                                .genre(movie.getGenre())
                                .poster(movie.getPoster())
                                .trailer(movie.getTrailer())
                                .description(movie.getDescription())
                                .rating(movie.getRating())
                                .isRunning(movie.getIsRunning())
                                .showtimes(showtimes)        
                                .build();
            return result;
        }).collect(Collectors.toList());
    }

    // Existing search methods can stay, but if you want showtimes included, create similar versions returning MovieDTO
    @Override
    public List<MovieDTO> searchMoviesWithShowtimes(String input, String genre) {
        List<MovieDTO> allAbstract = allMoviesWithShowtimes();
        List<ConcreteMovieDTO> all = allAbstract.stream()
                                         .map(dto -> (ConcreteMovieDTO) dto)
                                         .collect(Collectors.toList());
        // Filter by title
        if (input != null && !input.trim().isEmpty()) {
            all = all.stream()
                    .filter(m -> m.getTitle().toLowerCase().contains(input.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Filter by genre
        if (genre != null && !genre.equalsIgnoreCase("Any")) {
            all = all.stream()
                    .filter(m -> m.getGenre().stream().anyMatch(g -> g.equalsIgnoreCase(genre)))
                    .collect(Collectors.toList());
        }

        return all.stream()
                   .map(dto -> (MovieDTO) dto)
                   .collect(Collectors.toList());
    }

    @Override
    public Optional<Movie> getMovieById(String id) {
        return movieRepository.findById(new ObjectId(id))
                   .map(movie -> (Movie) movie);
    }

    @Override
    public Optional<Movie> getMovieByTimestamp(long timestamp) {
        List<ConcreteMovie> all = allMovies(); // fetch all movies
        for (ConcreteMovie m : all) {
            if (m.getId().getTimestamp() == timestamp) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }
}
