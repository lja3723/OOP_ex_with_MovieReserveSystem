package com.lja3723.ex.movie_reservation.condition;

import com.lja3723.ex.movie_reservation.Screening;
import com.lja3723.ex.movie_reservation.value.Sequence;
import com.lja3723.ex.movie_reservation.*;
import com.lja3723.ex.movie_reservation.value.*;

public class SequenceCondition implements DiscountCondition {
	private Sequence sequence;

	public SequenceCondition(Sequence sequence) {
		this.sequence = sequence;
	}
	
	public boolean isSatisfiedBy(Screening screening) {
		return screening.isSequence(sequence);
	}
	
}