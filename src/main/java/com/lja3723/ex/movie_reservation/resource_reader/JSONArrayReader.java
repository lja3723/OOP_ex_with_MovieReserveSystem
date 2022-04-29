package com.lja3723.ex.movie_reservation.resource_reader;

import org.json.*;
import java.io.FileNotFoundException;

final class JSONArrayReader {
    private JSONArrayReader() {}
    public static JSONArray getJSONArray (String filePath) throws FileNotFoundException {
        return new JSONArray(FileReader.fileToString(filePath));
    }
}
