package com.example.managertask.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managertask.R;
import com.example.managertask.adapter.TaskAdapter;
import com.example.managertask.database.TaskDatabase;
import com.example.managertask.model.Task;
import com.example.managertask.model.User;

import java.util.List;

public class TodoFragment extends Fragment {
    private static final String ARGS_USER = "user";
    private RecyclerView mRecyclerViewTodo;
    private TaskDatabase mDatabase;
    private LinearLayout mLayoutEmptyRecyclerview;
    private TaskAdapter mTodoAdapter;
    private User mUser;

    public TodoFragment() {
    }

    public static TodoFragment newInstance(User user) {
        TodoFragment fragment = new TodoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = (User) getArguments().getSerializable(ARGS_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewTodo = view.findViewById(R.id.todo_recyclerview);
        mLayoutEmptyRecyclerview = view.findViewById(R.id.layout_empty_recyclerview);
    }

    private void initViews() {
        mRecyclerViewTodo.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabase = TaskDatabase.getInstance(getActivity());
        long userId = mDatabase.getUserDao().getUserId(mUser.getUsername(), mUser.getPassword());
        List<Task> tasks = mDatabase.getTaskDao().getAllTasksForEveryUser(userId);
        if (tasks.size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        } else {
            mTodoAdapter = new TaskAdapter(tasks, this);
            mRecyclerViewTodo.setAdapter(mTodoAdapter);
        }
    }
}

