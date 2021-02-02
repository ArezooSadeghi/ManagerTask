package com.example.managertask.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.managertask.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {

    private TimePicker mTimePicker;
    private Timestamp mTaskTime;

    private static final String TAG = TimePickerFragment.class.getSimpleName();
    private static final String ARGS_TASK_TIME = "taskTime";
    public static final String EXTRA_USER_SELECTED_TIME = "com.example.managertask.userselectedtime";

    public static TimePickerFragment newInstance(Timestamp taskTime) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_TIME, taskTime);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskTime = (Timestamp) getArguments().getSerializable(ARGS_TASK_TIME);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.fragment_time_picker,
                null);

        findViews(view);
        initTimePicker();

        AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTimePicker.setIs24HourView(true);
                        String hour = String.valueOf(mTimePicker.getCurrentHour());
                        String minute = String.valueOf(mTimePicker.getCurrentMinute());
                        try {
                            SimpleDateFormat simpleDateFormat =
                                    new SimpleDateFormat("HH:mm");
                            Date date = simpleDateFormat
                                    .parse(String.format("%s:%s", hour, minute));
                            Timestamp userSelectedTime = new Timestamp(date.getTime());
                            sendResult(userSelectedTime);
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                        dismiss();
                    }
                })

                .setNegativeButton(android.R.string.cancel, null)
                .create();

        return dialog;
    }


    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker);
    }


    private void initTimePicker() {
        Date date = new Date(mTaskTime.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
    }


    private void sendResult(Timestamp userSelectedTime) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_TIME, userSelectedTime);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}