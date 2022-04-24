package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.Movie;
import com.lja3723.ex.movie_reservation.Screening;
import com.lja3723.ex.movie_reservation.physical.Theatre;
import com.lja3723.ex.movie_reservation.value.Sequence;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScreeningsJsonReader extends JSONArrayReader<Screening> {
    private final List<Movie> movies;
    private final List<Theatre> theatres;
	public ScreeningsJsonReader(String filePath, List<Movie> movies, List<Theatre> theatres) {
        super(filePath);
        this.movies = movies;
        this.theatres = theatres;
        initListForThisClass();
    }

    protected void initListForThisClass() {
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObject = jArray.getJSONObject(i);
            Movie movie = getMovieByName(jObject.getString("movie_name"));
            list.addAll(convert(jObject.getJSONArray("screenings"), movie));
        }
    }

    private List<Screening> convert(JSONArray screenings, Movie movie) {
        List<Screening> screeningList = new ArrayList<>();

        for (int i = 0; i < screenings.length(); i++) {
            JSONObject sameDateScreenings = screenings.getJSONObject(i);
            LocalDate screeningDate = LocalDate.parse(
                    sameDateScreenings.getString("date"), DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            JSONArray screeningTheatres = sameDateScreenings.getJSONArray("theatres");
            screeningList.addAll(convert(screeningTheatres, movie, screeningDate));
        }
        return screeningList;
    }

    private List<Screening> convert(JSONArray screeningTheatres, Movie movie, LocalDate screeningDate) {
        List<Screening> screeningList = new ArrayList<>();

        for (int j = 0; j < screeningTheatres.length(); j++) {
            JSONObject screeningsTheatre = screeningTheatres.getJSONObject(j);
            Theatre theatre =  getTheatreByNumber(screeningsTheatre.getInt("number"));

            JSONArray screeningStartTimes = screeningsTheatre.getJSONArray("start_times");
            screeningList.addAll(convert(screeningStartTimes, movie, screeningDate, theatre));
        }

        return screeningList;
    }

    private List<Screening> convert(JSONArray screeningStartTimes, Movie movie, LocalDate screeningDate, Theatre theatre) {
        List<Screening> screeningList = new ArrayList<>();

        for (int k = 0; k < screeningStartTimes.length(); k++) {
            LocalTime screeningStartTime = LocalTime.parse(
                    screeningStartTimes.getString(k), DateTimeFormatter.ofPattern("HH:mm")
            );

            screeningList.add(new Screening(
                    movie,
                    new Sequence(k + 1),
                    LocalDateTime.of(screeningDate, screeningStartTime),
                    theatre));
        }

        return screeningList;
    }

    private Movie getMovieByName(String movieName) throws JSONException {
        for (Movie movie: movies) {
            if (movie.getTitle().equals(movieName)) {
                return movie;
            }
        }
        throw new JSONException(
                String.format("cannot find movie named \"%s\" in given movie list!", movieName));
    }

    private Theatre getTheatreByNumber(int theatreNumber) throws JSONException {
        for (Theatre theatre : theatres) {
            if (theatre.getNumber() == theatreNumber)
                return theatre;
        }
        throw new JSONException(
                String.format("cannot find theatre of number %d in given theatre list!", theatreNumber));
    }


    @Override
    protected void initList() { }

    @Override
    protected Screening convert(JSONObject jObject) throws JSONException{
        return null;
    }
}