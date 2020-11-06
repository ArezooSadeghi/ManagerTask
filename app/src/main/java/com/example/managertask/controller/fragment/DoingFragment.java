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
import com.example.managertask.model.State;
import com.example.managertask.model.Task;

import java.util.List;
import java.util.UUID;

public class DoingFragment extends Fragment {

    private static final String ARGS_USER_ID = "userId";
    private LinearLayout mLayoutEmptyRecyclerview;
    private RecyclerView mRecyclerViewDoing;
    private DemoDatabase mDatabase;
    private TaskAdapter mDoingAdapter;
    private UUID mUserId;

    public DoingFragment() {

    }


    public static DoingFragment newInstance(UUID userId) {
        DoingFragment fragment = new DoingFragment();
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

        View view = inflater.inflate(R.layout.fragment_doing, container, false);
        findViews(view);
        initViews();
        return view;
    }


    private void findViews(View view) {
        mRecyclerViewDoing = view.findViewById(R.id.doing_recyclerview);
        mLayoutEmptyRecyclerview = view.findViewById(R.id.layout_empty_recyclerview);
    }


    private void initViews() {
        mRecyclerViewDoing.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Task> doingTasks = mDatabase.getDemoDao().getAllTaksByState(State.DOING, mUserId);
        if (doingTasks.size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        }
        if (mDoingAdapter == null) {
            mDoingAdapter = new TaskAdapter(doingTasks, this);
            mRecyclerViewDoing.setAdapter(mDoingAdapter);
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
                if (mDoingAdapter != null) {
                    mDoingAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void updateRecyclerview() {
        if (mDatabase.getDemoDao().getAllTaksByState(State.DOING, mUserId).size() > 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.GONE);
        }
        if (mDatabase.getDemoDao().getAllTaksByState(State.DOING, mUserId).size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        }
        mDoingAdapter.updateTasks(mDatabase.getDemoDao().getAllTaksByState(State.DOING, mUserId));
    }

    public void allRemoved() {
        mDatabase.getDemoDao().deleteAllTasksForUser(mUserId);
        updateRecyclerview();
    }
}


