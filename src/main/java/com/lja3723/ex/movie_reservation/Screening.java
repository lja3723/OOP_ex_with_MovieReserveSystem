package com.lja3723.ex.movie_reservation;

import java.time.*;

import com.lja3723.ex.movie_reservation.physical.Theatre;
import com.lja3723.ex.movie_reservation.value.Money;
import com.lja3723.ex.movie_reservation.value.Sequence;

public class Screening {
	private Movie movie;
	private Sequence sequence;
	private LocalDateTime whenScreened;
	private Theatre theatre;

	public Screening(Movie movie, Sequence sequence, LocalDateTime whenScreened, Theatre theatre) {
		this.movie = movie;
		this.sequence = sequence;
		this.whenScreened = whenScreened;
		this.theatre = theatre;
	}

	public LocalDateTime getStartTime() {
		return whenScreened;
	}

	public boolean isSequence(Sequence sequence) {
		return this.sequence.equals(sequence);
	}

	//public Money getMovieFee() {
	//	return movie.getFee();
	//}

	//public Reservation reserve(Customer customer, int audienceCount) {
	//	return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
	//}

	//private Money calculateFee(int audienceCount) {
	//	return movie.calculateMovieFee(this).times(audienceCount);
	//}
}