package com.example.managertask.utils;

import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final String ERROR = "error";

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
            Log.e(ERROR, e.getMessage(), e);
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

    public static String nowTimeStringFormating(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static Timestamp stringTimeFormating(String time) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date parsedDate = dateFormat.parse(time);
            timestamp = new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            Log.e(ERROR, e.getMessage(), e);
        }
        return timestamp;
    }

    public static String timeStringFormating(Timestamp time) {
        return new SimpleDateFormat("HH:mm").format(time);
    }
}
