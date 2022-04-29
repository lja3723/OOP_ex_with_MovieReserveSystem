package com.lja3723.ex.movie_reservation.policy;

import java.util.*;

import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.policy.DiscountPolicyBuilder;
import com.lja3723.ex.movie_reservation.reservable.Screening;
import com.lja3723.ex.movie_reservation.value.Money;

public abstract class DiscountPolicy {
	private final List<DiscountCondition> conditions;

	public DiscountPolicy(DiscountCondition... conditions) {
		this.conditions = Arrays.asList(conditions);
	}
	public DiscountPolicy(List<DiscountCondition> conditions) {
		this.conditions = conditions;
	}
	
	public Money calculateDiscountAmount(Screening screening) {
		for (DiscountCondition each : conditions) {
			if (each.isSatisfiedBy(screening)) {
				return getDiscountAmount(screening);
			}
		}

		return Money.ZERO;
	}

	public static DiscountPolicyBuilder builder() {
		return DiscountPolicyBuilder.getInstance();
	}

	abstract protected Money getDiscountAmount(Screening screening);
}