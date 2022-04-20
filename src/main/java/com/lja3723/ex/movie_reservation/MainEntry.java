package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.physical.MultiplexCinema;
import com.lja3723.ex.movie_reservation.physical.Seat;
import com.lja3723.ex.movie_reservation.physical.Theatre;
import com.lja3723.ex.movie_reservation.value.Money;
import com.lja3723.ex.movie_reservation.value.Sequence;

import java.time.*;

public class MainEntry {
	public static void main(String[] args) {
		MultiplexCinema cinema = new MultiplexCinema("종합상영관 가상점", Money.wons(50_000_000));
		MovieReservationSystem MRS =
				new MovieReservationSystem(cinema);

		Customer customer = new Customer("이장안", Money.wons(500_000));

		Reservation reservation = MRS.createReservation(
			customer,
			new Screening(
				new Movie( "", LocalDate.of(2000,1,1), Duration.ofMinutes(0)),
				new Sequence(1),
				LocalDateTime.now(),
				new Theatre()
			),
			new Seat("A1")
		);

	}
}