package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.value.Money;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public final class MoviePricesJsonReader {
    JSONArrayReader jsonArrayReader;
    Map<String, Money> map = new HashMap<>();
    public MoviePricesJsonReader(String filePath) {
        this.jsonArrayReader = new JSONArrayReader(filePath);
        initMap();
    }

    public void initMap() {
        JSONArray jArray = jsonArrayReader.getJSONArray();

        String movieName;
        Money price;
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObject = jArray.getJSONObject(i);
            movieName = jObject.getString("movie_name");

            JSONObject priceObject = jObject.getJSONObject("price");
            if (priceObject.getString("currency_type").equals("KRW")) {
                price = Money.wons(priceObject.getInt("value"));
            }
            else {
                throw new RuntimeException("Currency type is not KRW!");
            }

            map.put(movieName, price);
        }
    }

	public Money getPrice(String movieName) {
        if (map.containsKey(movieName)) {
            return map.get(movieName);
        } else {
            throw new RuntimeException(String.format("Cannot find price of movie name \"%s\"!", movieName));
        }
    }
}