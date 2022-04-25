package com.lja3723.ex.movie_reservation.policy;

import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.value.*;

import java.util.ArrayList;
import java.util.List;

public class DiscountPolicyBuilder {
    private DiscountPolicyBuilder() {}
    private enum PolicyType {
        AMOUNT, PERCENT, NONE;

        public static PolicyType convert(String policyType) {
            if (policyType.toLowerCase().equals("amount")) {
                return AMOUNT;
            }
            else if (policyType.toLowerCase().equals("percent")) {
                return PERCENT;
            }
            else {
                return NONE;
            }
        }
    }
    private PolicyType policy;
    private List<DiscountCondition> conditions = new ArrayList<>();
    private Money discountAmount = Money.ZERO;
    private Percentage discountPercent = new Percentage(0);

    public static DiscountPolicyBuilder getInstance() {
        return new DiscountPolicyBuilder();
    }

    public DiscountPolicyBuilder policyType(String policyType) {
        this.policy = PolicyType.convert(policyType);
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

    public DiscountPolicy build() {
        switch (policy) {
            case AMOUNT:
                return new AmountDiscountPolicy(discountAmount, conditions);
            case PERCENT:
                return new PercentDiscountPolicy(discountPercent, conditions);
            case NONE:
                return new NoneDiscountPolicy();
            default:
                throw new RuntimeException("Unexpected discount policy type;");
        }
    }
}

