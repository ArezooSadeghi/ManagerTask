package com.example.managertask.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.managertask.R;
import com.example.managertask.database.TaskDatabase;
import com.example.managertask.model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogAddNewTaskFragment extends DialogFragment {
    private Button mButtonSave, mButtonDate, mButtonTime;
    private EditText mEditTextTitle, mEditTextDescription;
    private TaskDatabase mDatabase;
    public static final String TAG1 = "DANTF";
    public static final String TAG2 = "DANTF";

    public DialogAddNewTaskFragment() {
    }

    public static DialogAddNewTaskFragment newInstance() {
        DialogAddNewTaskFragment fragment = new DialogAddNewTaskFragment();
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
        mEditTextTitle = view.findViewById(R.id.edittext_title);
        mEditTextDescription = view.findViewById(R.id.edittext_description);
    }

    private void setListeners() {
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task();
                task.setTitle(mEditTextTitle.getText().toString());
                task.setDescription(mEditTextDescription.getText().toString());
                mDatabase = TaskDatabase.getInstance(getActivity());
                mDatabase.taskDao().insert(task);
                dismiss();
            }
        });

        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
                datePickerFragment.show(getActivity().getSupportFragmentManager(), TAG1);
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
                timePickerFragment.show(getActivity().getSupportFragmentManager(), TAG2);
            }
        });
    }

    private void initViews() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        mButtonDate.setText(dateFormat.format(date));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        mButtonTime.setText(simpleDateFormat.format(calendar.getTime()));
    }
}