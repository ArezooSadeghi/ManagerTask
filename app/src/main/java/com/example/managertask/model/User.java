package com.example.managertask.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "user_table")
public class User implements Comparable, Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private UUID mUserId;

    @ColumnInfo(name = "username")
    private String mUsername;

    @ColumnInfo(name = "password")
    private String mPassword;

    @ColumnInfo(name = "registerydate")
    private Date mRegisteryDate;

    public User() {
        mUserId = UUID.randomUUID();
        mRegisteryDate = new Date();
    }

    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
        mUserId = UUID.randomUUID();
        mRegisteryDate = new Date();
    }

    public void setRegisteryDate(Date registeryDate) {
        mRegisteryDate = registeryDate;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setUserId(@NonNull UUID userId) {
        mUserId = userId;
    }

    @NonNull
    public UUID getUserId() {
        return mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public Date getRegisteryDate() {
        return mRegisteryDate;
    }

    @Override
    public int compareTo(Object o) {
        User user = (User) o;
        if (user.getUsername().equals(this.mUsername) &&
                (user.getPassword().equals(this.mPassword))) {
            return 0;
        } else {
            return 1;
        }
    }
}