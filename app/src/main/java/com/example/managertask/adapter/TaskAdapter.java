package com.example.managertask.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managertask.R;
import com.example.managertask.diffutils.TaskDiffUtilsCallbacks;
import com.example.managertask.controller.fragment.TaskDetailFragment;
import com.example.managertask.model.Task;
import com.example.managertask.utils.DateUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> implements Filterable {
    private static final String TAG = "Edit";
    private static final int REQUEST_CODE = 0;
    private List<Task> mTasks;
    private List<Task> mTasksFiltered;
    private Fragment mFragment;
    private Task mTask;

    public TaskAdapter(List<Task> tasks, Fragment fragment) {
        mTasks = tasks;
        mFragment = fragment;
        mTasksFiltered = mTasks;
    }

    public void setTasks(List<Task> tasks) {
        mTasks = tasks;
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mFragment.getActivity()).inflate(R.layout.detail_task_item,
                parent,
                false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.bindTask(mTasksFiltered.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads);
        else {
            Bundle bundle = (Bundle) payloads.get(0);
            for (String key : bundle.keySet())
                holder.bindTask((Task) bundle.getSerializable(TaskDiffUtilsCallbacks.TASK_BUNDLE));
        }
    }

    @Override
    public int getItemCount() {
        return mTasksFiltered.size();
    }

    public void updateTasks(List<Task> newTasks) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TaskDiffUtilsCallbacks(mTasks, newTasks));
        diffResult.dispatchUpdatesTo(this);
        mTasks.clear();
        mTasks.addAll(newTasks);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString();
                if (userInput.isEmpty()) {
                    mTasksFiltered = mTasks;
                } else {
                    mTasksFiltered = new ArrayList<>();
                    for (Task task : mTasks) {
                        if (task.getTitle().toLowerCase().contains(userInput) ||
                                task.getDescription().toLowerCase().contains(userInput) ||
                                DateUtils.dateFormating(task.getDate()).toLowerCase().contains(userInput)) {
                            mTasksFiltered.add(task);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mTasksFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mTasksFiltered = (List<Task>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle, mTextViewDate, mTextViewTime;
        private Button mButtonFirstLetter;
        private ImageView mPhoto;
        private Task mTask;

        public TaskHolder(@NonNull final View itemView) {
            super(itemView);
            findViews(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskDetailFragment taskDetailFragment = TaskDetailFragment
                            .newInstance(mTask.getTaskId());

                    taskDetailFragment.setTargetFragment(mFragment, REQUEST_CODE);
                    taskDetailFragment.show(mFragment.getActivity()
                            .getSupportFragmentManager(), TAG);
                }
            });
        }

        private void findViews(@NonNull View itemView) {
            mTextViewTitle = itemView.findViewById(R.id.txt_row_title);
            mTextViewDate = itemView.findViewById(R.id.txt_date);
            mTextViewTime = itemView.findViewById(R.id.txt_time);
            mButtonFirstLetter = itemView.findViewById(R.id.fab_first_letter);
            mPhoto = itemView.findViewById(R.id.img_task);
        }

        private void bindTask(Task task) {
            mTask = task;
            mTextViewTitle.setText(task.getTitle());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = task.getDate();
            mTextViewDate.setText(dateFormat.format(date));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(task.getTime().getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            mTextViewTime.setText(simpleDateFormat.format(calendar.getTime()));
            mButtonFirstLetter.setText(task.getTitle().substring(0, 1));
            File filesDir = mFragment.getActivity().getFilesDir();
            File photoFile = new File(filesDir, task.getPathPhoto());
            if (photoFile != null && photoFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                mPhoto.setImageBitmap(bitmap);
            }
        }
    }
}
