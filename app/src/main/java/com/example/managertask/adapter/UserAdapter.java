package com.example.managertask.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managertask.R;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.User;
import com.example.managertask.utils.DateUtils;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private List<User> mUsers;
    private Context mContext;

    public UserAdapter(List<User> users, Context context) {
        mUsers = users;
        mContext = context;
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
    public int getItemCount() {
        return mUsers.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewDate, mTextViewNumber;
        private DemoDatabase mDatabase = DemoDatabase.getInstance(mContext);

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        public void bindUser(User user) {
            mTextViewDate.setText(DateUtils.dateFormating(user.getRegisteryDate()));
            int numberOfTask = mDatabase
                    .getDemoDao()
                    .getAllTasksForEveryUser(user.getUserId()).size();
            mTextViewNumber.setText("" + numberOfTask);
        }

        private void findViews(@NonNull View itemView) {
            mTextViewDate = itemView.findViewById(R.id.txt_date_task_user_item);
            mTextViewNumber = itemView.findViewById(R.id.txt_number_task_user_item);
        }
    }
}
