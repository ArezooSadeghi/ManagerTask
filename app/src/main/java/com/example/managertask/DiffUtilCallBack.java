package com.example.managertask;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.managertask.model.Task;

import java.util.List;

public class DiffUtilCallBack extends DiffUtil.Callback {
    List<Task> newList;
    List<Task> oldList;

    public DiffUtilCallBack(List<Task> newList, List<Task> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).getId() == oldList.get(oldItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = newList.get(newItemPosition).compareTo(oldList.get(oldItemPosition));
        return result == 0;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        Task newTask = newList.get(newItemPosition);
        Task oldTask = oldList.get(oldItemPosition);

        Bundle diff = new Bundle();

       /* if (newTask.price != (oldTask.price)) {
            diff.putInt("price", newTask.price);
        }*/
        if (diff.size() == 0) {
            return null;
        }
        return diff;
        //return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

