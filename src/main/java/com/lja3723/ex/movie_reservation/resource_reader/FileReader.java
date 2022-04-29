package com.lja3723.ex.movie_reservation.resource_reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

final class FileReader {
    private FileReader() {}
    public static String fileToString(String filePath) throws FileNotFoundException {
        //open file (throws FileNotFoundException if file not exist)
        Scanner scanner = new Scanner(new File(filePath));

        //file to String
        StringBuilder fileString = new StringBuilder();
        while (scanner.hasNextLine()) {
            fileString.append(scanner.nextLine());
        }
        return fileString.toString();
    }
}
