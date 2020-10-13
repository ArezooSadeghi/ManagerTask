package com.example.managertask.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.managertask.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User... users);

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();
}