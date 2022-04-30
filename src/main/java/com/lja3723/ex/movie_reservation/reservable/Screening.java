package com.lja3723.ex.movie_reservation.reservable;

import java.time.*;
import com.lja3723.ex.movie_reservation.physical.Theatre;

public class Screening {
	private final Movie movie;
	private final int sequence;
	private final LocalDateTime whenScreened;
	private final Theatre theatre;

	public Screening(Movie movie, int sequence, LocalDateTime whenScreened, Theatre theatre) {
		this.movie = movie;
		this.sequence = sequence;
		this.whenScreened = whenScreened;
		this.theatre = theatre;
	}

	public boolean isMovieNameEquals(String movieName) {
		return movie.getName().equals(movieName);
	}

	public boolean isTheatreNumberEquals(int number) {
		return theatre.getNumber() == number;
	}

	public LocalDateTime getScreenedDateTime() {
		return whenScreened;
	}

	public boolean isScreeningTimeBetween(LocalTime startTime, LocalTime endTime) {
		LocalTime screeningTime = whenScreened.toLocalTime();
		return startTime.isBefore(screeningTime) && endTime.isAfter(screeningTime);
	}

	public boolean isDayOfWeekEquals(DayOfWeek dayOfWeek) {
		return whenScreened.getDayOfWeek().equals(dayOfWeek);
	}




	public boolean isSequence(int sequence) {
		return this.sequence == sequence;
	}

	@Override
	public String toString() {
		return String.format("Screened Movie: %s, ", movie.getName()) +
		String.format("Theatre number: %d, ", theatre.getNumber()) +
		String.format("Screening Time: [ Date: %s; Time: %s; Sequence: %d; ]",
				whenScreened.toLocalDate().toString(), whenScreened.toLocalTime().toString(), sequence);
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