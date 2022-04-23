package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.Movie;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;

public class MovieJsonReader extends JSONArrayReader<Movie>{
   public MovieJsonReader(String filePath) {
       super(filePath);
   }

   @Override
    protected Movie convert(JSONObject jObject) {
        String title =
                jObject.getString("movie_name");
        LocalDate releaseDate =
                LocalDate.parse(jObject.getString("release_date"), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        Duration runningTime =
                Duration.ofMinutes(jObject.getInt("running_time"));
        return new Movie(title, releaseDate, runningTime);
    }
}