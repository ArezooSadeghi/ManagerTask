package com.example.managertask.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "user_table")
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public UUID mUserId;

    @ColumnInfo(name = "username")
    private String mUsername;

    @ColumnInfo(name = "password")
    private String mPassword;

    public User() {
        mUserId = UUID.randomUUID();
    }

    public User(String username, String password) {
        mUserId = UUID.randomUUID();
        mUsername = username;
        mPassword = password;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
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
}