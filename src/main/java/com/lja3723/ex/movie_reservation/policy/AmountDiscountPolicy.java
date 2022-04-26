package com.lja3723.ex.movie_reservation.policy;

import com.lja3723.ex.movie_reservation.DiscountPolicy;
import com.lja3723.ex.movie_reservation.reservable.screening.Screening;
import com.lja3723.ex.movie_reservation.DiscountCondition;
import com.lja3723.ex.movie_reservation.value.Money;

import java.util.List;

public class AmountDiscountPolicy extends DiscountPolicy {
	private final Money discountAmount;

	public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
		super(conditions);
		this.discountAmount = discountAmount;
	}

	public AmountDiscountPolicy(Money discountAmount, List<DiscountCondition> conditions) {
		super(conditions);
		this.discountAmount = discountAmount;
	}

	@Override
	protected Money getDiscountAmount(Screening screening) {
		return discountAmount;
	}
}