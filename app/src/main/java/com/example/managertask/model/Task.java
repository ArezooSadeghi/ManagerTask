package com.example.managertask.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "task_table")
public class Task {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
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

    @ColumnInfo(name = "usertaskid")
    private UUID mUserTaskId;

    public Task() {
    }

    public Task(String title, String description, Date date, Timestamp time, State state,
                UUID userTaskId) {
        mTitle = title;
        mDescription = description;
        mDate = date;
        mTime = time;
        mState = state;
        mUserTaskId = userTaskId;
    }

    public void setTaskId(int taskId) {
        mTaskId = taskId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setTime(Timestamp time) {
        mTime = time;
    }

    public void setState(State state) {
        mState = state;
    }

    public void setUserTaskId(UUID userTaskId) {
        mUserTaskId = userTaskId;
    }

    public int getTaskId() {
        return mTaskId;
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

    public Timestamp getTime() {
        return mTime;
    }

    public State getState() {
        return mState;
    }

    public UUID getUserTaskId() {
        return mUserTaskId;
    }
}
