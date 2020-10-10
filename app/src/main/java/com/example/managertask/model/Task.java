package com.example.managertask.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.OffsetTime;
import java.util.Date;

@Entity(tableName = "task_table")
public class Task implements Comparable, Cloneable {

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

    @ColumnInfo(name = "state")
    private State mState;

    public Task() {
    }

    public Task(String title, String description) {
        mTitle = title;
        mDescription = description;
    }



    @Override
    public int compareTo(Object o) {
        Task compare = (Task) o;

        if (compare.getId() == this.getId())
        {
            return 0;
        }
        return 1;
    }

    @Override
    public Task clone() {

        Task clone;
        try {
            clone = (Task) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return clone;
    }


    public void setTitle(String title) {
        mTitle = title;
    }

    public void setTime(Timestamp time) {
        mTime = time;
    }

    public void setState(State state) {
        mState = state;
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

    public State getState() {
        return mState;
    }

    public String getDescription() {
        return mDescription;
    }

    public Date getDate() {
        return mDate;
    }
}
