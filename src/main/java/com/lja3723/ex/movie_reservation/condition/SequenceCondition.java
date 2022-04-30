package com.lja3723.ex.movie_reservation.condition;

import com.lja3723.ex.movie_reservation.reservable.Screening;

final class SequenceCondition extends DiscountCondition {
	private final int sequence;

	public SequenceCondition(int sequence) {
		this.sequence = sequence;
	}
	
	public boolean isSatisfiedBy(Screening screening) {
		return screening.isSequence(sequence);
	}
	
}