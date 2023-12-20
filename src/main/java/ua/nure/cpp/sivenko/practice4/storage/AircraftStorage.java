package ua.nure.cpp.sivenko.practice4.storage;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ua.nure.cpp.sivenko.practice4.Constants.AircraftHeaders;
import ua.nure.cpp.sivenko.practice4.entity.Aircraft;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.nure.cpp.sivenko.practice4.Constants.CSV_FILE;
import static ua.nure.cpp.sivenko.practice4.Constants.CSV_OUT_FILE;

public class AircraftStorage implements TextStorage<Aircraft> {
    @Override
    public void saveToFile(List<Aircraft> list) {
        try (FileWriter fileWriter = new FileWriter(CSV_OUT_FILE)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(AircraftHeaders.class)
                    .build();

            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);

            for (var aircraft : list) {
                csvPrinter.printRecord(aircraft.getModel(), aircraft.getWeight());
            }
            System.out.println("\nAircraft list have been saved to CSV file successfully!");
        } catch (IOException ex) {
            System.err.println("Error while saving to file: " + CSV_OUT_FILE);
        }
    }

    @Override
    public List<Aircraft> loadFromFile() {
        final List<Aircraft> aircraftList = new ArrayList<>();

        try (FileReader fileReader = new FileReader(CSV_FILE)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(AircraftHeaders.class)
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser csvParser = new CSVParser(fileReader, csvFormat);

            for (CSVRecord csvRecord : csvParser) {
                var aircraft = new Aircraft();

                aircraft.setModel(csvRecord.get(AircraftHeaders.model));
                aircraft.setWeight(Integer.parseInt(csvRecord.get(AircraftHeaders.weight)));

                aircraftList.add(aircraft);
            }
        } catch (IOException ex) {
            System.err.println("Error while loading from file: " + CSV_FILE);
        }

        return aircraftList;
    }
}
