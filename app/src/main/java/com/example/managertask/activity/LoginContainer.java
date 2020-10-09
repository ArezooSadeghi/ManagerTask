package com.example.managertask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.managertask.R;
import com.example.managertask.fragment.LoginPage;

public class LoginContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.login_container);
        if (fragment == null) {
            LoginPage loginPage = LoginPage.newInstance();
            fragmentManager.beginTransaction().add(R.id.login_container, loginPage).commit();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginContainer.class);
    }
}