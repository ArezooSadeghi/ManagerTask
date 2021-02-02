package com.example.managertask.controller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.managertask.R;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

public class LoginFragment extends Fragment {

    private Button mButtonSignUp, mButtonLoginAdmin;
    private FloatingActionButton mFabLogin;
    private EditText mEditTextUserName, mEditTextPassword;
    private DemoDatabase mDatabase;
    private UserSignUpCallback mUserSignUpCallback;
    private UserLoginCallback mUserLoginCallback;
    private AdminLoginCallback mAdminLoginCallback;


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        findViews(view);
        initViews();
        setListeners();

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UserSignUpCallback) {
            mUserSignUpCallback = (UserSignUpCallback) context;
        }
        if (context instanceof UserLoginCallback) {
            mUserLoginCallback = (UserLoginCallback) context;
        }
        if (context instanceof AdminLoginCallback) {
            mAdminLoginCallback = (AdminLoginCallback) context;
        }
    }


    private void findViews(View view) {
        mButtonSignUp = view.findViewById(R.id.btn_sign_up);
        mFabLogin = view.findViewById(R.id.fab_login);
        mButtonLoginAdmin = view.findViewById(R.id.btn_login_admin);
        mEditTextUserName = view.findViewById(R.id.txt_username);
        mEditTextPassword = view.findViewById(R.id.txt_password);
    }


    private void initViews() {
        mEditTextPassword.setTransformationMethod(new PasswordTransformationMethod());
    }


    private void setListeners() {
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserSignUpCallback.signUpClicked();
            }
        });

        mFabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> users = mDatabase.getDemoDao().getAllUsers();
                if (mEditTextUserName.getText().toString().isEmpty()
                        || mEditTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), R.string.enter_username_password,
                            Toast.LENGTH_SHORT).show();
                }
                if (users.size() == 0) {
                    Toast.makeText(getActivity(), R.string.not_found_username,
                            Toast.LENGTH_SHORT).show();
                }

                boolean flag = true;
                for (User user : users) {
                    if (user.getUsername().equals(mEditTextUserName.getText().toString())
                            && (user.getPassword().equals(mEditTextPassword.getText().toString()))) {
                        flag = false;
                        mUserLoginCallback.loginClicked(user.getUserId());
                        break;
                    }
                }
                if (flag == true) {
                    Toast.makeText(getActivity(), R.string.not_found_username,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdminLoginCallback.AdminLoginClicked();
            }
        });
    }


    public interface UserSignUpCallback {
        void signUpClicked();
    }


    public interface UserLoginCallback {
        void loginClicked(UUID userId);
    }


    public interface AdminLoginCallback {
        void AdminLoginClicked();
    }
}