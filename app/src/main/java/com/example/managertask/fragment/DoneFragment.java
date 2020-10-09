package com.example.managertask.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managertask.R;
import com.example.managertask.adapter.TaskAdapter;
import com.example.managertask.database.TaskDatabase;
import com.example.managertask.model.State;
import com.example.managertask.model.Task;

import java.util.List;

public class DoneFragment extends Fragment {
    private RecyclerView mRecyclerViewDone;
    private TaskDatabase mDatabase;
    private LinearLayout mLayoutEmptyRecyclerview;
    private TaskAdapter mDoneAdapter;

    public DoneFragment() {
    }

    public static DoneFragment newInstance() {
        DoneFragment fragment = new DoneFragment();
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

        View view = inflater.inflate(R.layout.fragment_done, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewDone = view.findViewById(R.id.done_recyclerview);
        mLayoutEmptyRecyclerview = view.findViewById(R.id.layout_empty_recyclerview);
    }

    private void initViews() {
        mRecyclerViewDone.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatabase = TaskDatabase.getInstance(getActivity());
        List<Task> doneTasks = mDatabase.taskDao().getStateTasks(State.DONE);
        if (doneTasks.size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        } else {
            if (mDoneAdapter == null) {
                mDoneAdapter = new TaskAdapter(doneTasks, this);
            } else {
                mDoneAdapter.setTasks(doneTasks);
                mDoneAdapter.notifyDataSetChanged();
            }
                mRecyclerViewDone.setAdapter(mDoneAdapter);
            }
        }
    }
