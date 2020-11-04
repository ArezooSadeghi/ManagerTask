package com.example.managertask.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managertask.R;
import com.example.managertask.adapter.UserAdapter;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.User;

import java.util.List;

public class UserListFragment extends Fragment {
    private RecyclerView mRecyclerViewUserList;
    private UserAdapter mAdapter;
    private DemoDatabase mDatabase;

    public UserListFragment() {
    }

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
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

        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        findViews(view);
        initViews();
        setupAdapter();
        return view;
    }

    private void findViews(View view) {
        mRecyclerViewUserList = view.findViewById(R.id.recycler_view_user_list);
    }

    private void initViews() {
        mRecyclerViewUserList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupAdapter() {
        List<User> users = mDatabase.getDemoDao().getAllUsers();
        mAdapter = new UserAdapter(users, getContext(), new UserAdapter.RemoveCallback() {
            @Override
            public void removeClicked() {
                mAdapter.updateUsers(mDatabase.getDemoDao().getAllUsers());
            }
        });
        mRecyclerViewUserList.setAdapter(mAdapter);
    }
}