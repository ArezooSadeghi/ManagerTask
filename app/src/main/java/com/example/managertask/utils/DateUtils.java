package com.example.managertask.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String dateFormating(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }

    public static Date stringFormating(String dateString) {
            Date date = new Date();
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                date = (Date) dateFormat.parse(dateString);
            } catch (Exception e) {
            }
            return date;
    }

    public static Date stringToTime(String time) {
        Date date = new Date();
        try {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            date = (Date) dateFormat.parse(time);
        } catch (Exception e) {
        }
        return date;
    }

    public static String timeToString(Date time) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(time);
    }

}
