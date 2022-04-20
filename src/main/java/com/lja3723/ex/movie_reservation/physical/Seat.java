package com.lja3723.ex.movie_reservation.physical;

public class Seat {
	private char row;
	private int column;
	private boolean reservation;
	
	public Seat(char row, int column) {
		this.row = row;
		this.column = column;
		this.reservation = false;
	}

	//format: A1, D15, etc.
	public Seat(String seat) {
		this(seat.charAt(0), Integer.parseInt(seat.substring(1)));
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
		return (new StringBuilder())
			.append(row)
			.append(column)
			.toString();
	}
}