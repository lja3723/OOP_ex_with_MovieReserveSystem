package com.lja3723.ex.movie_reservation.resource_reader;

import org.json.JSONObject;
import com.lja3723.ex.movie_reservation.Movie;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovieReader extends JSONArrayReader{
   public MovieReader(String filePath) {
       super(filePath);
   }
    protected <T> T convert(JSONObject jObject) {
        String title =
                jObject.getString("movie_name");
        LocalDate releaseDate =
                LocalDate.parse(jObject.getString("release_date"), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        Duration runningTime =
                Duration.ofMinutes(jObject.getInt("running_time"));
        return (T)new Movie(title, releaseDate, runningTime);
    }
}