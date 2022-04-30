package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.reservable.Movie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieList {
    private final List<Movie> list;
    public MovieList(List<Movie> list) {
        this.list = list;
    }

    public void add(Movie movie) {
        if (list.contains(movie)) return;
        list.add(movie);
    }

    public Movie getMovie(String movieName) throws IllegalArgumentException {
        return getMovieByName(movieName);
    }

    public List<String> getMovieList() {
        List<String> returnList = new ArrayList<>();
        for (Movie movie: list) {
            returnList.add(movie.getName());
        }
        return returnList;
    }

    public boolean remove(String movieName) {
        return list.remove(getMovieByName(movieName));
    }


    private Movie getMovieByName(String movieName) throws IllegalArgumentException {
        for (Movie movie: list) {
            if (movie.getName().equals(movieName))
                return movie;
        }
        throw new IllegalArgumentException( String.format("Can't found movie by name \"%s\"!", movieName));
    }
}
