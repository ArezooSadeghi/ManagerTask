package com.example.managertask.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.managertask.R;

import java.util.Calendar;

public class TimePickerDialog extends DialogFragment {
    private TimePicker mTimePicker;

    public TimePickerDialog() {
    }

    public static TimePickerDialog newInstance() {
        TimePickerDialog fragment = new TimePickerDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_time_picker, null);
        findViews(view);
        initViews();
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        return dialog;
    }

    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.timepicker);
    }

    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
    }
}