package com.lja3723.ex.movie_reservation.resource_reader;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class JSONArrayReader {
    private JSONArray jArray;

    public JSONArrayReader (String filePath) {
        initJsonArray(filePath);
    }

    private void initJsonArray(String filePath) {
        try {
            jArray = new JSONArray(readFileFrom(filePath));
        } catch (JSONException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String readFileFrom(String filePath) throws FileNotFoundException {
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

    public JSONArray getJSONArray() {
        return jArray;
    }
}
