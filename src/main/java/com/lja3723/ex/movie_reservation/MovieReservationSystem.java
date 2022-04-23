package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.physical.MultiplexCinema;
import com.lja3723.ex.movie_reservation.physical.Seat;
import com.lja3723.ex.movie_reservation.physical.Theatre;
import com.lja3723.ex.movie_reservation.resource_reader.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;

public class MovieReservationSystem {
	private final MultiplexCinema cinema;
	public MovieReservationSystem(MultiplexCinema cinema) {
		this.cinema = cinema;
		initialize();
	}

	private void initialize() {
		List<Movie> movies = new MovieJsonReader("src/main/resources/movies.json").getList();
		cinema.initTheatres(new TheatreJsonReader("src/main/resources/theatres.json").getList());

		System.out.println("[Movies]");
		for (Movie movie : movies) {
			System.out.printf("Title: %s, ReleaseDate: %s, RunningTime: %d%n",
					movie.getTitle(),
					movie.getReleaseDate(),
					movie.getRunningTime().toMinutes()
			);
		}

		System.out.println("[Theatres]");
		for (Theatre theatre : cinema.getTheatres()) {
			System.out.printf("number: %d, name: %s, rows: %d, columns: %d, seats: %d%n",
					theatre.getTheatreNumber(), theatre.getTheatreName(),
					theatre.getRows(), theatre.getColumns(), theatre.getSeatsCount());
			System.out.print("seats: ");
			for (Seat seat : theatre.getSeats()) {
				System.out.printf("%s; ", seat);
			}
			System.out.println();
			System.out.print("excludedSeats: ");
			for (Seat seat : theatre.getExcludedSeats()) {
				System.out.printf("%s; ", seat);
			}
			System.out.println();
		}
	}

	public Reservation createReservation(Customer customer, Screening screening, Seat seat) {
		return new Reservation(customer, screening, seat);
	}

}