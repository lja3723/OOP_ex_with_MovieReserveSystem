package com.lja3723.ex.movie_reservation.policy;

import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.value.*;

import java.util.ArrayList;
import java.util.List;

public class DiscountPolicyBuilder {
    private DiscountPolicyBuilder() {}
    public enum PolicyType {  AMOUNT, PERCENT, NONE }
    private PolicyType policy;
    private final List<DiscountCondition> conditions = new ArrayList<>();
    private Money discountAmount = Money.ZERO;
    private Percentage discountPercent = new Percentage(0);

    public static DiscountPolicyBuilder getInstance() {
        return new DiscountPolicyBuilder();
    }

    public DiscountPolicyBuilder policyType(String policyType) {
        this.policy = PolicyType.valueOf(policyType.toUpperCase());
        return this;
    }

    public DiscountPolicyBuilder amount(Money amount) {
        this.discountAmount = amount;
        return this;
    }

    public DiscountPolicyBuilder percent(Percentage percent) {
        this.discountPercent = percent;
        return this;
    }

    public DiscountPolicyBuilder addCondition(DiscountCondition condition) {
        conditions.add(condition);
        return this;
    }

    public DiscountPolicyBuilder addConditions(List<DiscountCondition> conditions) {
        this.conditions.addAll(conditions);
        return this;
    }

    public DiscountPolicy build() {
        return switch (policy) {
            case AMOUNT -> new AmountDiscountPolicy(discountAmount, conditions);
            case PERCENT -> new PercentDiscountPolicy(discountPercent, conditions);
            case NONE -> new NoneDiscountPolicy();
        };
    }
}

