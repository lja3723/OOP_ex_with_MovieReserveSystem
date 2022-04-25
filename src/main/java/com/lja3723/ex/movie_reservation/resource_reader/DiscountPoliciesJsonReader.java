package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.policy.DiscountPolicy;
import com.lja3723.ex.movie_reservation.policy.NoneDiscountPolicy;

import java.util.Map;

public final class DiscountPoliciesJsonReader {
    JSONArrayReader jsonArrayReader;
    Map<String, DiscountPolicy> map;
    public DiscountPoliciesJsonReader (String filePath) {
        this.jsonArrayReader = new JSONArrayReader(filePath);
    }
	public DiscountPolicy getDiscountPolicy(String movieName) {
        return new NoneDiscountPolicy();
    }
}