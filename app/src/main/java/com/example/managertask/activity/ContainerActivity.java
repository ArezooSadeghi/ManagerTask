package com.example.managertask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.managertask.R;
import com.example.managertask.fragment.LoginPage;
import com.example.managertask.fragment.SignupPage;

public class ContainerActivity extends AppCompatActivity implements LoginPage.LoginCallbacks,
        SignupPage.SignupCallbacks, LoginPage.LoginCallbacksTaskPager {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, LoginPage.newInstance())
                    .commit();
        }
    }

    @Override
    public void signupClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SignupPage.newInstance())
                .commit();
    }

    @Override
    public void backClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, LoginPage.newInstance())
                .commit();
    }

    @Override
    public void loginClicked() {
        Intent intent = TaskPagerActivity.newIntent(this);
        startActivity(intent);
    }
}