package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.physical.MultiplexCinema;
import com.lja3723.ex.movie_reservation.physical.Theatre;
import com.lja3723.ex.movie_reservation.value.Money;

import java.util.List;

public class MainEntry {
	public static void main(String[] args) {
		MovieReservationSystem MRS = new MovieReservationSystem("종합상영관 가상점", Money.wons(50_000_000));
		System.out.println("program exits");
	}
}