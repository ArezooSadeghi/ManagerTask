package com.example.managertask.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.managertask.R;
import com.example.managertask.controller.fragment.UserListFragment;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.user_list_container);

        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.user_list_container, UserListFragment.newInstance())
                    .commit();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, UserListActivity.class);
    }
}