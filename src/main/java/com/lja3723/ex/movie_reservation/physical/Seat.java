package com.lja3723.ex.movie_reservation.physical;

public class Seat {
	private final Theatre theatre; //좌석이 소속된 극장
	private final char row;
	private final int column;
	private boolean reservation;
	
	public Seat(Theatre theatre, char row, int column) {
		this.theatre = theatre;
		this.row = Character.toUpperCase(row);
		this.column = column;
		this.reservation = false;
	}

	public Seat(Theatre theatre, int row, int column) {
		this(theatre, "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(row - 1), column);
	}

	//format: A1, D15, etc.
	public Seat(Theatre theatre, String seatPosition) {
		this(theatre, parseRow(seatPosition), parseColumn(seatPosition));
	}

	private static char parseRow(String seatPosition) {
		return seatPosition.charAt(0);
	}

	private static int parseColumn(String seatPosition) {
		return Integer.parseInt(seatPosition.substring(1));
	}

	public final Theatre getTheatre() {
		return theatre;
	}
	public char getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void reserve() {
		this.reservation = true;
	}

	public void cancelReserve() {
		this.reservation = false;
	}

	public boolean isReserved() {
		return this.reservation;
	}

	@Override
	public String toString() {
		return String.format("%c%d", row, column);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Seat seat)) {
			return false;
		}

		return seat.row == this.row && seat.column == this.column;
	}

	public boolean equals(String seatPosition) {
		return equals(new Seat(null, seatPosition));
	}
}