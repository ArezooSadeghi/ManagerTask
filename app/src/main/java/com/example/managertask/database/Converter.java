package com.example.managertask.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converter {

    @TypeConverter
    public static Date longToDate(Long timestamp) {
        return timestamp == null ? null :new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToLong(Date date) {
        return date == null ? null : date.getTime();
    }
}
