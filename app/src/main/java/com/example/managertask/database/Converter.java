package com.example.managertask.database;

import androidx.room.TypeConverter;

import com.example.managertask.model.State;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    @TypeConverter
    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }

    @TypeConverter
    public static Date stringToDate(String dateString) {
        Date date = new Date();
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            date = (Date) dateFormat.parse(dateString);
        } catch (Exception e) {
        }
        return date;
    }

    @TypeConverter
    public static Timestamp stringToTimestamp(String time) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            Date parsedDate = dateFormat.parse(time);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (Exception e) {
        }
        return timestamp;
    }

    @TypeConverter
    public static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat("hh:mm:ss").format(timestamp);
    }

    @TypeConverter
    public static String enumToString(State state) {
        return state.name();
    }

    @TypeConverter
    public static State stringToEnum(String state) {
        State s = State.valueOf(state);
        return s;
    }
}
