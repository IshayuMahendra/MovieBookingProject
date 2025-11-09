package dev.jeffery.movie_booking_project_backend.factories;

import org.springframework.stereotype.Component;

import dev.jeffery.movie_booking_project_backend.builders.MovieBuilder;
import dev.jeffery.movie_booking_project_backend.builders.MovieDTOBuilder;
import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.dto.MovieDTO;
import dev.jeffery.movie_booking_project_backend.services.MovieService;

@Component
public interface MovieDomainFactory {

    public MovieBuilder createMovieBuilder();

    public MovieDTOBuilder createMovieDTOBuilder();
}
