package com.example.managertask.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.managertask.R;
import com.example.managertask.controller.fragment.DoingFragment;
import com.example.managertask.controller.fragment.DoneFragment;
import com.example.managertask.controller.fragment.NewTaskFragment;
import com.example.managertask.controller.fragment.RemoveTasksFragment;
import com.example.managertask.controller.fragment.TaskDetailFragment;
import com.example.managertask.controller.fragment.TodoFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.UUID;

public class TaskPagerActivity extends AppCompatActivity implements NewTaskFragment.SaveClickedCallback,
        TaskDetailFragment.SaveDetailCallback, TaskDetailFragment.DeleteDetailCallback,
        RemoveTasksFragment.ItemRemovedAllTasksCallback {

    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private Button mButtonAddNewTask;
    private UUID mUserId;
    private DoneFragment mDoneFragment;
    private DoingFragment mDoingFragment;
    private TodoFragment mTodoFragment;

    private static final String TAG = TaskPagerActivity.class.getSimpleName();
    private static final String EXTRA_USER_ID = "com.example.managertask.userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskpager);

        findViews();
        initToolbar();
        setupAdapter();
        initViews();
        setListeners();
        handleIntent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_pager_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                Intent intent = ContainerActivity.newIntent(this);
                startActivity(intent);
                return true;
            case R.id.item_remove:
                RemoveTasksFragment fragment = RemoveTasksFragment.newInstance();
                fragment.show(getSupportFragmentManager(), TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void newTaskSaveClicked() {
        if (mDoneFragment != null) {
            mDoneFragment.updateRecyclerview();
        }
        if (mDoingFragment != null) {
            mDoingFragment.updateRecyclerview();
        }
        if (mTodoFragment != null) {
            mTodoFragment.updateRecyclerview();
        }
    }


    @Override
    public void saveClicked() {
        if (mDoneFragment != null) {
            mDoneFragment.updateRecyclerview();
        }
        if (mDoingFragment != null) {
            mDoingFragment.updateRecyclerview();
        }
        if (mTodoFragment != null) {
            mTodoFragment.updateRecyclerview();
        }
    }

    @Override
    public void deleteClicked() {
        if (mDoneFragment != null) {
            mDoneFragment.updateRecyclerview();
        }
        if (mDoingFragment != null) {
            mDoingFragment.updateRecyclerview();
        }
        if (mTodoFragment != null) {
            mTodoFragment.updateRecyclerview();
        }
    }


    @Override
    public void itemRemovedClicked() {
        if (mDoneFragment != null) {
            mDoneFragment.removeAllTasks();
        }
        if (mDoingFragment != null) {
            mDoingFragment.removeAllTasks();
        }
        if (mTodoFragment != null) {
            mTodoFragment.removeAllTasks();
        }
    }


    private void findViews() {
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tab_layout);
        mButtonAddNewTask = findViewById(R.id.btn_add_new_task);
        mToolbar = findViewById(R.id.toolbar_task_pager);
    }


    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    private void setupAdapter() {
        TaskPagerAdapter adapter = new TaskPagerAdapter(this);
        mViewPager.setAdapter(adapter);
    }


    private void initViews() {
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText(R.string.title_done_tab);
                                break;
                            case 1:
                                tab.setText(R.string.title_doing_tab);
                                break;
                            default:
                                tab.setText(R.string.title_todo_tab);
                                break;
                        }
                    }
                });
        tabLayoutMediator.attach();
    }


    private void setListeners() {
        mButtonAddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewTaskFragment newTaskFragment = NewTaskFragment.newInstance(mUserId);
                newTaskFragment.show(getSupportFragmentManager(), TAG);
            }
        });
    }


    private void handleIntent() {
        Intent intent = this.getIntent();
        mUserId = (UUID) intent.getSerializableExtra(EXTRA_USER_ID);
    }


    private class TaskPagerAdapter extends FragmentStateAdapter {

        private final static int NUMBER_OF_PAGES = 3;

        public TaskPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    mDoneFragment = DoneFragment.newInstance(mUserId);
                    return mDoneFragment;
                case 1:
                    mDoingFragment = DoingFragment.newInstance(mUserId);
                    return mDoingFragment;
                default:
                    mTodoFragment = TodoFragment.newInstance(mUserId);
                    return mTodoFragment;
            }
        }

        @Override
        public int getItemCount() {
            return NUMBER_OF_PAGES;
        }
    }


    public static Intent newIntent(Context context, UUID userId) {
        Intent intent = new Intent(context, TaskPagerActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }
}