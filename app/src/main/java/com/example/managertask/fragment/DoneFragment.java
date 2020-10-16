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

public class DoneFragment extends Fragment {
    private static final String ARGS_USER_ID = "userId";
    private RecyclerView mRecyclerViewDone;
    private DemoDatabase mDatabase;
    private LinearLayout mLayoutEmptyRecyclerview;
    private TaskAdapter mDoneAdapter;
    private UUID mUserId;

    public DoneFragment() {
    }

    public static DoneFragment newInstance(UUID userId) {
        DoneFragment fragment = new DoneFragment();
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
        mDatabase = DemoDatabase.getInstance(getActivity());
        updateRecyclerview();
    }

    public void updateRecyclerview() {
        List<Task> doneTasks = mDatabase.getDemoDao().getAllTaksByState(State.DONE, mUserId);
        if (doneTasks.size() == 0) {
            mLayoutEmptyRecyclerview.setVisibility(View.VISIBLE);
        } else {
            mLayoutEmptyRecyclerview.setVisibility(View.GONE);
            if (mDoneAdapter == null) {
                mDoneAdapter = new TaskAdapter(doneTasks, this);
                mRecyclerViewDone.setAdapter(mDoneAdapter);
            } else {
                mDoneAdapter.setTasks(doneTasks);
                mRecyclerViewDone.setAdapter(mDoneAdapter);
                mDoneAdapter.notifyDataSetChanged();
            }
        }
    }
}
