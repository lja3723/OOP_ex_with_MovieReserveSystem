package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.condition.DiscountCondition;
import com.lja3723.ex.movie_reservation.value.Sequence;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DiscountConditionJsonReader {
    public static List<DiscountCondition> getConditions(JSONArray jArrayConditions) {
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
}
