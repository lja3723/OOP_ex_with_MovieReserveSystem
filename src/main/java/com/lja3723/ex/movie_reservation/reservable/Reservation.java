package com.lja3723.ex.movie_reservation.reservable;

import com.lja3723.ex.movie_reservation.physical.Seat;
import com.lja3723.ex.movie_reservation.reservable.screening.Screening;

public class Reservation {
	private final Customer customer;
	private final Screening screening;
	private final Seat seat;
	
	public Reservation(Customer customer, Screening screening, Seat seat) {
		this.customer = customer;
		this.screening = screening;
		this.seat = seat;
	}
}