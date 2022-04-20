package com.lja3723.ex.movie_reservation.policy;

import com.lja3723.ex.movie_reservation.Screening;
import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.value.Money;
import com.lja3723.ex.movie_reservation.value.Percentage;

public class PercentDiscountPolicy extends DiscountPolicy {
	private Percentage percent;

	public PercentDiscountPolicy(Percentage percent, DiscountCondition... conditions) {
		super(conditions);
		this.percent = percent;
	}
	
	@Override
	protected Money getDiscountAmount(Screening screening) {
		return Money.ZERO;// screening.getMovieFee().times(percent);
	}
}