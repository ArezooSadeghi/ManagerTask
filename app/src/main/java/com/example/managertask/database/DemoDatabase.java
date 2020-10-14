package com.example.managertask.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.managertask.model.Task;
import com.example.managertask.model.User;

@Database(entities = {User.class, Task.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class DemoDatabase extends RoomDatabase {

    private static DemoDatabase sDatabase;

    public final static String DB_NAME = "demo.db";

    public synchronized static DemoDatabase getInstance(Context context) {
        if (sDatabase == null) {
            sDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    DemoDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sDatabase;
    }

    public abstract DemoDao getDemoDao();
}

