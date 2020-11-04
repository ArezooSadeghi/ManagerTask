package com.example.managertask.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.managertask.R;
import com.example.managertask.controller.fragment.UsersListFragment;

public class UsersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.user_list_container);

        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.user_list_container, UsersListFragment.newInstance())
                    .commit();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, UsersListActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_admin_logout) {
            Intent intent = ContainerActivity.newIntent(this);
            startActivity(intent);
        }
        return true;
    }
}