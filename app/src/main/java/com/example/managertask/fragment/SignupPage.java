package com.example.managertask.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.managertask.R;
import com.example.managertask.database.TaskDatabase;
import com.example.managertask.model.User;

public class SignupPage extends Fragment {
    private Button mButtonSignup;
    private EditText mEditTextUsername, mEditTextPassword;
    private TaskDatabase mDatabase;
    private User mUser;

    public SignupPage() {
    }

    public static SignupPage newInstance() {
        SignupPage fragment = new SignupPage();
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

        View view = inflater.inflate(R.layout.fragment_signup_page, container, false);
        findViews(view);
        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser = new User();
                mUser.setUsername(mEditTextUsername.getText().toString());
                mUser.setPassword(mEditTextPassword.getText().toString());
                mDatabase = TaskDatabase.getInstance(getActivity());
                mDatabase.taskDao().insert(mUser);
            }
        });
        return view;
    }

    private void findViews(View view) {
        mButtonSignup = view.findViewById(R.id.btn_signup);
        mEditTextUsername = view.findViewById(R.id.txt_username);
        mEditTextPassword = view.findViewById(R.id.txt_password);
    }
}