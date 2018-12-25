package icu.cyclone.alex.utils;

public class UText {
    final public static String SLS = System.lineSeparator();

    public static String arrayToStr(Object[] array, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length - 1; i++) {
            sb.append(array[i].toString()).append(separator);
        }
        sb.append(array[array.length - 1]);
        return sb.toString();
    }

    public static <T extends Enum<T>> String enumToStr(Class<T> aEnum, String textBefore,
                                                       String textAfter, String separator, boolean numbered) {
        T[] array = aEnum.getEnumConstants();
        if (array.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length - 1; i++) {
            if (numbered) {
                sb.append(i);
            }
            sb.append(textBefore).append(array[i]).append(textAfter).append(separator);
        }
        if (numbered) {
            sb.append(array.length - 1);
        }
        sb.append(textBefore).append(array[array.length - 1].toString()).append(textAfter);
        return sb.toString();
    }

    public static String stringRepeat(String str, int count) {
        if (count < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str.length() * count);
        for (; count > 0; count--) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String eachLineWithPrefix(String string, String firstLinePrefix, String prefix) {
        String[] strings = string.split(SLS);
        for (int i = 0; i < strings.length; i++) {
            strings[i] = i == 0 ? firstLinePrefix + strings[i] : prefix + strings[i];
        }
        return arrayToStr(strings, SLS);
    }

    public static int calcSpacesBefore(String line) {
        int count = 0;
        for (char c : line.toCharArray()) {
            if (c != ' ') {
                break;
            }
            count += 1;
        }
        return count;
    }

    public static String trimLeft(String text) {
        return text.substring(calcSpacesBefore(text));
    }

    public static Character[] strToCharArr(String str) {
        Character[] arr = new Character[str.length()];
        for (int i = 0; i < str.length(); i++) {
            arr[i] = str.charAt(i);
        }
        return arr;
    }

    public static String firstWord(String string) {
        String str = trimLeft(string);
        return str.indexOf(' ') < 0 ? str : str.substring(0, str.indexOf(' '));
    }
}
