package com.example.managertask.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.managertask.model.State;
import com.example.managertask.model.Task;
import com.example.managertask.model.UserTask;

import java.util.List;

@androidx.room.Dao
public interface TaskDao {

    @Query("SELECT * FROM task_table")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task_table WHERE taskId=:taskId")
    Task getTask(int taskId);

    @Query("SELECT * FROM task_table WHERE state=:taskState")
    List<Task> getStateTasks(State taskState);

    @Query("SELECT * FROM task_table INNER JOIN user_table ON userId = taskId")
    List<Task> getAllUserTasks();

    @Query("SELECT * FROM task_table WHERE taskId=:userId")
    List<Task> getAllTasksForEveryUser(long userId);

    @Insert
    void insert(Task... tasks);

    @Delete
    void delete(Task... tasks);

    @Delete
    void reset(List<Task> tasks);

    @Update
    void update(Task... tasks);
}
