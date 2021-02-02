package com.example.managertask.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.managertask.R;
import com.example.managertask.controller.fragment.AdminLoginFragment;
import com.example.managertask.controller.fragment.LoginFragment;
import com.example.managertask.controller.fragment.SignUpFragment;

import java.util.UUID;

public class ContainerActivity extends AppCompatActivity implements LoginFragment.UserSignUpCallback,
        LoginFragment.UserLoginCallback, LoginFragment.AdminLoginCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_container);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, LoginFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    }


    @Override
    public void signUpClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SignUpFragment.newInstance())
                .addToBackStack(null)
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
                .replace(R.id.fragment_container, AdminLoginFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, ContainerActivity.class);
    }
}