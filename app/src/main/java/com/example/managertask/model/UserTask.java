package com.example.managertask.model;

import java.sql.Timestamp;
import java.util.Date;

public class UserTask {
    private int mTaskId;
    private String mTitle;
    private String mDescription;
    private State mState;
    private Date mDate;
    private Timestamp mTime;
    private long mUserId;
    private String mUsername;
    private String mPassword;
}
