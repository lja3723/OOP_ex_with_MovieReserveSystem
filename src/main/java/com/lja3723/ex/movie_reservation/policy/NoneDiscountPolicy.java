package com.lja3723.ex.movie_reservation.policy;

import com.lja3723.ex.movie_reservation.Screening;
import com.lja3723.ex.movie_reservation.value.Money;

public class NoneDiscountPolicy extends DiscountPolicy {
	@Override
	protected Money getDiscountAmount(Screening screening) {
		return Money.ZERO;
	}
}