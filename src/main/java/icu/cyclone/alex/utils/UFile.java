package icu.cyclone.alex.utils;

import java.io.*;
import java.util.ArrayList;

public class UFile {
    public static ArrayList<String> readToStrList(String fileName) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String str;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
        }
        return list;
    }

    public static void writeString(String fileName, String data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(data);
        }
    }
}
