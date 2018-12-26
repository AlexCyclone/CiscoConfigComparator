package icu.cyclone.alex.cisco;

import icu.cyclone.alex.cisco.reader.ConfigReader;
import icu.cyclone.alex.cisco.reader.InvalidReadDataException;
import icu.cyclone.alex.utils.Tree;

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

    public Config loadConfig(ConfigReader configReader) throws InvalidReadDataException, InvalidConfigFormatException {

        configTree = ConfigParser.parse(configReader.read());
        setName(configReader.getName());
        return this;
    }
}
