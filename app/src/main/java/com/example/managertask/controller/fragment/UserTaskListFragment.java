package com.example.managertask.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managertask.R;
import com.example.managertask.adapter.TaskAdapter;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.Task;

import java.util.List;

public class UserTaskListFragment extends Fragment {

    private RecyclerView mRecyclerViewUserTaskList;
    private TaskAdapter mAdapter;
    private DemoDatabase mDatabase;

    public UserTaskListFragment() {

    }

    public static UserTaskListFragment newInstance() {
        UserTaskListFragment fragment = new UserTaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = DemoDatabase.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_users_task_list, container, false);
        findViews(view);
        initViews();
        setupAdapter();
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewUserTaskList = view.findViewById(R.id.recycler_view_user_task_list);
    }

    private void initViews() {
        mRecyclerViewUserTaskList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupAdapter() {
        List<Task> tasks = mDatabase.getDemoDao().getAllTasks();
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(tasks, this);
            mRecyclerViewUserTaskList.setAdapter(mAdapter);
        } else {
            updateRecyclerview();
        }
    }

    public void updateRecyclerview() {
        mAdapter.updateTasks(mDatabase.getDemoDao().getAllTasks());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.updateTasks(mDatabase.getDemoDao().getAllTasks());
        }
    }
}