package com.lja3723.ex.movie_reservation.physical;

import java.util.*;

public class Theatre {
	private final int theatreNumber;
	private final String theatreName;
	private final int rows;
	private final int columns;
	private List<Seat> seats = new ArrayList<>();
	private List<Seat> excludedSeats;

	public Theatre(int theatreNumber, String theatreName, int rows, int columns) {
		this.theatreNumber = theatreNumber;
		this.theatreName = theatreName;
		this.rows = rows;
		this.columns = columns;
		initSeats();
	}

	private void initSeats() {
		for (int row = 1; row <= rows; row++) {
			for (int column = 1; column <= columns; column++) {
				seats.add(new Seat(this, row, column));
			}
		}
	}

	public void initExcludedSeats(List<Seat> excludedSeats) {
		if (this.excludedSeats != null) {
			return;
		}
		this.excludedSeats = excludedSeats;
		for (Seat excludedSeat : excludedSeats) {
			seats.remove(excludedSeat);
		}
	}

	public int getTheatreNumber() {
		return theatreNumber;
	}

	public final String getTheatreName() {
		return theatreName;
	}
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getSeatsCount() {
		return seats.size();
	}

	public int getExcludedSeatsCount() {
		return excludedSeats.size();
	}

	/*
	public Seat getSeat(String seatPosition) {
		int index = seats.indexOf(new Seat(this, seatPosition));
		return index != -1 ? seats.get(index) : null;
	}*/

	public final List<Seat> getSeats() {
		return seats;
	}

	public final List<Seat> getExcludedSeats() {
		return excludedSeats;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(String.format("Number: %d, Name: %s, Rows: %d, Columns: %d, SeatsCount: %d, ExcludedSeatsCount: %d%n",
				theatreNumber, theatreName, rows, columns, getSeatsCount(), getExcludedSeatsCount()));

		string.append("Seats: ");
		for (Seat seat : seats) {
			string.append(String.format("%s; ", seat));
		}
		string.append("\n");

		string.append("ExcludedSeats: ");
		for (Seat seat : excludedSeats) {
			string.append(String.format("%s; ", seat));
		}
		string.append("\n");

		return string.toString();
	}

}