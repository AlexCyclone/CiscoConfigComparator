package icu.cyclone.alex.cisco.reader;

import icu.cyclone.alex.utils.UFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileConfigReader implements ConfigReader {

    private String filename;

    public FileConfigReader(String filename) {
        this.filename = filename;
    }

    @Override
    public ArrayList<String> read() throws InvalidReadDataException {
        ArrayList<String> text;
        try {
            text = UFile.readToStrList(filename);
        } catch (IOException e) {
            throw new InvalidReadDataException("Duplicate section was found");
        }
        return text;
    }

    @Override
    public String getName() {
        Path p = Paths.get(filename);
        return p.toFile().getName();
    }


}
