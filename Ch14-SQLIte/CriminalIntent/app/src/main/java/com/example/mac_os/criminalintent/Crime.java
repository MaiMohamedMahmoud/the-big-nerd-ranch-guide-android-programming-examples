package com.example.mac_os.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private String mTitle;
    private Date mDate;
    private UUID mId;
    private boolean mSolved;


    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mDate = new Date();
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
