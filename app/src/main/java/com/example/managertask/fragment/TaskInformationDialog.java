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
import com.example.managertask.database.TaskDatabase;
import com.example.managertask.model.State;
import com.example.managertask.model.Task;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskInformationDialog extends DialogFragment {
    public static final String TASK_ID = "taskId";
    public static final String TAG = "TAG";
    public static final String TAG_2 = "Tag2";
    public static final int DATE_PICKER_REQUEST_CODE = 0;
    public static final int TIME_PICKER_REQUEST_CODE = 1;
    private EditText mEditTextTitle, mEditTextDescription;
    private Button mButtonDate, mButtonTime, mButtonSave, mButtonDelete, mButtonEdit;
    private CheckBox mCheckBoxDone;
    private long mTaskId;
    private Task mTask;
    private TaskDatabase mDatabase;
    private Date mUserSelectedDate;
    private Timestamp mUserSelectedTime;

    public TaskInformationDialog() {
    }

    public static TaskInformationDialog newInstance(long taskId) {
        TaskInformationDialog fragment = new TaskInformationDialog();
        Bundle args = new Bundle();
        args.putLong(TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId = getArguments().getLong(TASK_ID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_task_information_dialog, null);
        findViews(view);
        initViews();
        setListeners();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        return dialog;
    }

    private void setListeners() {
        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditTextTitle.setFocusableInTouchMode(true);
                mEditTextDescription.setFocusableInTouchMode(true);
                mButtonDate.setEnabled(true);
                mButtonTime.setEnabled(true);
                mCheckBoxDone.setEnabled(true);
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask.setTitle(mEditTextTitle.getText().toString());
                mTask.setDescription(mEditTextDescription.getText().toString());
                mTask.setDate(mUserSelectedDate);
                mTask.setTime(mUserSelectedTime);
                if (mCheckBoxDone.isChecked()) {
                    mTask.setState(State.DONE);
                } else {
                    mTask.setState(State.DOING);
                }
                mDatabase.getTaskDao().update(mTask);
                dismiss();
            }
        });

        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(mTask.getDate());
                datePickerDialog.setTargetFragment(TaskInformationDialog.this,
                        DATE_PICKER_REQUEST_CODE);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), TAG);
            }
        });
        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(mTask.getTime());
                timePickerDialog.setTargetFragment(TaskInformationDialog.this,
                        TIME_PICKER_REQUEST_CODE);
                timePickerDialog.show(getActivity().getSupportFragmentManager(), TAG_2);
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getTaskDao().delete(mTask);
                dismiss();
            }
        });
    }

    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.title_task_information);
        mEditTextDescription = view.findViewById(R.id.description_task_information);
        mButtonDate = view.findViewById(R.id.date_task_information);
        mButtonTime = view.findViewById(R.id.time_task_information);
        mButtonSave = view.findViewById(R.id.save_task_information);
        mButtonDelete = view.findViewById(R.id.delete_task_information);
        mButtonEdit = view.findViewById(R.id.edit_task_information);
        mCheckBoxDone = view.findViewById(R.id.state_task_information);
    }

    public void initViews() {
        mDatabase = TaskDatabase.getInstance(getActivity());
        List<Task> tasks = mDatabase.getTaskDao().getAllTasks();
        for (Task task : tasks) {
            if (task.getTaskId() == mTaskId) {
                mTask = task;
                mEditTextTitle.setText(mTask.getTitle());
                mEditTextTitle.setFocusable(false);
                mEditTextDescription.setText(mTask.getDescription());
                mEditTextDescription.setFocusable(false);
                mButtonDate.setEnabled(false);
                mButtonTime.setEnabled(false);
                if (mTask.getState().equals(State.DONE)) {
                    mCheckBoxDone.setChecked(true);
                    mCheckBoxDone.setEnabled(false);
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                mButtonDate.setText(dateFormat.format(mTask.getDate()));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(mTask.getTime().getTime());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                mButtonTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }
        if (requestCode == DATE_PICKER_REQUEST_CODE) {
            mUserSelectedDate = (Date) data.getSerializableExtra(DatePickerDialog
                    .EXTRA_USER_SELECTED_DATE);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            mButtonDate.setText(dateFormat.format(mUserSelectedDate));
        }

        if (requestCode == TIME_PICKER_REQUEST_CODE) {
            mUserSelectedTime = (Timestamp) data.getSerializableExtra(TimePickerDialog
                    .EXTRA_USER_SELECTED_TIME);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mUserSelectedTime.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            mButtonTime.setText(simpleDateFormat.format(calendar.getTime()));
        }
    }
}