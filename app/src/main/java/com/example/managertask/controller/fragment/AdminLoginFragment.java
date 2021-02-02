package com.example.managertask.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.managertask.R;
import com.example.managertask.controller.activity.UsersTaskListActivity;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.Admin;

import java.util.List;

public class AdminLoginFragment extends Fragment {

    private EditText mTextUsername, mTextPassword;
    private Button mButtonLogin;
    private DemoDatabase mDatabase;


    public static AdminLoginFragment newInstance() {
        AdminLoginFragment fragment = new AdminLoginFragment();
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

        View view = inflater.inflate(
                R.layout.fragment_admin_login, container, false);

        findViews(view);
        setListeners();

        return view;
    }


    private void findViews(View view) {
        mButtonLogin = view.findViewById(R.id.btn_login_admin);
        mTextUsername = view.findViewById(R.id.txt_admin_username);
        mTextPassword = view.findViewById(R.id.txt_admin_password);
    }


    private void setListeners() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Admin> adminList = mDatabase.getDemoDao().getAllAdmins();
                if (mTextUsername.getText().toString().isEmpty() || mTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), R.string.enter_username_password, Toast.LENGTH_SHORT).show();
                }
                for (Admin admin : adminList) {
                    if (admin.getUsername().equals(mTextUsername.getText().toString()) &&
                            admin.getPassword().equals(mTextPassword.getText().toString())) {
                        Intent intent = UsersTaskListActivity.newIntent(getContext());
                        startActivity(intent);
                    } else {
                        Toast.makeText(
                                getContext(),
                                R.string.incorrect_information,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}