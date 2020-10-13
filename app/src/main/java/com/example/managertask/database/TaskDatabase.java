package com.example.managertask.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.managertask.model.Task;
import com.example.managertask.model.User;

@androidx.room.Database(entities = {Task.class, User.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase sDatabase;

    public final static String TASK_DB_NAME = "task.db";

    public synchronized static TaskDatabase getInstance(Context context) {
        if (sDatabase == null) {
            sDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    TaskDatabase.class, TASK_DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sDatabase;
    }

    public abstract TaskDao getTaskDao();

    public abstract UserDao getUserDao();
}
