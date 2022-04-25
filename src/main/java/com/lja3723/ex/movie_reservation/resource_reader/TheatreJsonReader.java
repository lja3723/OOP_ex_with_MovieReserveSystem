package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.physical.*;
import org.json.*;

import java.io.FileNotFoundException;
import java.util.*;

public final class TheatreJsonReader {
    private final String filePath;
    private final List<Theatre> theatreList = new ArrayList<>();
	public TheatreJsonReader(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        initList();
    }

    private void initList() throws FileNotFoundException {
        JSONArray jArray = JSONArrayReader.getJSONArray(filePath);

        for (int i = 0; i < jArray.length(); i++) {
            theatreList.add(convert(jArray.getJSONObject(i)));
        }
    }

    private Theatre convert(JSONObject jObject) {
        int theatre_number = jObject.getInt("theatre_number");
        String theatre_name = jObject.getString("theatre_name");
        int rows = jObject.getInt("rows");
        int columns = jObject.getInt("columns");
        Theatre theatre = new Theatre(theatre_number, theatre_name, rows, columns);

        List<Seat> excludedSeats = new ArrayList<>();
        JSONArray array = jObject.getJSONArray("excluded_seats");
        for (int i = 0; i < array.length(); i++) {
            excludedSeats.add(new Seat(theatre, array.getString(i)));
        }
        theatre.initExcludedSeats(excludedSeats);

        return theatre;
    }

    public List<Theatre> getList() {
        return theatreList;
    }
}