package com.example.managertask.diffutils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.managertask.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDiffUtilCallback extends DiffUtil.Callback {

    public static final String USER_BUNDLE = "userBundle";

    private List<User> mOldUsers = new ArrayList<>();
    private List<User> mNewUsers = new ArrayList<>();


    public UserDiffUtilCallback(List<User> oldUsers, List<User> newUsers) {
        mOldUsers = oldUsers;
        mNewUsers = newUsers;
    }


    @Override
    public int getOldListSize() {
        return mOldUsers != null ? mOldUsers.size() : 0;
    }


    @Override
    public int getNewListSize() {
        return mNewUsers != null ? mNewUsers.size() : 0;
    }


    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = mNewUsers.get(newItemPosition).compareTo(mOldUsers.get(oldItemPosition));
        if (result == 0) {
            return true;
        }
        return false;
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        User newUser = mNewUsers.get(newItemPosition);
        User oldUser = mOldUsers.get(oldItemPosition);

        Bundle bundle = new Bundle();

        if (!(newUser == oldUser)) {
            bundle.putSerializable(USER_BUNDLE, newUser);
        }
        if (bundle.size() == 0) {
            return null;
        }
        return bundle;
    }
}
