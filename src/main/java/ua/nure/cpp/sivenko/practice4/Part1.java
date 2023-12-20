package ua.nure.cpp.sivenko.practice4;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import ua.nure.cpp.sivenko.practice4.Constants.AircraftHeaders;
import ua.nure.cpp.sivenko.practice4.entity.Aircraft;
import ua.nure.cpp.sivenko.practice4.storage.AircraftStorage;
import ua.nure.cpp.sivenko.practice4.storage.TextStorage;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static ua.nure.cpp.sivenko.practice4.Constants.CSV_FILE;
import static ua.nure.cpp.sivenko.practice4.Constants.CSV_OUT_FILE;

public class Part1 {
    private static final Map<String, Integer> AIRCRAFTS_MAP = Map.ofEntries(
            entry("Super Hornet VX-9", 1400),
            entry("Boeing 737Max8", 7500),
            entry("Neo Ra-73800", 1000),
            entry("Airbus A319", 3200)
    );

    public static void main(String[] args) throws IOException {
        populateCsvFile();

        TextStorage<Aircraft> storage = new AircraftStorage();
        List<Aircraft> list = storage.loadFromFile();

        System.out.println("\nAircraft List: ");
        list.forEach(System.out::println);

        Collections.sort(list);
        System.out.println("\nSorted Aircraft List: ");
        list.forEach(System.out::println);

        storage.saveToFile(list);

        System.out.println("\nSource file content: ");
        displayFileContent(CSV_FILE);

        System.out.println("\nDestination file content: ");
        displayFileContent(CSV_OUT_FILE);
    }

    private static void populateCsvFile() {
        try (FileWriter fileWriter = new FileWriter(CSV_FILE)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(AircraftHeaders.class)
                    .build();

            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);

            AIRCRAFTS_MAP.forEach((model, weight) -> {
                try {
                    csvPrinter.printRecord(model, weight);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            System.out.println("\nCSV file have been created successfully!");
        } catch (IOException ex) {
            System.err.println("Error while writing to file: " + CSV_FILE);
        }
    }

    private static void displayFileContent(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println("Error while reading from file: " + fileName);
        }
    }
}
