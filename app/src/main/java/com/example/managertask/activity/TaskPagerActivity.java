package com.example.managertask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.managertask.R;
import com.example.managertask.fragment.DoingFragment;
import com.example.managertask.fragment.DoneFragment;
import com.example.managertask.fragment.TodoFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TaskPagerActivity extends AppCompatActivity {
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskpager);
        findViews();
        initViews();
    }

    private void findViews() {
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);
    }

    private void initViews() {
        mViewPager.setAdapter(new TaskPagerAdapter(this));
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
                    return DoneFragment.newInstance();
                case 1:
                    return DoingFragment.newInstance();
                default:
                    return TodoFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return NUMBER_OF_PAGES;
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, TaskPagerActivity.class);
    }
}