package com.lja3723.ex.movie_reservation.value;

public class Sequence {
	private final int sequence;

	public Sequence(int sequence) {
		this.sequence = sequence;
	}

	public int get() {
		return sequence;
	}

	public boolean equals(Sequence sequence) {
		return this.sequence == sequence.sequence;
	}
}