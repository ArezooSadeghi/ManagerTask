package com.example.managertask.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.managertask.R;
import com.example.managertask.controller.fragment.AdminLoginPageFragment;
import com.example.managertask.controller.fragment.LoginPageFragment;
import com.example.managertask.controller.fragment.SignupPageFragment;

import java.util.UUID;

public class ContainerActivity extends AppCompatActivity implements LoginPageFragment.UserSignup,
        SignupPageFragment.BackClicked, LoginPageFragment.UserLogin, LoginPageFragment.AdminLogin {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, LoginPageFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void signupClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SignupPageFragment.newInstance())
                .commit();
    }

    @Override
    public void backClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, LoginPageFragment.newInstance())
                .commit();
    }

    @Override
    public void loginClicked(UUID userId) {
        Intent intent = TaskPagerActivity.newIntent(this, userId);
        startActivity(intent);
    }

    @Override
    public void AdminLoginClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, AdminLoginPageFragment.newInstance())
                .commit();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ContainerActivity.class);
    }
}