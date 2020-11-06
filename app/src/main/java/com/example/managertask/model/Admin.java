package com.example.managertask.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "admin_table")
public class Admin {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private UUID mAdminId;

    @ColumnInfo(name = "username")
    private String mUsername;

    @ColumnInfo(name = "password")
    private String mPassword;

    public Admin() {
        mAdminId = UUID.randomUUID();
    }

    public Admin(String username, String password) {
        mUsername = username;
        mPassword = password;
        mAdminId = UUID.randomUUID();
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setAdminId(@NonNull UUID adminId) {
        mAdminId = adminId;
    }

    @NonNull
    public UUID getAdminId() {
        return mAdminId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }
}
