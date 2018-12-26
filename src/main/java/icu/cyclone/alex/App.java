package icu.cyclone.alex;

import icu.cyclone.alex.cisco.ConfigComparator;
import icu.cyclone.alex.cisco.InvalidConfigFormatException;
import icu.cyclone.alex.cisco.reader.ConfigReader;
import icu.cyclone.alex.cisco.reader.FileConfigReader;
import icu.cyclone.alex.cisco.reader.InvalidReadDataException;
import icu.cyclone.alex.cisco.reader.SSHConfigReader;

public class App {
    public static void main(String[] args) {
        ConfigReader cr1 = new FileConfigReader("config1");
        ConfigReader cr2 = new SSHConfigReader("fseadmin", "121.Quetzalc0@tl.10",
                "82.207.89.142", 6022);
        ConfigComparator diff;
        try {
            diff = new ConfigComparator(cr1, cr2, 2);
            System.out.println(diff.fullView());
        } catch (InvalidReadDataException | InvalidConfigFormatException e) {
            e.printStackTrace();
        }
    }
}
