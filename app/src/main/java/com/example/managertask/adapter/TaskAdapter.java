package com.example.managertask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managertask.R;
import com.example.managertask.model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private List<Task> mTasks;
    private Context mContext;

    public TaskAdapter(List<Task> tasks, Context context) {
        mTasks = tasks;
        mContext = context;
        notifyDataSetChanged();
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.task_row, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.bindTask(mTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle, mTextViewDate, mTextViewTime;
        private Button mButtonFirstLetter;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(@NonNull View itemView) {
            mTextViewTitle = itemView.findViewById(R.id.txt_row_title);
            mTextViewDate = itemView.findViewById(R.id.txt_date);
            mTextViewTime = itemView.findViewById(R.id.txt_time);
            mButtonFirstLetter = itemView.findViewById(R.id.fab_first_letter);
        }

        private void bindTask(Task task) {
            mTextViewTitle.setText(task.getTitle());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = task.getDate();
            mTextViewDate.setText(dateFormat.format(date));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(task.getTime().getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            mTextViewTime.setText(simpleDateFormat.format(calendar.getTime()));
            mButtonFirstLetter.setText(task.getTitle().substring(0, 1));
        }
    }
}
