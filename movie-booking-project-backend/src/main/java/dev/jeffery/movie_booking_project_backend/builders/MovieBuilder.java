package dev.jeffery.movie_booking_project_backend.builders;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public interface MovieBuilder {
    public MovieBuilder title(String title);
    public MovieBuilder genre(List<String> genre);
}
