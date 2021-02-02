package com.example.managertask.controller.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.managertask.R;

public class RemoveTasksFragment extends DialogFragment {

    private ItemRemovedAllTasksCallback mItemRemovedAllTasksCallback;


    public static RemoveTasksFragment newInstance() {
        RemoveTasksFragment fragment = new RemoveTasksFragment();
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
        return new AlertDialog.Builder(getContext())
                .setMessage(R.string.remove_all_tasks_question)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mItemRemovedAllTasksCallback.itemRemovedClicked();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ItemRemovedAllTasksCallback) {
            mItemRemovedAllTasksCallback = (ItemRemovedAllTasksCallback) context;
        }
    }


    public interface ItemRemovedAllTasksCallback {
        void itemRemovedClicked();
    }
}