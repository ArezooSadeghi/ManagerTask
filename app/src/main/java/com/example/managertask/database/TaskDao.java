package com.example.managertask.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.managertask.model.State;
import com.example.managertask.model.Task;

import java.util.List;

@androidx.room.Dao
public interface TaskDao {

    @Query("SELECT * FROM task_table")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task_table WHERE taskId=:taskId")
    Task getTask(int taskId);

    @Query("SELECT * FROM task_table WHERE state=:taskState")
    List<Task> getStateTasks(State taskState);

    @Insert
    void insert(Task... tasks);

    @Delete
    void delete(Task... tasks);

    @Delete
    void reset(List<Task> tasks);

    @Update
    void update(Task... tasks);
}
