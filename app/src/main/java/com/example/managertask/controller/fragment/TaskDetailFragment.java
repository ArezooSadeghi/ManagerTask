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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDetailFragment extends DialogFragment {

    public static final String TASK_ID = "taskId";
    public static final String TAG = "TAG";
    public static final String TAG_2 = "Tag2";
    public static final int DATE_PICKER_REQUEST_CODE = 0;
    public static final int TIME_PICKER_REQUEST_CODE = 1;
    public static final String ARGS_POSITION = "position";
    private EditText mEditTextTitle, mEditTextDescription;
    private Button mButtonDate, mButtonTime, mButtonSave, mButtonDelete, mButtonEdit;
    private ExtendedFloatingActionButton mButtonShare;
    private CheckBox mCheckBoxDone;
    private UUID mTaskId;
    private Task mTask;
    private DemoDatabase mDatabase;
    private Date mUserSelectedDate;
    private Timestamp mUserSelectedTime;
    private TaskInformationCallbacks mCallbacks;
    private TaskInformationCallbacks1 mCallbacks1;

    public TaskDetailFragment() {

    }

    public static TaskDetailFragment newInstance(UUID taskId) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId = (UUID) getArguments().getSerializable(TASK_ID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_detail_task, null);

        findViews(view);
        initViews();
        setListeners();
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        return dialog;
    }


    private void findViews(View view) {
        mEditTextTitle = view.findViewById(R.id.title_task_information);
        mEditTextDescription = view.findViewById(R.id.description_task_information);
        mButtonDate = view.findViewById(R.id.date_task_information);
        mButtonTime = view.findViewById(R.id.time_task_information);
        mButtonSave = view.findViewById(R.id.save_task_information);
        mButtonDelete = view.findViewById(R.id.delete_task_information);
        mButtonEdit = view.findViewById(R.id.edit_task_information);
        mButtonShare = view.findViewById(R.id.fab_share);
        mCheckBoxDone = view.findViewById(R.id.state_task_information);
    }


    public void initViews() {
        mDatabase = DemoDatabase.getInstance(getActivity());
        List<Task> tasks = mDatabase.getDemoDao().getAllTasks();
        for (Task task : tasks) {
            if (mTaskId.equals(task.getTaskId())) {
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
                mButtonDate.setText(DateUtils.dateFormating(mTask.getDate()));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(mTask.getTime().getTime());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                mButtonTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }
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
                mCallbacks.saveClicked();
                dismiss();
            }
        });

        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(mTask.getDate());
                datePickerDialog.setTargetFragment(TaskDetailFragment.this,
                        DATE_PICKER_REQUEST_CODE);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), TAG);
            }
        });
        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(mTask.getTime());
                timePickerDialog.setTargetFragment(TaskDetailFragment.this,
                        TIME_PICKER_REQUEST_CODE);
                timePickerDialog.show(getActivity().getSupportFragmentManager(), TAG_2);
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.getDemoDao().deleteTask(mTask);
                mCallbacks1.deleteClicked();
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

    public interface TaskInformationCallbacks {
        void saveClicked();
    }

    public interface TaskInformationCallbacks1 {
        void deleteClicked();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TaskInformationCallbacks) {
            mCallbacks = (TaskInformationCallbacks) context;
        }
        if (context instanceof TaskInformationCallbacks1) {
            mCallbacks1 = (TaskInformationCallbacks1) context;
        }
    }
}
