package com.lja3723.ex.movie_reservation.condition;

import com.lja3723.ex.movie_reservation.reservable.DiscountCondition;
import com.lja3723.ex.movie_reservation.reservable.Screening;
import com.lja3723.ex.movie_reservation.value.Sequence;

public class SequenceCondition extends DiscountCondition {
	private final Sequence sequence;

	public SequenceCondition(Sequence sequence) {
		this.sequence = sequence;
	}
	
	public boolean isSatisfiedBy(Screening screening) {
		return screening.isSequence(sequence);
	}
	
}