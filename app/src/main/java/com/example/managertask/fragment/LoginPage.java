package com.example.managertask.fragment;

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

import java.util.List;
import java.util.UUID;

public class LoginPage extends Fragment {
    private Button mButtonSignup, mButtonLogin;
    private EditText mEditTextUsename, mEditTextPassword;
    private DemoDatabase mDatabase;
    private LoginCallbacks mCallbacks;
    private LoginCallbacksTaskPager mCallbacksTaskPager;

    public LoginPage() {
    }

    public static LoginPage newInstance() {
        LoginPage fragment = new LoginPage();
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

        View view = inflater.inflate(R.layout.fragment_login_page, container, false);
        findViews(view);
        initViews();
        setListeners();
        return view;
    }

    private void findViews(View view) {
        mButtonSignup = view.findViewById(R.id.btn_signup);
        mButtonLogin = view.findViewById(R.id.btn_login);
        mEditTextUsename = view.findViewById(R.id.txt_username);
        mEditTextPassword = view.findViewById(R.id.txt_password);
    }


    private void initViews() {
        mEditTextPassword.setTransformationMethod(new PasswordTransformationMethod());
    }


    private void setListeners() {
        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.signupClicked();
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = DemoDatabase.getInstance(getActivity());
                List<User> users = mDatabase.getDemoDao().getAllUsers();
                if (mEditTextUsename.getText().toString().equals("")
                        || mEditTextPassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "please enter username and password",
                            Toast.LENGTH_SHORT).show();
                }
                if (users.size() == 0) {
                    Toast.makeText(getActivity(), "Username not found",
                            Toast.LENGTH_SHORT).show();
                }

                boolean flag = true;
                for (User user : users) {
                    if (user.getUsername().equals(mEditTextUsename.getText().toString())
                            && (user.getPassword().equals(mEditTextPassword.getText().toString()))) {
                        flag = false;
                        mCallbacksTaskPager.loginClicked(user.getUserId());
                        break;
                    }
                }
                if (flag == true) {
                    Toast.makeText(getActivity(), "Username not found",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface LoginCallbacks {
        void signupClicked();
    }

    public interface LoginCallbacksTaskPager {
        void loginClicked(UUID userId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginCallbacks) {
            mCallbacks = (LoginCallbacks) context;
        }

        if (context instanceof LoginCallbacksTaskPager) {
            mCallbacksTaskPager = (LoginCallbacksTaskPager) context;
        }
    }
}