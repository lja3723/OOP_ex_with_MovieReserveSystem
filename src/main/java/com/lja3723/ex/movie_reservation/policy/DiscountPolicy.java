package com.lja3723.ex.movie_reservation.policy;

import java.util.*;

import com.lja3723.ex.movie_reservation.Screening;
import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.value.Money;
import com.lja3723.ex.movie_reservation.*;
import com.lja3723.ex.movie_reservation.condition.*;
import com.lja3723.ex.movie_reservation.value.*;

public abstract class DiscountPolicy {
	private List<DiscountCondition> conditions = new ArrayList<>();

	public DiscountPolicy(DiscountCondition... conditions) {
		this.conditions = Arrays.asList(conditions);
	}
	
	public Money calculateDiscountAmount(Screening screening) {
		for (DiscountCondition each : conditions) {
			if (each.isSatisfiedBy(screening)) {
				return getDiscountAmount(screening);
			}
		}

		return Money.ZERO;
	}

	abstract protected Money getDiscountAmount(Screening screening);
}