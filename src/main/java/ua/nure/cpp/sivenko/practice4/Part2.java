package ua.nure.cpp.sivenko.practice4;

import ua.nure.cpp.sivenko.practice4.entity.Aircraft;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part2 {
    private static final String SER_FILE = "src/main/resources/data.ser";

    private static final List<Aircraft> AIRCRAFTS = new ArrayList<>(Arrays.asList(
            new Aircraft("Super Hornet VX-9", 1400),
            new Aircraft("Boeing 737Max8", 7500),
            new Aircraft("Neo Ra-73800", 1000),
            new Aircraft("Airbus A319", 3200)
    ));

    public static void main(String[] args) {
        AIRCRAFTS.forEach(System.out::println);
        serialize();

        ArrayList<Aircraft> aircrafts = deserialize();
        aircrafts.forEach(System.out::println);
    }

    private static void serialize() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SER_FILE))) {
            outputStream.writeObject(AIRCRAFTS);
            System.out.println("\nAircrafts have been serialized to: " + SER_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ArrayList<Aircraft> deserialize() {
        final ArrayList<Aircraft> aircrafts;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SER_FILE))) {
            aircrafts = (ArrayList<Aircraft>) inputStream.readObject();
            System.out.println("\nAircrafts have been deserialized from: " + SER_FILE);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return aircrafts;
    }
}
