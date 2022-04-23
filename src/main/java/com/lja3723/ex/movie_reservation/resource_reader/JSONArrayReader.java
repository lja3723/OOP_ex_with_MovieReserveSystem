package com.lja3723.ex.movie_reservation.resource_reader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public abstract class JSONArrayReader {
    private JSONArray jArray;

    public JSONArrayReader (String filePath) {
        try {
            jArray = new JSONArray(readFileFrom(filePath));
        } catch (JSONException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> getObjectList() {
        List<T> objects = new ArrayList<>();
        for (int i = 0; i < jArray.length(); i++) {
            objects.add(convert(jArray.getJSONObject(i)));
        }
        return objects;
    }

    private String readFileFrom(String filePath) throws FileNotFoundException {
        Scanner scanner;
        //open file
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw e;
        }

        //file to String
        StringBuilder FileString = new StringBuilder();
        while (scanner.hasNextLine()) {
            FileString.append(scanner.nextLine()).append('\n');
        }
        return FileString.toString();
    }

    //JSONObject 를 T로 변환하는 로직을 오버라이딩 해야 함
    abstract protected <T> T convert(JSONObject jObject);
}
