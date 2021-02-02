package com.example.managertask.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.managertask.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    private DatePicker mDatePicker;
    private Date mTaskDate;

    private static final String ARGS_TASK_DATE = "taskDate";
    public static final String EXTRA_USER_SELECTED_DATE = "com.example.managertask.userselecteddate";


    public static DatePickerFragment newInstance(Date taskDate) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_DATE, taskDate);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskDate = (Date) getArguments().getSerializable(ARGS_TASK_DATE);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.fragment_date_picker,
                null);

        findViews(view);
        initDatePicker();

        AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Date userSelectedDate = extractDateFromDatePicker();
                        sendResult(userSelectedDate);
                        dismiss();
                    }
                })

                .setNegativeButton(android.R.string.cancel, null)
                .create();

        return dialog;
    }


    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.date_picker);
    }


    public void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTaskDate);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth, null);
    }


    private Date extractDateFromDatePicker() {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int dayOfMonth = mDatePicker.getDayOfMonth();
        GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        return calendar.getTime();
    }


    public void sendResult(Date userSelectedDate) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_DATE, userSelectedDate);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}