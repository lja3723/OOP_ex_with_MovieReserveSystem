package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.physical.*;
import org.json.*;
import java.io.FileNotFoundException;
import java.util.*;

public final class TheatreJsonReader {
    private final List<Theatre> theatreList = new ArrayList<>();
	public TheatreJsonReader(String filePath) throws FileNotFoundException {
        JSONArray jArray = JSONArrayReader.getJSONArray(filePath);

        //init list
        for (int i = 0; i < jArray.length(); i++) {
            theatreList.add(parse(jArray.getJSONObject(i)));
        }
    }

    private Theatre parse(JSONObject jObject) {
        int rows = jObject.getInt("rows");
        int columns = jObject.getInt("columns");
        int theatre_number = jObject.getInt("theatre_number");
        String theatre_name = jObject.getString("theatre_name");

        Theatre theatre = new Theatre(theatre_number, theatre_name, rows, columns);
        theatre.initExcludedSeats(parseExcludedSeats(theatre, jObject.getJSONArray("excluded_seats")));

        return theatre;
    }

    private List<Seat> parseExcludedSeats(Theatre theatre, JSONArray excludedSeatsJArray) {
        List<Seat> excludedSeats = new ArrayList<>();
        for (int i = 0; i < excludedSeatsJArray.length(); i++) {
            excludedSeats.add(new Seat(theatre, excludedSeatsJArray.getString(i)));
        }
        return excludedSeats;
    }

    public List<Theatre> getList() {
        return theatreList;
    }
}