package com.lja3723.ex.movie_reservation.resource_reader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public abstract class JSONArrayReader<T> {
    private JSONArray jArray;
    private final List<T> list = new ArrayList<>();

    public JSONArrayReader (String filePath) {
        initJsonArray(filePath);
        initList();
    }

    private void initJsonArray(String filePath) {
        try {
            jArray = new JSONArray(readFileFrom(filePath));
        } catch (JSONException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initList() {
        for (int i = 0; i < jArray.length(); i++) {
            list.add(convert(jArray.getJSONObject(i)));
        }
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
        StringBuilder fileString = new StringBuilder();
        while (scanner.hasNextLine()) {
            fileString.append(scanner.nextLine());
        }
        return fileString.toString();
    }

    public final List<T> getList() { return list; }

    //JSONObject 를 T로 변환하는 로직을 오버라이딩 해야 함
    abstract protected T convert(JSONObject jObject);
}
