package com.example.managertask.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
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

import java.util.List;

public class TaskInformationDialog extends DialogFragment {
    public static final String TASK_ID = "taskId";
    private EditText mEditTextTitle, mEditTextDescription;
    private Button mButtonDate, mButtonTime, mButtonSave, mButtonDelete, mButtonEdit;
    private CheckBox mCheckBoxDone;
    private int mTaskId;
    private Task mTask;
    private TaskDatabase mDatabase;

    public TaskInformationDialog() {
    }

    public static TaskInformationDialog newInstance(int taskId) {
        TaskInformationDialog fragment = new TaskInformationDialog();
        Bundle args = new Bundle();
        args.putInt(TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId = getArguments().getInt(TASK_ID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_task_information_dialog, null);
        findViews(view);
        initViews();
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
        mCheckBoxDone = view.findViewById(R.id.state_task_information);
    }

    public void initViews() {
        mDatabase = TaskDatabase.getInstance(getActivity());
        List<Task> tasks = mDatabase.taskDao().getAllTask();
        for (Task task : tasks) {
            if (task.getId() == mTaskId) {
                mTask = task;
                mEditTextTitle.setText(mTask.getTitle());
                mEditTextDescription.setText(mTask.getDescription());
                mButtonDate.setText(mTask.getDate().toString());
                mButtonTime.setText(mTask.getTime().toString());
                if (mTask.getState().equals(State.DONE)) {
                    mCheckBoxDone.setChecked(true);
                }
            }

        }
    }
}