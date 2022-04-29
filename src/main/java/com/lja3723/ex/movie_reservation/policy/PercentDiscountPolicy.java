package com.lja3723.ex.movie_reservation.policy;

import com.lja3723.ex.movie_reservation.reservable.Screening;
import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.value.*;

import java.util.List;

final class PercentDiscountPolicy extends DiscountPolicy {
	private final Percentage percent;

	public PercentDiscountPolicy(Percentage percent, DiscountCondition... conditions) {
		super(conditions);
		this.percent = percent;
	}

	public PercentDiscountPolicy(Percentage percent, List<DiscountCondition> conditions) {
		super(conditions);
		this.percent = percent;
	}
	
	@Override
	protected Money getDiscountAmount(Screening screening) {
		return Money.ZERO;// screening.getMovieFee().times(percent);
	}
}