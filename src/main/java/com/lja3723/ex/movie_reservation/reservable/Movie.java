package com.lja3723.ex.movie_reservation.reservable;

import com.lja3723.ex.movie_reservation.value.Money;

import java.time.*;

public class Movie {
	private final String title;
	private final LocalDate releaseDate;
	private final Duration runningTime;

	private final Money basicPrice;
	private final DiscountPolicy discountPolicy;

	public Movie(
			String title, LocalDate releaseDate, Duration runningTime,
			Money basicPrice, DiscountPolicy discountPolicy) {
		this.title = title;
		this.releaseDate = releaseDate;
		this.runningTime = runningTime;
		this.basicPrice = basicPrice;
		this.discountPolicy = discountPolicy;
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

	public Money getBasicPrice() {
		return basicPrice;
	}

	public Money getDiscountedPrice(Screening screening) {
		return basicPrice.minus(discountPolicy.calculateDiscountAmount(screening));
	}
	@Override
	public String toString() {
		return String.format("Title: %s, ReleaseDate: %s, RunningTime: %d, BasicPrice: %s KRW",
				title,
				releaseDate,
				runningTime.toMinutes(),
				basicPrice
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