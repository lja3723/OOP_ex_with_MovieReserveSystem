package com.lja3723.ex.movie_reservation.condition;

import com.lja3723.ex.movie_reservation.reservable.Screening;

public abstract class DiscountCondition {
	public static DiscountConditionBuilder builder() {
		return DiscountConditionBuilder.getInstance();
	}
	abstract public boolean isSatisfiedBy(Screening screening);
}