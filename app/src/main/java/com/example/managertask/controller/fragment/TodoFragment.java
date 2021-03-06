package com.example.managertask.controller.fragment;

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

import java.util.List;
import java.util.UUID;

public class TodoFragment extends Fragment {

    private LinearLayout mLayoutEmptyRecyclerview;
    private RecyclerView mRecyclerViewTodo;
    private DemoDatabase mDatabase;
    private TaskAdapter mTodoAdapter;
    private UUID mUserId;

    private static final String ARGS_USER_ID = "userId";


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

        setHasOptionsMenu(true);

        mUserId = (UUID) getArguments().getSerializable(ARGS_USER_ID);
        mDatabase = DemoDatabase.getInstance(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        findViews(view);
        initViews();
        setupAdapter();

        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mTodoAdapter != null) {
                    mTodoAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void findViews(View view) {
        mRecyclerViewTodo = view.findViewById(R.id.recycler_view_todo);
        mLayoutEmptyRecyclerview = view.findViewById(R.id.layout_empty_recyclerview);
    }


    private void initViews() {
        mRecyclerViewTodo.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    private void setupAdapter() {
        List<Task> todoTasks = mDatabase.getDemoDao().getAllTasksForEveryUser(mUserId);
        if (todoTasks.size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        }
        if (mTodoAdapter == null) {
            mTodoAdapter = new TaskAdapter(todoTasks, this);
            mRecyclerViewTodo.setAdapter(mTodoAdapter);
        } else {
            updateRecyclerview();
        }
    }


    public void updateRecyclerview() {
        if (mDatabase.getDemoDao().getAllTasksForEveryUser(mUserId).size() > 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.GONE);
        }
        if (mDatabase.getDemoDao().getAllTasksForEveryUser(mUserId).size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        }
        mTodoAdapter.updateTasks(mDatabase.getDemoDao().getAllTasksForEveryUser(mUserId));
    }


    public void removeAllTasks() {
        mDatabase.getDemoDao().deleteAllTasksForUser(mUserId);
        updateRecyclerview();
    }
}


