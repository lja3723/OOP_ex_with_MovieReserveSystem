package com.lja3723.ex.movie_reservation;

import com.lja3723.ex.movie_reservation.reservable.screening.Screening;
import com.lja3723.ex.movie_reservation.condition.DiscountConditionBuilder;

public abstract class DiscountCondition {
	public static DiscountConditionBuilder builder() {
		return DiscountConditionBuilder.getInstance();
	}
	abstract public boolean isSatisfiedBy(Screening screening);
}