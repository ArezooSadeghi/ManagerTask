package com.example.managertask.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.managertask.R;
import com.example.managertask.adapter.TaskAdapter;
import com.example.managertask.database.TaskDatabase;
import com.example.managertask.model.State;
import com.example.managertask.model.Task;

import java.util.List;

public class DoingFragment extends Fragment {
    private RecyclerView mRecyclerViewDoing;
    private TaskDatabase mDatabase;
    private LinearLayout mLayoutEmptyRecyclerview;
    private TaskAdapter mDoingAdapter;

    public DoingFragment() {
    }

    public static DoingFragment newInstance() {
        DoingFragment fragment = new DoingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mDatabase = TaskDatabase.getInstance(getActivity());
        List<Task> doingTasks = mDatabase.taskDao().getStateTasks(State.DOING);
        if (doingTasks.size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        } else {
            if (mDoingAdapter == null) {
                mDoingAdapter = new TaskAdapter(doingTasks, this);
            } else {
                mDoingAdapter.setTasks(doingTasks);
                mDoingAdapter.notifyDataSetChanged();
            }
                mRecyclerViewDoing.setAdapter(mDoingAdapter);
            }
        }
    }
