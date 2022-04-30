package com.lja3723.ex.movie_reservation.reservable;

import com.lja3723.ex.movie_reservation.policy.DiscountPolicy;
import com.lja3723.ex.movie_reservation.value.Money;

import java.time.*;

public class Movie {
	private final String name;
	private final LocalDate releaseDate;
	private final Duration runningTime;

	private final Money basicPrice;
	private final DiscountPolicy discountPolicy;

	public Movie(
			String name, LocalDate releaseDate, Duration runningTime,
			Money basicPrice, DiscountPolicy discountPolicy) {
		this.name = name;
		this.releaseDate = releaseDate;
		this.runningTime = runningTime;
		this.basicPrice = basicPrice;
		this.discountPolicy = discountPolicy;
	}

	public String getName() {
		return name;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public Duration getRunningTime() {
		return runningTime;
	}

	public Money getBasicPrice() {
		return basicPrice;
	}

	public Money getDiscountedPrice(Screening screening) {
		Money discountedPrice = screening.isMovieNameEquals(name) ?
				discountPolicy.calculateDiscountAmount(basicPrice, screening) : Money.ZERO;

		return basicPrice.minus(discountedPrice);
	}
	@Override
	public String toString() {
		return String.format("Title: %s, ReleaseDate: %s, RunningTime: %d, BasicPrice: %s KRW",
				name,
				releaseDate,
				runningTime.toMinutes(),
				basicPrice
		);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Movie movie)) return false;

		return movie.name.equals(this.name) && movie.releaseDate == this.releaseDate;
	}
}