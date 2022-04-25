package com.lja3723.ex.movie_reservation;

import java.time.*;
import com.lja3723.ex.movie_reservation.physical.Theatre;
import com.lja3723.ex.movie_reservation.value.Sequence;

public class Screening {
	private final Movie movie;
	private final Sequence sequence;
	private final LocalDateTime whenScreened;
	private final Theatre theatre;

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

	@Override
	public String toString() {
		return String.format("Screened Movie: [ %s ]%n",
				movie.toString()) +
		String.format("Theatre info: [ name: %s, number: %d ]%n",
				theatre.getName(), theatre.getNumber()) +
		String.format("Screening Time: [ Date: %s, Time: %s, Sequence: %d ]%n",
				whenScreened.toLocalDate().toString(), whenScreened.toLocalTime().toString(), sequence.get());
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