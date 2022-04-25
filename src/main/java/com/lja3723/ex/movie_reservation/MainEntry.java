package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.reservable.MovieReservationSystem;
import com.lja3723.ex.movie_reservation.value.Money;

public class MainEntry {
	public static void main(String[] args) {
		MovieReservationSystem MRS = new MovieReservationSystem("종합상영관 가상점", Money.wons(50_000_000));
		System.out.println("program exits");
	}
}