package com.example.managertask.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.managertask.R;
import com.example.managertask.controller.fragment.TaskDetailFragment;
import com.example.managertask.controller.fragment.UserTaskListFragment;

public class UserTaskListActivity extends AppCompatActivity implements
        TaskDetailFragment.TaskInformationCallbacks, TaskDetailFragment.TaskInformationCallbacks1 {
    private UserTaskListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.user_task_list_container);

        if (fragment == null) {
            mFragment = UserTaskListFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.user_task_list_container, mFragment)
                    .commit();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, UserTaskListActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_admin_logout:
                Intent logoutIntent = ContainerActivity.newIntent(this);
                startActivity(logoutIntent);
                return true;
            case R.id.item_list_all_users:
                Intent showUserIntent = UserListActivity.newIntent(this);
                startActivity(showUserIntent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void saveClicked() {
        mFragment.updateRecyclerview();
    }

    @Override
    public void deleteClicked() {
        mFragment.updateRecyclerview();
    }
}