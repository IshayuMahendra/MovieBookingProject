package dev.jeffery.movie_booking_project_backend.factories;

import org.springframework.stereotype.Component;

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
}
