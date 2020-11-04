package com.example.managertask.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.managertask.model.Admin;
import com.example.managertask.model.State;
import com.example.managertask.model.Task;
import com.example.managertask.model.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface DemoDao {

    @Insert
    void insertUser(User user);

    @Insert
    void insertTask(Task task);

    @Insert
    void inserAdmin(Admin admin);

    @Delete
    void deleteUser(User user);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateUser(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Task task);

    @Query("SELECT * FROM admin_table")
    List<Admin> getAdmins();

    @Query("SELECT * FROM task_table")
    List<Task> getWholeTasks();

    @Query("SELECT * FROM task_table")
    List<Task> getAllTasks();

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

    @Query("SELECT * FROM task_table WHERE usertaskid=:userId")
    List<Task> getAllTasksForEveryUser(UUID userId);

    @Query("SELECT * FROM task_table WHERE id=:taskId")
    Task getTask(UUID taskId);

    @Query("SELECT * FROM task_table WHERE usertaskid=:taskId AND state=:taskState")
    List<Task> getAllTaksByState(State taskState, UUID taskId);

    @Query("DELETE FROM task_table WHERE usertaskid=:userId")
    void deleteAllTasksForUser(UUID userId);
}
