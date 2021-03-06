package com.example.managertask.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.example.managertask.utils.DateUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDetailFragment extends DialogFragment {


    private EditText mEditTextTitle, mEditTextDescription;
    private Button mButtonDate, mButtonTime, mButtonSave, mButtonDelete, mButtonShare;
    private CheckBox mCheckBoxDone;
    private UUID mTaskId;
    private Task mTask;
    private DemoDatabase mDatabase;
    private Date mUserSelectedDate;
    private Timestamp mUserSelectedTime;
    private SaveDetailCallback mSaveDetailCallback;
    private DeleteDetailCallback mDeleteDetailCallback;

    private static final int DATE_PICKER_REQUEST_CODE = 0;
    private static final int TIME_PICKER_REQUEST_CODE = 1;

    private static final String ARGS_TASK_ID = "taskId";
    private static final String TAG = TaskDetailFragment.class.getSimpleName();


    public static TaskDetailFragment newInstance(UUID taskId) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskId = (UUID) getArguments().getSerializable(ARGS_TASK_ID);
        mDatabase = DemoDatabase.getInstance(getContext());
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_detail_task, null);

        findViews(view);
        initViews();
        setListeners();

        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }
        if (requestCode == DATE_PICKER_REQUEST_CODE) {
            mUserSelectedDate = (Date) data.getSerializableExtra(DatePickerFragment
                    .EXTRA_USER_SELECTED_DATE);
            mButtonDate.setText(DateUtils.dateFormating(mUserSelectedDate));
        }
        if (requestCode == TIME_PICKER_REQUEST_CODE) {
            mUserSelectedTime = (Timestamp) data.getSerializableExtra(TimePickerFragment
                    .EXTRA_USER_SELECTED_TIME);
            mButtonTime.setText(DateUtils.nowTimeStringFormating(mUserSelectedTime));
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SaveDetailCallback) {
            mSaveDetailCallback = (SaveDetailCallback) context;
        }
        if (context instanceof DeleteDetailCallback) {
            mDeleteDetailCallback = (DeleteDetailCallback) context;
        }
    }


    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.edit_text_title);
        mEditTextDescription = view.findViewById(R.id.edit_text_description);
        mButtonDate = view.findViewById(R.id.btn_date);
        mButtonTime = view.findViewById(R.id.btn_time);
        mButtonSave = view.findViewById(R.id.btn_save);
        mButtonDelete = view.findViewById(R.id.btn_delete);
        mButtonShare = view.findViewById(R.id.btn_share);
        mCheckBoxDone = view.findViewById(R.id.checkbox_done);
    }


    public void initViews() {
        List<Task> tasks = mDatabase.getDemoDao().getAllTasks();
        for (Task task : tasks) {
            if (mTaskId.equals(task.getTaskId())) {
                mTask = task;
                mEditTextTitle.setText(mTask.getTitle());
                mEditTextDescription.setText(mTask.getDescription());
                if (mTask.getState().equals(State.DONE)) {
                    mCheckBoxDone.setChecked(true);
                }
                mButtonDate.setText(DateUtils.dateFormating(mTask.getDate()));
                mButtonTime.setText(DateUtils.nowTimeStringFormating(mTask.getTime()));
            }
        }
    }


    private void setListeners() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask.setTitle(mEditTextTitle.getText().toString());
                mTask.setDescription(mEditTextDescription.getText().toString());
                if (mUserSelectedDate == null) {
                    mTask.setDate(mTask.getDate());
                } else {
                    mTask.setDate(mUserSelectedDate);
                }
                if (mUserSelectedTime == null) {
                    mTask.setDate(mTask.getDate());
                } else {
                    mTask.setTime(mUserSelectedTime);
                }
                if (mCheckBoxDone.isChecked()) {
                    mTask.setState(State.DONE);
                } else {
                    mTask.setState(State.DOING);
                }
                mDatabase.getDemoDao().updateTask(mTask);
                mSaveDetailCallback.saveClicked();
                dismiss();
            }
        });

        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment
                        = DatePickerFragment.newInstance(mTask.getDate());
                datePickerFragment.setTargetFragment(TaskDetailFragment.this,
                        DATE_PICKER_REQUEST_CODE);
                datePickerFragment.show(getActivity().getSupportFragmentManager(), TAG);
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment
                        = TimePickerFragment.newInstance(mTask.getTime());
                timePickerFragment.setTargetFragment(TaskDetailFragment.this,
                        TIME_PICKER_REQUEST_CODE);
                timePickerFragment.show(getActivity().getSupportFragmentManager(), TAG);
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getDemoDao().deleteTask(mTask);
                mDeleteDetailCallback.deleteClicked();
                dismiss();
            }
        });

        mButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getReport());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(
                        sendIntent, getString(R.string.send_report));
                startActivity(shareIntent);
            }
        });
    }


    private String getReport() {
        String title = mTask.getTitle();
        String description = mTask.getDescription();
        String state = mTask.getState().name().toLowerCase();
        String date = DateUtils.dateFormating(mTask.getDate());
        return getString(
                R.string.task_report,
                title,
                description,
                state,
                date
        );
    }


    public interface SaveDetailCallback {
        void saveClicked();
    }


    public interface DeleteDetailCallback {
        void deleteClicked();
    }
}
