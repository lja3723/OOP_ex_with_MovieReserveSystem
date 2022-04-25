package com.lja3723.ex.movie_reservation.condition;

import com.lja3723.ex.movie_reservation.value.Sequence;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DiscountConditionBuilder {
    private DiscountConditionBuilder() {}
    private enum ConditionType {
        SEQUENCE, PERIOD, NONE;

        public static ConditionType convert(String type) {
            if (type.toLowerCase().equals("sequence")) {
                return SEQUENCE;
            }
            else if (type.toLowerCase().equals("period")) {
                return PERIOD;
            }
            else {
                return NONE;
            }
        }
    }

    ConditionType type = ConditionType.NONE;
    Sequence sequence;
    DayOfWeek dayOfWeek;
    LocalTime start;
    LocalTime end;

    public static DiscountConditionBuilder getInstance() {
        return new DiscountConditionBuilder();
    }

    public DiscountConditionBuilder conditionType(String type) {
        this.type = ConditionType.convert(type);
        return this;
    }

    public DiscountConditionBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public DiscountConditionBuilder dayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public DiscountConditionBuilder duration(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
        return this;
    }

    public DiscountCondition build() {
        switch(type) {
            case SEQUENCE:
                return new SequenceCondition(sequence);
            case PERIOD:
                return new PeriodCondition(dayOfWeek, start, end);
            case NONE: default:
                throw new RuntimeException("Unexpected discount condition type;");
        }
    }
}
