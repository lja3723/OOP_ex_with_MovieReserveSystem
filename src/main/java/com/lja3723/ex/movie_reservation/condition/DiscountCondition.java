package com.lja3723.ex.movie_reservation.condition;

import com.lja3723.ex.movie_reservation.Screening;
import com.lja3723.ex.movie_reservation.*;
import com.lja3723.ex.movie_reservation.policy.DiscountPolicyBuilder;

public abstract class DiscountCondition {
	public static DiscountConditionBuilder builder() {
		return DiscountConditionBuilder.getInstance();
	}
	abstract public boolean isSatisfiedBy(Screening screening);
}