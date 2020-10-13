package com.example.managertask.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class TimePickerDialog extends DialogFragment {
    public static final String EXTRA_USER_SELECTED_TIME = "user selected time";
    public static final String ARGS_TASK_TIME = "taskTime";
    private TimePicker mTimePicker;
    private Timestamp mTaskTime;

    public TimePickerDialog() {
    }

    public static TimePickerDialog newInstance(Timestamp taskTime) {
        TimePickerDialog fragment = new TimePickerDialog();
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
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_time_picker, null);
        findViews(view);
        initTimePicker();
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(view)
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
                        }
                        dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        return dialog;
    }

    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.timepicker);
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