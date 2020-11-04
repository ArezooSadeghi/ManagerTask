package com.example.managertask.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "admin_table")
public class Admin {

    @ColumnInfo(name = "username")
    private String mUsername;

    @ColumnInfo(name = "password")
    private String mPassword;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private UUID mAdminId;

    public Admin() {
        mAdminId = UUID.randomUUID();
    }

    public Admin(String username, String password) {
        mUsername = username;
        mPassword = password;
        mAdminId = UUID.randomUUID();
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @NonNull
    public UUID getAdminId() {
        return mAdminId;
    }

    public void setAdminId(@NonNull UUID adminId) {
        mAdminId = adminId;
    }
}
