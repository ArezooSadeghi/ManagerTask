package com.example.managertask.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.managertask.R;
import com.example.managertask.controller.fragment.TaskDetailFragment;
import com.example.managertask.controller.fragment.UsersTaskListFragment;

public class UsersTaskListActivity extends AppCompatActivity implements
        TaskDetailFragment.SaveDetailCallback, TaskDetailFragment.DeleteDetailCallback {

    private UsersTaskListFragment mFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_users_task_list);

        findViews();
        initToolbar();


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.user_task_list_container);

        if (fragment == null) {
            mFragment = UsersTaskListFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.user_task_list_container, mFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_task_list_menu, menu);
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
                return super.onOptionsItemSelected(item);
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


    private void findViews() {
        mToolbar = findViewById(R.id.users_task_list_toolbar);
    }


    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, UsersTaskListActivity.class);
    }
}