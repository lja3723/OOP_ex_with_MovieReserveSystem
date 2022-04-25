package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.policy.DiscountPolicy;
import com.lja3723.ex.movie_reservation.value.*;
import org.json.*;
import java.time.*;
import java.util.*;

public final class DiscountPoliciesJsonReader {
    JSONArrayReader jsonArrayReader;
    Map<String, DiscountPolicy> map = new HashMap<>();
    public DiscountPoliciesJsonReader (String filePath) {
        this.jsonArrayReader = new JSONArrayReader(filePath);
        initMap();
    }

    private void initMap() {
        JSONArray jArray = jsonArrayReader.getJSONArray();

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObject = jArray.getJSONObject(i);

            String movieName = jObject.getString("movie_name");
            DiscountPolicy discountPolicy = convert(jObject.getJSONObject("discount_policy"));

            map.put(movieName, discountPolicy);
        }
    }

    private DiscountPolicy convert(JSONObject discountPolicy) {
        String type = discountPolicy.getString("type");
        Money amount = Money.ZERO;
        Percentage percent = new Percentage(0);
        List<DiscountCondition> conditions;

        if (type.equals("amount")) {
            amount = MoneyJsonReader.convert(discountPolicy.getJSONObject("value"));
        }
        else if (type.equals("percent")) {
            percent = new Percentage(discountPolicy.getDouble("value"));
        }

        conditions = convert(discountPolicy.getJSONArray("conditions"));

        return DiscountPolicy.builder()
                .policyType(type)
                .amount(amount)
                .percent(percent)
                .addConditions(conditions)
                .build();
    }

    private List<DiscountCondition> convert(JSONArray jArrayConditions) {
        List<DiscountCondition> conditions = new ArrayList<>();
        Sequence sequence = new Sequence(1);
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(0, 0);

        for (int i = 0; i < jArrayConditions.length(); i++) {
            JSONObject condition = jArrayConditions.getJSONObject(i);
            String type = condition.getString("type");

            if (type.equals("sequence")) {
                sequence = new Sequence(condition.getInt("value"));
            }

            else if (type.equals("period")) {
                JSONObject period = condition.getJSONObject("value");
                JSONObject duration = period.getJSONObject("duration");

                dayOfWeek = DayOfWeek.valueOf(period.getString("day_of_the_week").toUpperCase());
                start = LocalTime.parse(duration.getString("start"));
                end = LocalTime.parse(duration.getString("end"));
            }

            conditions.add(DiscountCondition.builder()
                    .conditionType(type)
                    .sequence(sequence)
                    .dayOfWeek(dayOfWeek)
                    .duration(start, end)
                    .build()
            );
        }

        return conditions;
    }

	public DiscountPolicy getDiscountPolicy(String movieName) {
        if (map.containsKey(movieName)) {
            return map.get(movieName);
        }
        else {
            return DiscountPolicy.builder()
                    .policyType("none")
                    .build();
        }
    }
}