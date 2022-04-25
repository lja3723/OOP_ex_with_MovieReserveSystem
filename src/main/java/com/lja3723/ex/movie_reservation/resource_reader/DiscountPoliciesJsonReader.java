package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.policy.DiscountPolicy;
import com.lja3723.ex.movie_reservation.policy.NoneDiscountPolicy;

public final class DiscountPoliciesJsonReader {
    JSONArrayReader jsonArrayReader;
    public DiscountPoliciesJsonReader (String filePath) {
        this.jsonArrayReader = new JSONArrayReader(filePath);
    }
	public DiscountPolicy getDiscountPolicy(String movieName) {
        return new NoneDiscountPolicy();
    }
}