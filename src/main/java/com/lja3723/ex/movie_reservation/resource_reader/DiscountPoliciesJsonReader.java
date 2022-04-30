package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.policy.DiscountPolicy;
import com.lja3723.ex.movie_reservation.value.*;
import org.json.*;
import java.io.FileNotFoundException;
import java.util.*;

public final class DiscountPoliciesJsonReader {
    Map<String, DiscountPolicy> map = new HashMap<>();
    public DiscountPoliciesJsonReader (String filePath) throws FileNotFoundException {
        JSONArray jArray = JSONArrayReader.getJSONArray(filePath);

        //init map
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObject = jArray.getJSONObject(i);
            map.put(jObject.getString("movie_name"), parse(jObject.getJSONObject("discount_policy")));
        }
    }

    private DiscountPolicy parse(JSONObject discountPolicy) {
        String type = discountPolicy.getString("type");
        Money amount = Money.ZERO;
        Percentage percent = new Percentage(0);
        List<DiscountCondition> conditions;

        if (type.equals("amount"))
            amount = MoneyJsonReader.parse(discountPolicy.getJSONObject("value"));
        else if (type.equals("percent"))
            percent = new Percentage(discountPolicy.getDouble("value"));

        conditions = DiscountConditionJsonReader.getConditions(discountPolicy.getJSONArray("conditions"));

        return DiscountPolicy.builder()
                .policyType(type)
                .amount(amount)
                .percent(percent)
                .addConditions(conditions)
                .build();
    }

	public DiscountPolicy getDiscountPolicy(String movieName) {
        if (map.containsKey(movieName))
            return map.get(movieName);
        else
            return DiscountPolicy.builder()
                    .policyType("none")
                    .build();
    }
}