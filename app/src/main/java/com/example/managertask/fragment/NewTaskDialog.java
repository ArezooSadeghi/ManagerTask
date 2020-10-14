package com.example.managertask.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.managertask.R;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.State;
import com.example.managertask.model.Task;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class NewTaskDialog extends DialogFragment {
    private static final String ARGS_USER_ID = "userId";
    private Button mButtonSave, mButtonDate, mButtonTime;
    private EditText mEditTextTitle, mEditTextDescription;
    private CheckBox mCheckBoxDone;
    private DemoDatabase mDatabase;
    private Task mTask;
    private UUID mUserId;
    private Date mUserSelectedDate;
    private Timestamp mUserSelectedTime;
    public static final String TAG1 = "DANTF";
    public static final String TAG2 = "DANTF";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;

    public NewTaskDialog() {
    }

    public static NewTaskDialog newInstance(UUID userId) {
        NewTaskDialog fragment = new NewTaskDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = (UUID) getArguments().getSerializable(ARGS_USER_ID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_add_new_task, null);
        findViews(view);
        setListeners();
        initViews();
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        return dialog;
    }

    private void findViews(View view) {
        mButtonSave = view.findViewById(R.id.btn_save);
        mButtonDate = view.findViewById(R.id.btn_date);
        mButtonTime = view.findViewById(R.id.btn_time);
        mCheckBoxDone = view.findViewById(R.id.checkbox_done);
        mEditTextTitle = view.findViewById(R.id.edittext_title);
        mEditTextDescription = view.findViewById(R.id.edittext_description);
    }

    private void setListeners() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask = new Task();
                mTask.setUserTaskId(mUserId);
                mTask.setTitle(mEditTextTitle.getText().toString());
                mTask.setDescription(mEditTextDescription.getText().toString());
                mTask.setDate(mUserSelectedDate);
                mTask.setTime(mUserSelectedTime);
                if (mCheckBoxDone.isChecked()) {
                    mTask.setState(State.DONE);
                } else {
                    mTask.setState(State.DOING);
                }
                mDatabase = DemoDatabase.getInstance(getActivity());
                mDatabase.getDemoDao().insertTask(mTask);
                dismiss();
            }
        });

        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerFragment = DatePickerDialog.newInstance(new Date());
                datePickerFragment.setTargetFragment(NewTaskDialog.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), TAG1);
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerFragment = TimePickerDialog.newInstance(new Timestamp(new Date().getTime()));
                timePickerFragment.setTargetFragment(NewTaskDialog.this, REQUEST_CODE_TIME_PICKER);
                timePickerFragment.show(getActivity().getSupportFragmentManager(), TAG2);
            }
        });
    }

    private void initViews() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        mButtonDate.setText(dateFormat.format(date));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        mButtonTime.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE_DATE_PICKER) {
            mUserSelectedDate = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_USER_SELECTED_DATE);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            mButtonDate.setText(dateFormat.format(mUserSelectedDate));
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            mUserSelectedTime = (Timestamp) data.getSerializableExtra(TimePickerDialog.EXTRA_USER_SELECTED_TIME);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mUserSelectedTime.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            mButtonTime.setText(simpleDateFormat.format(calendar.getTime()));
        }
    }
}