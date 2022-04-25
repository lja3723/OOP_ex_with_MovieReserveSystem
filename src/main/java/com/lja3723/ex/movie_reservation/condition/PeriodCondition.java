package com.lja3723.ex.movie_reservation.condition;

import com.lja3723.ex.movie_reservation.Screening;
import com.lja3723.ex.movie_reservation.*;
import java.time.*;

public class PeriodCondition extends DiscountCondition {
	private DayOfWeek dayOfWeek;
	private LocalTime startTime;
	private LocalTime endTime;

	public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public boolean isSatisfiedBy(Screening screening) {
		LocalDateTime startTime = screening.getStartTime();
		return 
			startTime.getDayOfWeek().equals(dayOfWeek) &&
			this.startTime.compareTo(startTime.toLocalTime()) <= 0 &&
			this.endTime.compareTo(startTime.toLocalTime()) >= 0;
	}
}