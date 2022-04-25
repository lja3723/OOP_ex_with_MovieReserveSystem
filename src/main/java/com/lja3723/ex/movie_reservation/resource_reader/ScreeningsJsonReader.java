package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.*;
import com.lja3723.ex.movie_reservation.physical.Theatre;
import com.lja3723.ex.movie_reservation.value.Sequence;
import org.json.*;

import java.io.FileNotFoundException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public final class ScreeningsJsonReader {
    private final String filePath;
    private final List<Movie> movieList;
    private final List<Theatre> theatreList;
    private final List<Screening> screeningList = new ArrayList<>();

	public ScreeningsJsonReader(String filePath, List<Movie> movieList, List<Theatre> theatreList) throws FileNotFoundException {
        this.filePath = filePath;
        this.movieList = movieList;
        this.theatreList = theatreList;
        initList();
    }

    private void initList() throws FileNotFoundException {
        JSONArray jArray = JSONArrayReader.getJSONArray(filePath);

        Movie movie;
        LocalDate screeningDate;
        LocalTime screeningStartTime;
        Theatre theatre;

        for (int movieNameIndex = 0; movieNameIndex < jArray.length(); movieNameIndex++) {
            JSONObject jObject = jArray.getJSONObject(movieNameIndex);
            movie = getMovieByName(jObject.getString("movie_name"));

            JSONArray screenings = jObject.getJSONArray("screenings");
            for (int dateIndex = 0; dateIndex < screenings.length(); dateIndex++) {
                JSONObject sameDateScreenings = screenings.getJSONObject(dateIndex);
                screeningDate = LocalDate.parse(
                        sameDateScreenings.getString("date"), DateTimeFormatter.ofPattern("yyyy/MM/dd")
                );

                JSONArray theatres = sameDateScreenings.getJSONArray("theatres");
                for (int theatreIndex = 0; theatreIndex < theatres.length(); theatreIndex++) {
                    JSONObject screeningsTheatre = theatres.getJSONObject(theatreIndex);
                    theatre =  getTheatreByNumber(screeningsTheatre.getInt("number"));

                    JSONArray start_times = screeningsTheatre.getJSONArray("start_times");
                    for (int startTimeIndex = 0; startTimeIndex < start_times.length(); startTimeIndex++) {
                        screeningStartTime = LocalTime.parse(
                                start_times.getString(startTimeIndex), DateTimeFormatter.ofPattern("HH:mm")
                        );

                        screeningList.add(new Screening(
                                movie,
                                new Sequence(startTimeIndex + 1),
                                LocalDateTime.of(screeningDate, screeningStartTime),
                                theatre));
                    }
                }
            }
        }
    }

    private Movie getMovieByName(String movieName) throws JSONException {
        for (Movie movie: movieList) {
            if (movie.getTitle().equals(movieName)) {
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