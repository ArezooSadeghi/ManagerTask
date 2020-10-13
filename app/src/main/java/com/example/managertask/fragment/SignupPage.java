package com.example.managertask.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.managertask.R;
import com.example.managertask.database.TaskDatabase;
import com.example.managertask.model.User;

import java.util.List;

public class SignupPage extends Fragment {
    private Button mButtonSignup, mButtonBack;
    private EditText mEditTextUsername, mEditTextPassword;
    private TaskDatabase mDatabase;
    private SignupCallbacks mCallbacks;
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
        getActivity().getIntent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup_page, container, false);
        findViews(view);
        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = TaskDatabase.getInstance(getActivity());
                List<User> users = mDatabase.getUserDao().getAllUsers();
                if (mEditTextUsername.getText().toString().equals("") || mEditTextPassword.getText()
                        .toString().equals("")) {
                    Toast.makeText(getActivity(), "please enter username and password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (users.size() == 0) {
                        addNewUser();
                    } else if (!(users.size() == 0)) {
                        boolean flag = false;
                        for (User user : users) {
                            if (user.getUsername().equals(mEditTextUsername.getText().toString())
                                    && user.getPassword().equals(mEditTextPassword.getText()
                                    .toString())) {
                                flag = true;
                                Toast.makeText(getActivity(), "Such a user already exists",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (flag == false) {
                            addNewUser();
                        }
                    }
                }
            }
        });

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.backClicked();
            }
        });
        return view;
    }

    private void addNewUser() {
        User newUser = new User();
        newUser.setUsername(mEditTextUsername.getText().toString());
        newUser.setPassword(mEditTextPassword.getText().toString());
        mDatabase.getUserDao().insert(newUser);
        Toast.makeText(getActivity(), "Your registration was successful",
                Toast.LENGTH_SHORT).show();
    }

    private void findViews(View view) {
        mButtonSignup = view.findViewById(R.id.btn_signup);
        mButtonBack = view.findViewById(R.id.btn_back);
        mEditTextUsername = view.findViewById(R.id.txt_username);
        mEditTextPassword = view.findViewById(R.id.txt_password);
    }

    public interface SignupCallbacks {
        void backClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SignupCallbacks) {
            mCallbacks = (SignupCallbacks) context;
        }
    }
}