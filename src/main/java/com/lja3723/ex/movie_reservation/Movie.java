package com.lja3723.ex.movie_reservation;

import java.time.*;

import com.lja3723.ex.movie_reservation.policy.DiscountPolicy;
import com.lja3723.ex.movie_reservation.value.Money;
import com.lja3723.ex.movie_reservation.value.*;
import com.lja3723.ex.movie_reservation.policy.*;

public class Movie {
	private String title;
	private LocalDate releaseDate;
	private Duration runningTime;
	//private Money fee;
	//private DiscountPolicy discountPolicy;

	public Movie(String title, LocalDate releaseDate, Duration runningTime) {//, Money fee, DiscountPolicy discountPolicy) {
		this.title = title;
		this.releaseDate = releaseDate;
		this.runningTime = runningTime;
		//this.fee = fee;
		//this.discountPolicy = discountPolicy;
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

	
	//public Money getFee() {
	//	return fee;
	//}

	//public Money calculateMovieFee(Screening screening) {
	//	return fee.minus(discountPolicy.calculateDiscountAmount(screening));
	//}
}