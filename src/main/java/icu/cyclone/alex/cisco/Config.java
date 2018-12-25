package icu.cyclone.alex.cisco;

import icu.cyclone.alex.utils.InvalidFileFormatException;
import icu.cyclone.alex.utils.Tree;
import icu.cyclone.alex.utils.UFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Config {
    private Tree<String> configTree;

    public static Config getNew() {
        return new Config();
    }

    public Config() {
        this.configTree = new Tree<>();
    }

    public void setName(String name) {
        configTree.getRoot().setData(name);
    }

    public Tree<String> getConfigTree() {
        return configTree;
    }

    public Config loadConfigFromFile(String filename) throws InvalidFileFormatException {
        ArrayList<String> text;
        try {
            text = UFile.readToStrList(filename);
        } catch (IOException e) {
            throw new InvalidFileFormatException("Duplicate section was found");
        }
        configTree = ConfigParser.parse(text);
        Path p = Paths.get(filename);
        setName(p.toString());
        return this;
    }
}
