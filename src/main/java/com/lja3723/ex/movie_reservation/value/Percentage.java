package com.lja3723.ex.movie_reservation.value;

public class Percentage {
	private double percent;

	public Percentage(double percent) {
		this.percent = percent;
	}

	public double value() {
		return percent / 100.0;
	}

	
}