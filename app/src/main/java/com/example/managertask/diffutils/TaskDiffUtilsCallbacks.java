package com.example.managertask.diffutils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.managertask.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDiffUtilsCallbacks extends DiffUtil.Callback {
    public static final String TASK_BUNDLE = "taskBundle";
    private List<Task> mOldTasks = new ArrayList<>();
    private List<Task> mNewTasks = new ArrayList<>();

    public TaskDiffUtilsCallbacks(List<Task> oldTasks, List<Task> newTasks) {
        mOldTasks = oldTasks;
        mNewTasks = newTasks;
    }

    @Override
    public int getOldListSize() {
        return mOldTasks != null ? mOldTasks.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewTasks != null ? mNewTasks.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return (mOldTasks.get(oldItemPosition).getTaskId().equals(mNewTasks.get(newItemPosition).getTaskId()));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = mNewTasks.get(newItemPosition).compareTo(mOldTasks.get(oldItemPosition));
        if (result == 0) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Task newTask = mNewTasks.get(newItemPosition);
        Task oldTask = mOldTasks.get(oldItemPosition);

        Bundle bundle = new Bundle();

        if (!(newTask == oldTask)) {
            bundle.putSerializable(TASK_BUNDLE, newTask);
        }

        if (bundle.size() == 0) {
            return null;
        }
        return bundle;
    }
}
