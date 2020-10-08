package com.example.managertask.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.OffsetTime;
import java.util.Date;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "time")
    private Timestamp mTime;

    public Task() {
        mDate = new Date();
        mTime = new Timestamp(mDate.getTime());
    }

    public Task(String title, String description) {
        mTitle = title;
        mDescription = description;
        mDate = new Date();
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setTime(Timestamp time) {
        mTime = time;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getId() {
        return mId;
    }

    public Timestamp getTime() {
        return mTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public Date getDate() {
        return mDate;
    }
}
