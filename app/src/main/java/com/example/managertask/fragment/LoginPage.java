package com.example.managertask.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.managertask.R;

public class LoginPage extends Fragment {
    private Button mButtonSignup;
    private LoginCallbacks mCallbacks;

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
        return setListeners(view);
    }

    private void findViews(View view) {
        mButtonSignup = view.findViewById(R.id.btn_signup);
    }

    private View setListeners(View view) {
        mButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.signupClicked();
            }
        });
        return view;
    }

    public interface LoginCallbacks {
        void signupClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginCallbacks) {
            mCallbacks = (LoginCallbacks) context;
        }
    }
}