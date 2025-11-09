package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.services.MovieService;
import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.builders.ConcreteMovieDTOBuilder;
import dev.jeffery.movie_booking_project_backend.data.ConcreteMovie;
import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;
import dev.jeffery.movie_booking_project_backend.dto.ConcreteMovieDTO;
import dev.jeffery.movie_booking_project_backend.factories.MovieDomainFactory;
import dev.jeffery.movie_booking_project_backend.factories.ConcreteMovieDomainFactory;
import dev.jeffery.movie_booking_project_backend.data.Show;
import dev.jeffery.movie_booking_project_backend.repositories.MovieRepository;
import dev.jeffery.movie_booking_project_backend.repositories.ShowRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcreteMovieService implements MovieService{

    private final MovieDomainFactory factory;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    public ConcreteMovieService(MovieDomainFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<ConcreteMovie> allMovies(){
        return movieRepository.findAll();
    }

    // New method to return movies with showtimes
    @Override
    public List<MovieDTO> allMoviesWithShowtimes() {
        List<ConcreteMovie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> {
            List<Show> shows = showRepository.findByMovieID(movie.getId());
            List<String> showtimes = shows.stream()
                    .map(show -> timeFormat.format(show.getShowTime()))
                    .collect(Collectors.toList());

            ConcreteMovieDTOBuilder builder = (ConcreteMovieDTOBuilder) factory.createMovieDTOBuilder();
            ConcreteMovieDTO result = builder.id(movie.getId().toHexString())           
                                .title(movie.getTitle())
                                .genre(movie.getGenre())
                                .poster(movie.getPoster())
                                .trailer(movie.getTrailer())
                                .description(movie.getDescription())
                                .rating(movie.getRating())
                                .running(movie.getIsRunning())
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
