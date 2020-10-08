package com.example.managertask.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.managertask.R;

import java.util.Calendar;

public class DatePickerDialog extends DialogFragment {

    public static final String ARGS_TASK_DATE = "taskDate";
    private DatePicker mDatePicker;

    public DatePickerDialog() {
    }

    public static DatePickerDialog newInstance() {
        DatePickerDialog fragment = new DatePickerDialog();
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
        View view = inflater.inflate(R.layout.fragment_date_picker, null);
        findViews(view);
        initViews();
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        return dialog;
    }

    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.datepicker);
    }

    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth, null);
    }
}