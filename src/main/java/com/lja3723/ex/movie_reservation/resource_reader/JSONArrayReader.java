package com.lja3723.ex.movie_reservation.resource_reader;

import org.json.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class JSONArrayReader {
    private JSONArrayReader() {}

    public static JSONArray getJSONArray (String filePath) throws FileNotFoundException {
        try {
            return new JSONArray(readFileFrom(filePath));
        } catch (JSONException | FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static String readFileFrom(String filePath) throws FileNotFoundException {
        Scanner scanner;
        //open file
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        //file to String
        StringBuilder fileString = new StringBuilder();
        while (scanner.hasNextLine()) {
            fileString.append(scanner.nextLine());
        }
        return fileString.toString();
    }
}
