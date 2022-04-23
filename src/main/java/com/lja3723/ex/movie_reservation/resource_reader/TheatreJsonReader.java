package com.lja3723.ex.movie_reservation.resource_reader;

import com.lja3723.ex.movie_reservation.physical.Seat;
import com.lja3723.ex.movie_reservation.physical.Theatre;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TheatreJsonReader extends JSONArrayReader<Theatre> {
	public TheatreJsonReader(String filePath) {
        super(filePath);
    }

    @Override
    protected Theatre convert(JSONObject jObject) {
        int theatre_number =
                jObject.getInt("theatre_number");
        String theatre_name =
                jObject.getString("theatre_name");
        int rows =
                jObject.getInt("rows");
        int columns =
                jObject.getInt("columns");


        Theatre theatre = new Theatre(theatre_number, theatre_name, rows, columns);

        JSONArray array =
                jObject.getJSONArray("excluded_seats");

        List<Seat> excludedSeats = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            excludedSeats.add(new Seat(theatre, array.getString(i)));
        }
        theatre.initExcludedSeats(excludedSeats);

        return theatre;
    }
}