package com.example.managertask.database;

import androidx.room.TypeConverter;

import com.example.managertask.model.State;
import com.example.managertask.utils.DateUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class Converter {

    private static final String ERROR = "error";

    @TypeConverter
    public static String dateToString(Date date) {
        return DateUtils.dateFormating(date);
    }

    @TypeConverter
    public static Date stringToDate(String dateString) {
        return DateUtils.stringFormating(dateString);
    }


    @TypeConverter
    public static Timestamp stringToTimestamp(String time) {
        return DateUtils.stringTimeFormating(time);
    }

    @TypeConverter
    public static String timestampToString(Timestamp timestamp) {
        return DateUtils.timeStringFormating(timestamp);
    }

    @TypeConverter
    public static String enumToString(State state) {
        return state.name();
    }

    @TypeConverter
    public static State stringToEnum(String state) {
        return State.valueOf(state);
    }

    @TypeConverter
    public static String uuidToString(UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static UUID stringToUuid(String uuid) {
        return UUID.fromString(uuid);
    }
}
