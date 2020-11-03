package com.example.managertask.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
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
    private static final String ARGS_USER_ID = "userId";
    private LinearLayout mLayoutEmptyRecyclerview;
    private RecyclerView mRecyclerViewTodo;
    private DemoDatabase mDatabase;
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
        setHasOptionsMenu(true);
        mUserId = (UUID) getArguments().getSerializable(ARGS_USER_ID);
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
        mDatabase = DemoDatabase.getInstance(getActivity());
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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search...");
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


    public void updateRecyclerview() {
        if (mDatabase.getDemoDao().getAllTasksForEveryUser(mUserId).size() > 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.GONE);
        }
        if (mDatabase.getDemoDao().getAllTasksForEveryUser(mUserId).size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        }
        mTodoAdapter.updateTasks(mDatabase.getDemoDao().getAllTasksForEveryUser(mUserId));
    }
}


