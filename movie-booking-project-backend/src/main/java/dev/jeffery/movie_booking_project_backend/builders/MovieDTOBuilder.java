package dev.jeffery.movie_booking_project_backend.builders;

import dev.jeffery.movie_booking_project_backend.builders.MovieDTOBuilder;

import java.util.List;

import org.bson.types.ObjectId;

public interface MovieDTOBuilder {
    public MovieDTOBuilder id(String id);
    public MovieDTOBuilder title(String title);
    public MovieDTOBuilder genre(List<String> genre);
}
