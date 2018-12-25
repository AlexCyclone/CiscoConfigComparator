package icu.cyclone.alex.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class UCli {
    public static void printAndWait(String string) {
        System.out.println(string);
        System.out.println("(Press Enter to continue)");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static String notEmptyString(String request) {
        Scanner sc = new Scanner(System.in);
        String str;
        do {
            System.out.println(request);
            str = sc.nextLine().trim();
            if (str.equals("")) {
                System.out.println("Incorrect input. Try again.");
            }
        } while (str.equals(""));
        return str;
    }

    public static String trimmedString(String request) {
        Scanner sc = new Scanner(System.in);
        System.out.println(request);
        return sc.nextLine().trim();
    }

    public static int intRequest(String request) {
        return intRequest(request, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int intRequest(String request, int minValue, int maxValue) {
        Scanner sc = new Scanner(System.in);
        int a = Integer.MIN_VALUE;

        boolean incorrect = true;
        do {
            System.out.println(request);
            try {
                a = Integer.parseInt(sc.nextLine());
                if (a < minValue || a > maxValue) {
                    throw new NumberFormatException();
                }
                incorrect = false;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input. Try again.");
            }
        } while (incorrect);
        return a;
    }

    public static Calendar calendarRequest(String request, String datePattern) {
        Scanner sc = new Scanner(System.in);
        Calendar a = new GregorianCalendar();

        boolean incorrect = true;
        do {
            System.out.println(request);
            try {
                a = UDate.toCalendar(sc.nextLine(), datePattern);
                incorrect = false;
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect input. Try again.");
            }
        } while (incorrect);
        return a;
    }

    public static boolean boolRequest(String request, String trueText, String falseText) {
        Scanner sc = new Scanner(System.in);
        String line;
        boolean incorrect;
        do {
            System.out.println(request);
            line = sc.nextLine().trim();
            if (incorrect = !(line.equalsIgnoreCase(trueText) || line.equalsIgnoreCase(falseText))) {
                System.out.println("Incorrect input. Try again.");
            }
        } while (incorrect);
        return line.equals(trueText);
    }

    public static <T extends Enum<T>> T enumRequest(String request, Class<T> aEnum) {
        StringBuilder fullRequest = new StringBuilder(request);
        if (request.length() > 0) {
            fullRequest.append(System.lineSeparator());
        }

        fullRequest.append(UText.enumToStr(aEnum, " : ", "", UText.SLS, true));
        T[] constArray = aEnum.getEnumConstants();
        return constArray[intRequest(fullRequest.toString(), 0, constArray.length - 1)];
    }
}
