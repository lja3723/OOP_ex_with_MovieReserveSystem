package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.physical.Seat;
import com.lja3723.ex.movie_reservation.value.Money;

public class Reservation {
	private Customer customer;
	private Screening screening;
	private Seat seat;
	
	public Reservation(Customer customer, Screening screening, Seat seat) {
		this.customer = customer;
		this.screening = screening;
		this.seat = seat;
	}
}