package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.physical.*;
import com.lja3723.ex.movie_reservation.resource_reader.*;
import com.lja3723.ex.movie_reservation.value.Money;

import java.util.*;

public class MovieReservationSystem {
	private final MultiplexCinema cinema;
	private final List<Movie> movies;
	public MovieReservationSystem(String cinemaName, Money cinemaFinance) {
		JSONArrayReader<Movie> moviesReader = new MovieJsonReader("src/main/resources/movies.json");
		JSONArrayReader<Theatre> theatresReader = new TheatreJsonReader("src/main/resources/theatres.json");

		this.movies = moviesReader.getList();
		this.cinema = new MultiplexCinema(cinemaName, cinemaFinance, theatresReader.getList());

		printMovies(movies);
		printTheatres(cinema.getTheatres());
	}

	private void printMovies(List<Movie> movies) {
		System.out.println("[Movies]");
		for (Movie movie : movies) {
			System.out.print(movie);
		}
	}

	private void printTheatres(List<Theatre> theatres) {
		System.out.println("[Theatres]");
		for (Theatre theatre : cinema.getTheatres()) {
			System.out.print(theatre);
		}
	}

	public Reservation createReservation(Customer customer, Screening screening, Seat seat) {
		return new Reservation(customer, screening, seat);
	}

}