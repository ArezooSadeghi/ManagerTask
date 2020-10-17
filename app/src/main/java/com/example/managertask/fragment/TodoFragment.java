package com.example.managertask.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managertask.R;
import com.example.managertask.adapter.TaskAdapter;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TodoFragment extends Fragment {
    private static final String ARGS_USER_ID = "userId";
    private RecyclerView mRecyclerViewTodo;
    private DemoDatabase mDatabase;
    private LinearLayout mLayoutEmptyRecyclerview;
    private TaskAdapter mTodoAdapter;
    private UUID mUserId;

    public TodoFragment() {
    }

    public static TodoFragment newInstance(UUID userId) {
        TodoFragment fragment = new TodoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = (UUID) getArguments().getSerializable(ARGS_USER_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        findViews(view);
        initViews();
        setHasOptionsMenu(true);
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewTodo = view.findViewById(R.id.todo_recyclerview);
        mLayoutEmptyRecyclerview = view.findViewById(R.id.layout_empty_recyclerview);
    }

    private void initViews() {
        mRecyclerViewTodo.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabase = DemoDatabase.getInstance(getActivity());
    }

    public void updateRecyclerview() {
        List<Task> tasks = mDatabase.getDemoDao().getAllTasksForEveryUser(mUserId);
        if (tasks.size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        } else {
            mLayoutEmptyRecyclerview.setVisibility(View.GONE);
            if (mTodoAdapter == null) {
                mTodoAdapter = new TaskAdapter(tasks, this);
                mRecyclerViewTodo.setAdapter(mTodoAdapter);
            } else {
                mTodoAdapter.setTasks(tasks);
                mRecyclerViewTodo.setAdapter(mTodoAdapter);
                mTodoAdapter.notifyDataSetChanged();
            }
        }
    }

    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.item_search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mTodoAdapter.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }*/
    }


