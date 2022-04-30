package com.lja3723.ex.movie_reservation.condition;

import com.lja3723.ex.movie_reservation.reservable.Screening;
import java.time.*;

final class PeriodCondition extends DiscountCondition {
	private final DayOfWeek dayOfWeek;
	private final LocalTime startTime;
	private final LocalTime endTime;

	public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public boolean isSatisfiedBy(Screening screening) {
		return screening.isDayOfWeekEquals(dayOfWeek) &&
				screening.isScreeningTimeBetween(startTime, endTime);
	}
}