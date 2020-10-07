package com.example.managertask.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.managertask.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task_table")
    List<Task> getAllTask();

    @Query("SELECT * FROM task_table WHERE id=:taskId")
    Task getTask(int taskId);

    @Insert
    void insert(Task...tasks);

    @Delete
    void delete(Task...tasks);

    @Delete
    void reset(List<Task> tasks);

    @Update
    void update(Task...tasks);
}
