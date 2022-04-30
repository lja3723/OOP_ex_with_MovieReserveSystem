package com.lja3723.ex.movie_reservation.reservable;

import org.jetbrains.annotations.Nullable;

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

    @Nullable
    public Movie get(String movieName) {
        return getMovieByName(movieName);
    }

    public boolean remove(String movieName) {
        return list.remove(getMovieByName(movieName));
    }

    @Nullable
    private Movie getMovieByName(String movieName) {
        for (Movie movie: list) {
            if (movie.getName().equals(movieName))
                return movie;
        }
        return null;
    }
}
