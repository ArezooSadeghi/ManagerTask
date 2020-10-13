package com.example.managertask.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.managertask.model.Task;
import com.example.managertask.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User... users);

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

    @Query("SELECT * FROM user_table WHERE username=:username AND password=:password")
    User getUser(String username, String password);

    @Query("SELECT userId FROM user_table WHERE username=:username AND password=:password")
    long getUserId(String username, String password);

}