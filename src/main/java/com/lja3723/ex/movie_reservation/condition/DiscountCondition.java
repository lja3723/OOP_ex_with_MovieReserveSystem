package com.lja3723.ex.movie_reservation.condition;

import com.lja3723.ex.movie_reservation.Screening;
import com.lja3723.ex.movie_reservation.*;

public interface DiscountCondition {
	boolean isSatisfiedBy(Screening screening);
}