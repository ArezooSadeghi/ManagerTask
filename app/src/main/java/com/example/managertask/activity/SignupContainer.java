package com.example.managertask.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.managertask.R;
import com.example.managertask.fragment.SignupPage;

public class SignupContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_container);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.signup_container);
        if (fragment == null) {
            SignupPage signupPage = SignupPage.newInstance();
            fragmentManager.beginTransaction().add(R.id.signup_container, signupPage).commit();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SignupContainer.class);
    }
}