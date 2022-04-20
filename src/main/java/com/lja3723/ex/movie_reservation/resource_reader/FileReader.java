package com.lja3723.ex.movie_reservation.resource_reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

	public static String getTotalStringFrom(String filePath) throws FileNotFoundException {
		//open file
		Scanner scanner;
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
}