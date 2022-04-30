package com.lja3723.ex.movie_reservation.policy;

import com.lja3723.ex.movie_reservation.reservable.Screening;
import com.lja3723.ex.movie_reservation.value.Money;

final class NoneDiscountPolicy extends DiscountPolicy {
	@Override
	protected Money getDiscountAmount(Money basicPrice, Screening screening) {
		return Money.ZERO;
	}
}