package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.value.Money;
import org.json.*;
import java.io.FileNotFoundException;
import java.util.*;

public final class MoviePricesJsonReader {
    Map<String, Money> map = new HashMap<>();
    public MoviePricesJsonReader(String filePath) throws FileNotFoundException {
        JSONArray jArray = JSONArrayReader.getJSONArray(filePath);

        //init map
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObject = jArray.getJSONObject(i);
            JSONObject priceObject = jObject.getJSONObject("price");
            map.put(jObject.getString("movie_name"), MoneyJsonReader.parse(priceObject));
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