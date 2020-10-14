package com.example.managertask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.managertask.R;
import com.example.managertask.fragment.LoginPage;
import com.example.managertask.fragment.SignupPage;

import java.util.UUID;

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
    public void loginClicked(UUID userId) {
        Intent intent = TaskPagerActivity.newIntent(this, userId);
        startActivity(intent);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ContainerActivity.class);
    }
}