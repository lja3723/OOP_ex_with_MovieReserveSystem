package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.physical.MultiplexCinema;
import com.lja3723.ex.movie_reservation.physical.Seat;
import com.lja3723.ex.movie_reservation.resource_reader.*;

import java.io.FileNotFoundException;

public class MovieReservationSystem {
	private MultiplexCinema cinema;
	public MovieReservationSystem(MultiplexCinema cinema) {
		this.cinema = cinema;
		initialize();
	}

	private void initialize() {
		String str;
		try {
			str = FileReader.getTotalStringFrom("src/main/resources/movies.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(str);
	}

	public Reservation createReservation(Customer customer, Screening screening, Seat seat) {
		return new Reservation(customer, screening, seat);
	}

}