package icu.cyclone.alex.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

class UDate {
    static Calendar toCalendar(String source, String pattern) throws IllegalArgumentException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            calendar.setTime(sdf.parse(source));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return calendar;
    }
}
