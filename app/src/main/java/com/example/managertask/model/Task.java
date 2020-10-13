package com.example.managertask.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Date;

@Entity(tableName = "task_table", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "taskId"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    private int mTaskId;

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

    public Task(int taskId, String title, String description, Date date, Timestamp time, State state) {
        mTaskId = taskId;
        mTitle = title;
        mDescription = description;
        mDate = date;
        mTime = time;
        mState = state;
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

    public void setTaskId(int taskId) {
        mTaskId = taskId;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getTaskId() {
        return mTaskId;
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
