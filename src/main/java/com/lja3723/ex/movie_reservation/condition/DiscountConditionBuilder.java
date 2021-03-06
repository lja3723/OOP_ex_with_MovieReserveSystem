package com.lja3723.ex.movie_reservation.condition;

import java.time.*;

public final class DiscountConditionBuilder {
    private DiscountConditionBuilder() {}
    private enum ConditionType { SEQUENCE, PERIOD, NONE }

    ConditionType type = ConditionType.NONE;
    int sequence;
    DayOfWeek dayOfWeek;
    LocalTime start;
    LocalTime end;

    public static DiscountConditionBuilder getInstance() {
        return new DiscountConditionBuilder();
    }

    public DiscountConditionBuilder conditionType(String type) {
        this.type = ConditionType.valueOf(type.toUpperCase());
        return this;
    }

    public DiscountConditionBuilder sequence(int sequence) {
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
        return switch (type) {
            case SEQUENCE -> new SequenceCondition(sequence);
            case PERIOD -> new PeriodCondition(dayOfWeek, start, end);
            case NONE -> throw new RuntimeException("Unexpected discount condition type;");
        };
    }
}
