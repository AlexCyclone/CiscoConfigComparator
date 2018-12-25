package icu.cyclone.alex;

import icu.cyclone.alex.cisco.ConfigComparator;
import icu.cyclone.alex.utils.UFile;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        ConfigComparator diff = new ConfigComparator("config1", "config2", 2);
        try {
            UFile.writeString("configsDiff", diff.fullView());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
