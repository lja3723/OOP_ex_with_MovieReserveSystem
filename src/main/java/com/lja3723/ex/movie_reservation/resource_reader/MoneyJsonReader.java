package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.value.Money;
import org.json.JSONObject;

final class MoneyJsonReader {
    public static Money parse(JSONObject money) {
        String currencyType = money.getString("currency_type");
        if (currencyType.equals("KRW")) {
            return Money.wons(money.getInt("value"));
        }
        else {
            throw new RuntimeException(String.format("Currency type \"%s\" is not supported;", currencyType));
        }
    }
}
