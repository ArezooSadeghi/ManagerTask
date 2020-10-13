package com.example.managertask.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "user_table")
public class User implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    private long mUserId;

    @ColumnInfo(name = "username")
    private String mUsername;

    @ColumnInfo(name = "password")
    private String mPassword;

    public User() {
    }

    public User(long userId, String username, String password) {
        mUserId = userId;
        mUsername = username;
        mPassword = password;
    }

    public void setUserId(long userId) {
        mUserId = userId;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public long getUserId() {
        return mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }
}
