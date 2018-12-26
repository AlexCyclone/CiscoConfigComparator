package icu.cyclone.alex.cisco.reader;

import java.util.ArrayList;

public interface ConfigReader {
    ArrayList<String> read() throws InvalidReadDataException;
    String getName();
}
