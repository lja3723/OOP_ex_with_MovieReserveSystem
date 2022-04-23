package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.physical.MultiplexCinema;
import com.lja3723.ex.movie_reservation.physical.Seat;
import com.lja3723.ex.movie_reservation.resource_reader.*;
import java.util.*;

public class MovieReservationSystem {
	private MultiplexCinema cinema;
	public MovieReservationSystem(MultiplexCinema cinema) {
		this.cinema = cinema;
		initialize();
	}

	private void initialize() {
		JSONArrayReader movieReader = new MovieReader("src/main/resources/movies.json");
		List<Movie> movieList = movieReader.getObjectList();
		for (Movie movie : movieList) {
			System.out.printf("Title:%s, ReleaseDate:%s, RunningTime:%d%n",
					movie.getTitle(),
					movie.getReleaseDate(),
					movie.getRunningTime().getSeconds() / 60
			);
		}
	}

	public Reservation createReservation(Customer customer, Screening screening, Seat seat) {
		return new Reservation(customer, screening, seat);
	}

}