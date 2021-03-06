package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import org.json.*;
import java.time.*;
import java.util.*;

final class DiscountConditionJsonReader {
    public static List<DiscountCondition> getConditions(JSONArray jArrayConditions) {
        List<DiscountCondition> conditions = new ArrayList<>();
        int sequence = 1;
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(0, 0);

        for (int i = 0; i < jArrayConditions.length(); i++) {
            JSONObject condition = jArrayConditions.getJSONObject(i);
            String type = condition.getString("type");

            if (type.equals("sequence")) {
                sequence = condition.getInt("value");
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
}
