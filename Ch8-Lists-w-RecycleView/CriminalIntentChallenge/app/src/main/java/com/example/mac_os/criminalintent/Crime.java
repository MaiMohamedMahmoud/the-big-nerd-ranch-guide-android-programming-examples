package com.example.mac_os.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private String mTitle;
    private Date mDate ;
    private UUID mId;
    private boolean mSolved;
    private boolean mRequirePolice;



    public Crime() {
        mDate = new Date();
        mId = UUID.randomUUID();
    }

    public boolean isRequirePolice() {
        return mRequirePolice;
    }

    public void setRequirePolice(boolean requirePolice) {
        mRequirePolice = requirePolice;
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
