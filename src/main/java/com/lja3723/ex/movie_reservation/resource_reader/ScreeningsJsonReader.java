package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.physical.Theatre;
import com.lja3723.ex.movie_reservation.reservable.*;
import org.json.*;
import java.io.FileNotFoundException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class ScreeningsJsonReader {
    private final List<Movie> movieList;
    private final List<Theatre> theatreList;
    private final List<Screening> screeningList = new ArrayList<>();
    private static class ScreeningInfo {
        public Movie movie;
        public LocalDate date;
        public LocalTime startTime;
        public Theatre theatre;
    }
    private final ScreeningInfo screeningInfo = new ScreeningInfo();

	public ScreeningsJsonReader(String filePath, List<Movie> movieList, List<Theatre> theatreList) throws FileNotFoundException {
        this.movieList = movieList;
        this.theatreList = theatreList;

        initScreeningList(filePath);
    }

    private void initScreeningList(String filePath) throws FileNotFoundException {
        JSONArray jArray = JSONArrayReader.getJSONArray(filePath);

        for (int movieNameIndex = 0; movieNameIndex < jArray.length(); movieNameIndex++) {
            JSONObject jObject = jArray.getJSONObject(movieNameIndex);
            screeningInfo.movie = getMovieByName(jObject.getString("movie_name"));
            parseScreenings(jObject.getJSONArray("screenings"));
        }
    }

    private void parseScreenings(JSONArray screenings) {
        for (int dateIndex = 0; dateIndex < screenings.length(); dateIndex++) {
            JSONObject sameDateScreenings = screenings.getJSONObject(dateIndex);
            screeningInfo.date = LocalDate.parse(
                    sameDateScreenings.getString("date"),
                    DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            parseTheatres(sameDateScreenings.getJSONArray("theatres"));
        }

    }

    private void parseTheatres(JSONArray theatres) {
        for (int theatreIndex = 0; theatreIndex < theatres.length(); theatreIndex++) {
            JSONObject screeningsTheatre = theatres.getJSONObject(theatreIndex);
            screeningInfo.theatre =  getTheatreByNumber(screeningsTheatre.getInt("number"));
            parseStartTimes(screeningsTheatre.getJSONArray("start_times"));
        }
    }

    private void parseStartTimes(JSONArray startTimes) {
        for (int startTimeIndex = 0; startTimeIndex < startTimes.length(); startTimeIndex++) {
            screeningInfo.startTime = LocalTime.parse(startTimes.getString(startTimeIndex));
            screeningList.add(new Screening(
                    screeningInfo.movie,
                    startTimeIndex + 1,
                    LocalDateTime.of(screeningInfo.date, screeningInfo.startTime),
                    screeningInfo.theatre));
        }
    }

    private Movie getMovieByName(String movieName) throws JSONException {
        for (Movie movie: movieList) {
            if (movie.getName().equals(movieName)) {
                return movie;
            }
        }
        throw new JSONException(
                String.format("cannot find movie named \"%s\" in given movie list!", movieName));
    }

    private Theatre getTheatreByNumber(int theatreNumber) throws JSONException {
        for (Theatre theatre : theatreList) {
            if (theatre.getNumber() == theatreNumber)
                return theatre;
        }
        throw new JSONException(
                String.format("cannot find theatre of number %d in given theatre list!", theatreNumber));
    }

    public List<Screening> getList() {
        return screeningList;
    }
}