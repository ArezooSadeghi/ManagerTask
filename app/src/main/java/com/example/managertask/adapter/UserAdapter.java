package com.example.managertask.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managertask.R;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.diffutils.UserDiffUtilCallback;
import com.example.managertask.model.User;
import com.example.managertask.utils.DateUtils;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private List<User> mUsers;
    private Context mContext;
    private RemoveCallback mCallback;

    public UserAdapter(List<User> users, Context context, RemoveCallback callback) {
        mUsers = users;
        mContext = context;
        mCallback = callback;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.detail_user_item, parent,
                false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.bindUser(mUsers.get(position));
    }

    @Override
    public void onBindViewHolder(
            @NonNull UserHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle bundle = (Bundle) payloads.get(0);
            for (String key : bundle.keySet()) {
                holder.bindUser((User) bundle.getSerializable(UserDiffUtilCallback.USER_BUNDLE));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public void updateUsers(List<User> newUsers) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new UserDiffUtilCallback(mUsers, newUsers));
        diffResult.dispatchUpdatesTo(this);
        mUsers.clear();
        mUsers.addAll(newUsers);
    }

    public interface RemoveCallback {
        void removeClicked();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewDate, mTextViewNumberOfTask;
        private ImageButton mButtonRemove;
        private User mUser;
        private DemoDatabase mDatabase = DemoDatabase.getInstance(mContext);

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            mButtonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.getDemoDao().deleteUser(mUser);
                    mDatabase.getDemoDao().deleteAllTasksForUser(mUser.getUserId());
                    mCallback.removeClicked();
                }
            });
        }

        public void bindUser(User user) {
            mUser = user;
            mTextViewDate.setText(DateUtils.dateFormating(user.getRegisteryDate()));
            int numberOfTask = mDatabase
                    .getDemoDao()
                    .getAllTasksForEveryUser(user.getUserId()).size();
            mTextViewNumberOfTask.setText("" + numberOfTask);
        }

        private void findViews(@NonNull View itemView) {
            mTextViewDate = itemView.findViewById(R.id.txt_date_task_user_item);
            mTextViewNumberOfTask = itemView.findViewById(R.id.txt_number_task_user_item);
            mButtonRemove = itemView.findViewById(R.id.btn_remove);
        }
    }
}
