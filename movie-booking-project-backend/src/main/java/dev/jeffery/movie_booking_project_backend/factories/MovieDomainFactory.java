package dev.jeffery.movie_booking_project_backend.factories;

import org.springframework.stereotype.Component;

import dev.jeffery.movie_booking_project_backend.builders.MovieBuilder;
import dev.jeffery.movie_booking_project_backend.builders.MovieDTOBuilder;


@Component
public interface MovieDomainFactory {

    public MovieBuilder createMovieBuilder();

    public MovieDTOBuilder createMovieDTOBuilder();
}
