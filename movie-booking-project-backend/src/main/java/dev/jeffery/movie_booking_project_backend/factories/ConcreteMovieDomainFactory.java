package dev.jeffery.movie_booking_project_backend.factories;

import org.springframework.stereotype.Component;

import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.data.ConcreteMovie;
import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;
import dev.jeffery.movie_booking_project_backend.dto.ConcreteMovieDTO;
import dev.jeffery.movie_booking_project_backend.services.MovieService;
import dev.jeffery.movie_booking_project_backend.services.ConcreteMovieService;
import dev.jeffery.movie_booking_project_backend.builders.ConcreteMovieBuilder;
import dev.jeffery.movie_booking_project_backend.builders.ConcreteMovieDTOBuilder;
import dev.jeffery.movie_booking_project_backend.builders.MovieBuilder;
import dev.jeffery.movie_booking_project_backend.builders.MovieDTOBuilder;


@Component
public class ConcreteMovieDomainFactory implements MovieDomainFactory {

    @Override
    public MovieBuilder createMovieBuilder() {
        return new ConcreteMovieBuilder();
    }

    @Override
    public MovieDTOBuilder createMovieDTOBuilder() {
        return new ConcreteMovieDTOBuilder();
    }

    @Override
    public MovieService createMovieService(MovieDomainFactory factory) {
        return new ConcreteMovieService(factory);
    }
}
