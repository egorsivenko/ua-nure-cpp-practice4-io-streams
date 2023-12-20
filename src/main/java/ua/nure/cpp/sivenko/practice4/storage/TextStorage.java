package ua.nure.cpp.sivenko.practice4.storage;

import java.io.IOException;
import java.util.List;

public interface TextStorage<T> {
    void saveToFile(List<T> list) throws IOException;

    List<T> loadFromFile() throws IOException;
}
