package com.lja3723.ex.movie_reservation;

import java.time.*;

public class Movie {
	private final String title;
	private final LocalDate releaseDate;
	private final Duration runningTime;

	public Movie(String title, LocalDate releaseDate, Duration runningTime) {//, Money fee, DiscountPolicy discountPolicy) {
		this.title = title;
		this.releaseDate = releaseDate;
		this.runningTime = runningTime;
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public Duration getRunningTime() {
		return runningTime;
	}

	@Override
	public String toString() {
		return String.format("Title: %s, ReleaseDate: %s, RunningTime: %d",
				title,
				releaseDate,
				runningTime.toMinutes()
		);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Movie movie)) {
			return false;
		}
		return movie.title.equals(this.title) && movie.releaseDate == this.releaseDate;
	}
}