package com.example.managertask.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.managertask.R;
import com.example.managertask.database.DemoDatabase;
import com.example.managertask.model.State;
import com.example.managertask.model.Task;
import com.example.managertask.utils.DateUtils;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NewTaskDialog extends DialogFragment {
    private static final String ARGS_USER_ID = "userId";
    public static final int REQUEST_CODE_IMAGE_CAPTURE = 2;
    public static final String TAG = "error";
    private Button mButtonSave, mButtonDate, mButtonTime;
    private EditText mEditTextTitle, mEditTextDescription;
    public static final String AUTHORITY = "com.example.managertask.fileProvider";
    private CheckBox mCheckBoxDone;
    private ImageButton mButtonPhoto;
    private DemoDatabase mDatabase;
    private File mPhotoFile;
    private Uri mTaskUri;
    private Task mTask;
    private UUID mUserId;
    private Callbacks mCallbacks;
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
        mDatabase = DemoDatabase.getInstance(getContext());
        mPhotoFile = mDatabase.getPhotoFile(getContext());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_dialog_add_new_task, null);

        findViews(view);
        initViews();
        setListeners();
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        return dialog;
    }

    private void findViews(View view) {
        mButtonSave = view.findViewById(R.id.btn_save);
        mButtonDate = view.findViewById(R.id.btn_date);
        mButtonTime = view.findViewById(R.id.btn_time);
        mButtonPhoto = view.findViewById(R.id.img_take_photo);
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
                if (mUserSelectedDate == null) {
                    mTask.setDate(new Date());
                } else {
                    mTask.setDate(mUserSelectedDate);
                }
                if (mUserSelectedTime == null) {
                    mTask.setTime(new Timestamp(new Date().getTime()));
                } else {
                    mTask.setTime(mUserSelectedTime);
                }
                if (mCheckBoxDone.isChecked()) {
                    mTask.setState(State.DONE);
                } else {
                    mTask.setState(State.DOING);
                }
                /*mTask.setPathPhoto(mTaskUri.toString());*/
                mDatabase.getDemoDao().insertTask(mTask);
                mCallbacks.okClicked();
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

        mButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoIntent();

            }

            private void takePhotoIntent() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    if ( mPhotoFile != null && takePictureIntent
                            .resolveActivity(getActivity().getPackageManager()) != null) {
                        mTaskUri = getUriForFile();

                        List<ResolveInfo> activities = getActivity().getPackageManager()
                                .queryIntentActivities(
                                        takePictureIntent,
                                        PackageManager.MATCH_DEFAULT_ONLY);

                        for (ResolveInfo activity:activities) {
                            getActivity().grantUriPermission(
                                    activity.activityInfo.packageName,
                                    mTaskUri,
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            );

                        }

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTaskUri);
                        startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
                    }
                } catch (ActivityNotFoundException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        });
    }

    private Uri getUriForFile() {
        return FileProvider.getUriForFile(
                getContext(),
                AUTHORITY,
                mPhotoFile
        );
    }

    private void initViews() {
        mButtonDate.setText(DateUtils.dateFormating(new Date()));
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
            Log.d("Arezoo", "" + "Come");
            mUserSelectedDate = (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_USER_SELECTED_DATE);
            mButtonDate.setText(DateUtils.dateFormating(mUserSelectedDate));
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER) {
            mUserSelectedTime = (Timestamp) data.getSerializableExtra(TimePickerDialog.EXTRA_USER_SELECTED_TIME);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mUserSelectedTime.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            mButtonTime.setText(simpleDateFormat.format(calendar.getTime()));
        }

       if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
            Log.d("Arezoo", "" + "Come");
             Uri photoUri = getUriForFile();
             /*mTask.setPathPhoto(photoUri.toString());*/
             getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    public interface Callbacks {
        void okClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NewTaskDialog.Callbacks) {
            mCallbacks = (NewTaskDialog.Callbacks) context;
        }
    }
}