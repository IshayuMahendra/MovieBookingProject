package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.Movie;
import dev.jeffery.movie_booking_project_backend.repositories.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    public List<Movie> searchMovies(String input, String genre){
        System.out.println(input);
        System.out.println(genre);
        System.out.println();
        List<Movie> byTitle = searchMoviesByTitle(allMovies(), input);
        List<Movie> byGenre = searchMoviesByGenre(byTitle, genre);
        return byGenre;
    }

    public List<Movie> searchMoviesByTitle(List<Movie> movies, String input){
        // if no user input (or whitespace only)
        if (input == null || input.trim().isEmpty()){
            return movies;
        }

        List<Movie> matchingMovies = new ArrayList<Movie>();
        for (Movie m : movies){
            if (m.getTitle().toLowerCase().contains(input.toLowerCase())){
                matchingMovies.add(m);
            }
        }

        return matchingMovies;
    }

    public List<Movie> searchMoviesByGenre(List<Movie> movies, String genre){
        //if no specific genre selected
        if (genre == null || genre.equalsIgnoreCase("Any") || movies.isEmpty()){
            return movies;
        }

        List<Movie> matchingMovies = new ArrayList<Movie>();
        for (Movie m : movies){
            for (String g : m.getGenre()){
                if (g.equalsIgnoreCase(genre)){
                    matchingMovies.add(m);
                    break;
                }
            }
        }

        return matchingMovies;
    }

}