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
import com.example.managertask.adapter.TaskAdapter;
import com.example.managertask.database.TaskDatabase;
import com.example.managertask.model.Task;

import java.util.List;

public class DialogAddNewTaskFragment extends DialogFragment {
    private Button mButtonSave;
    private EditText mEditTextTitle, mEditTextDescription;
    private TaskDatabase mDatabase;

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
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        return dialog;
    }

    private void findViews(View view) {
        mButtonSave = view.findViewById(R.id.btn_save);
        mEditTextTitle = view.findViewById(R.id.edittext_title);
        mEditTextDescription = view.findViewById(R.id.edittext_description);
    }
}