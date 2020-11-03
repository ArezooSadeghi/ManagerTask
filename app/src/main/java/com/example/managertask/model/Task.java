package com.example.managertask.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "task_table")
public class Task implements Comparable, Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private UUID mTaskId;

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

    @ColumnInfo(name = "taskphoto")
    private String mPathPhoto;

    public Task() {
        mTaskId = UUID.randomUUID();
    }

    public Task(String title, String description, Date date, Timestamp time, State state,
                UUID userTaskId) {
        mTitle = title;
        mDescription = description;
        mDate = date;
        mTime = time;
        mState = state;
        mUserTaskId = userTaskId;
        mTaskId = UUID.randomUUID();
    }

    public void setTaskId(UUID taskId) {
        mTaskId = taskId;
    }

    public void setPathPhoto(String pathPhoto) {
        mPathPhoto = pathPhoto;
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

    public UUID getTaskId() {
        return mTaskId;
    }

    public String getPathPhoto() {
        return mPathPhoto;
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

    @Override
    public int compareTo(Object o) {
        Task task = (Task) o;
        if (task.getTitle().equals(this.mTitle) && (task.getDescription().equals(this.mDescription))
               && (task.getDate().equals(this.mDate)) && (task.getTime().equals(this.mTime)) &&
                (task.getState().equals(this.mState))) {
            return 0;
        } else {
            return 1;
        }
    }
}
