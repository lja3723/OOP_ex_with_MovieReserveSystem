package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.Movie;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.lja3723.ex.movie_reservation.policy.DiscountPolicy;
import com.lja3723.ex.movie_reservation.value.Money;
import org.json.JSONArray;
import org.json.JSONObject;

public final class MovieJsonReader {
    private final JSONArrayReader jsonArrayReader;
    private final MoviePricesJsonReader moviePricesJsonReader;
    private final DiscountPoliciesJsonReader discountPoliciesJsonReader;
    private List<Movie> movieList = new ArrayList<>();

   public MovieJsonReader(
           String filePath,
           MoviePricesJsonReader moviePricesJsonReader,
           DiscountPoliciesJsonReader discountPoliciesJsonReader)
   {
       //super(filePath);
       this.jsonArrayReader = new JSONArrayReader(filePath);
       this.moviePricesJsonReader = moviePricesJsonReader;
       this.discountPoliciesJsonReader = discountPoliciesJsonReader;
       initList();
   }

   private void initList() {
       JSONArray jArray = jsonArrayReader.getJSONArray();
       for (int i = 0; i < jArray.length(); i++) {
           movieList.add(convert(jArray.getJSONObject(i)));
       }
   }

    private Movie convert(JSONObject jObject) {
        String title =
                jObject.getString("movie_name");
        LocalDate releaseDate =
                LocalDate.parse(jObject.getString("release_date"), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        Duration runningTime =
                Duration.ofMinutes(jObject.getInt("running_time"));
        Money basicPrice =
                moviePricesJsonReader.getPrice(title);
        DiscountPolicy discountPolicy =
                discountPoliciesJsonReader.getDiscountPolicy(title);
        return new Movie(title, releaseDate, runningTime, basicPrice, discountPolicy);
    }

    public List<Movie> getList() {
       return movieList;
    }
}