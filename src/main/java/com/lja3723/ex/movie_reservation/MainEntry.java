package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.value.Money;
import com.lja3723.ex.movie_reservation.cli.*;

public class MainEntry {
	public static void main(String[] args) {
		CLIView view = new CLIView(new CLIController(new MovieReservationSystem("종합상영관 가상점", Money.wons(50_000_000))));

		while (!view.isExit()) {
			view.getClientMessage();
			view.updateUI();
		}
	}
}