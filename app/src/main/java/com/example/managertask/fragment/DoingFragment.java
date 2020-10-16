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
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.State;
import com.example.managertask.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DoingFragment extends Fragment {
    private static final String ARGS_USER_ID = "userId";
    private RecyclerView mRecyclerViewDoing;
    private DemoDatabase mDatabase;
    private LinearLayout mLayoutEmptyRecyclerview;
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
        mUserId = (UUID) getArguments().getSerializable(ARGS_USER_ID);
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
        mDatabase = DemoDatabase.getInstance(getActivity());
        updateRecyclerview();
    }

    public void updateRecyclerview() {
        List<Task> doingTasks = mDatabase.getDemoDao().getAllTaksByState(State.DOING, mUserId);
        if (doingTasks.size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        } else {
            mLayoutEmptyRecyclerview.setVisibility(View.GONE);
            if (mDoingAdapter == null) {
                mDoingAdapter = new TaskAdapter(doingTasks, this);
                mRecyclerViewDoing.setAdapter(mDoingAdapter);
            } else {
                mDoingAdapter.setTasks(doingTasks);
                mRecyclerViewDoing.setAdapter(mDoingAdapter);
                mDoingAdapter.notifyDataSetChanged();
            }
        }
    }
}

