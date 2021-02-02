package com.example.managertask.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.managertask.R;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.User;

import java.util.List;

public class SignUpFragment extends Fragment {

    private Button mButtonSignUp;
    private EditText mEditTextUsername, mEditTextPassword;
    private DemoDatabase mDatabase;


    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
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

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        findViews(view);
        setListeners();

        return view;
    }


    private void findViews(View view) {
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);
        mEditTextUsername = view.findViewById(R.id.txt_username);
        mEditTextPassword = view.findViewById(R.id.txt_password);
    }


    private void setListeners() {
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> users = mDatabase.getDemoDao().getAllUsers();
                if (mEditTextUsername.getText().toString().isEmpty() || mEditTextPassword.getText()
                        .toString().isEmpty()) {
                    Toast.makeText(getActivity(), R.string.enter_username_password,
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
                                Toast.makeText(getActivity(), R.string.exists_user,
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
    }


    private void addNewUser() {
        User newUser = new User();
        newUser.setUsername(mEditTextUsername.getText().toString());
        newUser.setPassword(mEditTextPassword.getText().toString());
        mDatabase.getDemoDao().insertUser(newUser);
        Toast.makeText(getActivity(), R.string.successful_sign_up,
                Toast.LENGTH_SHORT).show();
    }
}