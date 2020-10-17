package com.example.managertask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.managertask.R;
import com.example.managertask.fragment.DoingFragment;
import com.example.managertask.fragment.DoneFragment;
import com.example.managertask.fragment.NewTaskDialog;
import com.example.managertask.fragment.TaskInformationDialog;
import com.example.managertask.fragment.TodoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.UUID;

public class TaskPagerActivity extends AppCompatActivity implements NewTaskDialog.Callbacks,
        TaskInformationDialog.TaskInformationCallbacks, TaskInformationDialog.TaskInformationCallbacks1 {
    private static final String EXTRA_USER_ID = "com.example.managertask.userId";
    private static final String TAG = "TPA";
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFabAdd;
    private UUID mUserId;
    private TaskPagerAdapter mAdapter;
    private DoneFragment mDoneFragment;
    private DoingFragment mDoingFragment;
    private TodoFragment mTodoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskpager);
        findViews();
        setListeners();
        initViews();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = this.getIntent();
        mUserId = (UUID) intent.getSerializableExtra(EXTRA_USER_ID);
    }

    private void findViews() {
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);
        mFabAdd = findViewById(R.id.fab_add);
    }


    private void setListeners() {
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewTaskDialog dialogAddNewTaskFragment = NewTaskDialog.newInstance(mUserId);
                dialogAddNewTaskFragment.show(getSupportFragmentManager(), TAG);
            }
        });
    }


    private void initViews() {
        mAdapter = new TaskPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText(R.string.done_tab);
                                break;
                            case 1:
                                tab.setText(R.string.doing_tab);
                                break;
                            default:
                                tab.setText(R.string.todo_tab);
                                break;
                        }
                    }
                });
        tabLayoutMediator.attach();
    }

    @Override
    public void okClicked() {
        mDoneFragment.updateRecyclerview();
        mDoingFragment.updateRecyclerview();
        mTodoFragment.updateRecyclerview();
    }

    @Override
    public void saveClicked() {
        mDoneFragment.updateRecyclerview();
        mDoingFragment.updateRecyclerview();
        mTodoFragment.updateRecyclerview();
    }

    @Override
    public void deleteClicked() {
        mDoneFragment.updateRecyclerview();
        mDoingFragment.updateRecyclerview();
        mTodoFragment.updateRecyclerview();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            Intent intent = ContainerActivity.newIntent(this);
            startActivity(intent);
        }
        return true;
    }
}