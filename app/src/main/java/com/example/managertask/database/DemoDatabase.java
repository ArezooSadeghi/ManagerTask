package com.example.managertask.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.managertask.model.Admin;
import com.example.managertask.model.Task;
import com.example.managertask.model.User;

import java.io.File;
import java.util.UUID;

@Database(entities = {User.class, Task.class, Admin.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class DemoDatabase extends RoomDatabase {

    public final static String DB_NAME = "demo.db";
    private static DemoDatabase sDatabase;

    public synchronized static DemoDatabase getInstance(Context context) {
        if (sDatabase == null) {
            sDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    DemoDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            addAdmin();
        }
        return sDatabase;
    }


    public abstract DemoDao getDemoDao();


    private static void addAdmin() {
        Admin admin = new Admin("Arezoo", "6660279350");
        sDatabase.getDemoDao().inserAdmin(admin);
    }


    public File getPhotoFile(Context context) {
        File filesDir = context.getFilesDir();
        File photoFile = new File(filesDir, getPhotoFileName());
        return photoFile;
    }


    public String getPhotoFileName() {
        return "IMG_" + UUID.randomUUID() + ".jpg";
    }
}

