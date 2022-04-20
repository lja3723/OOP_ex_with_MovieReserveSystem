package com.lja3723.ex.movie_reservation.policy;

import com.lja3723.ex.movie_reservation.Screening;
import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.value.Money;
import com.lja3723.ex.movie_reservation.*;
import com.lja3723.ex.movie_reservation.condition.*;
import com.lja3723.ex.movie_reservation.value.*;

public class AmountDiscountPolicy extends DiscountPolicy {
	private Money discountAmount;

	public AmountDiscountPolicy(Money discountAmount, DiscountCondition... conditions) {
		super(conditions);
		this.discountAmount = discountAmount;
	}
	
	@Override
	protected Money getDiscountAmount(Screening screening) {
		return discountAmount;
	}
}