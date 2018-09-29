package com.example.mac_os.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;

public class DatePickerActivity extends  SingleFragmentActivity {

    public String strDtae = "Date";

    @Override
    protected Fragment CreateFragment() {
        Date date = (Date) getIntent().getSerializableExtra(strDtae);
        return new DatePickerFragment().newInstance(date);
    }

    public Intent NewIntent(Context context, Date date){
        Intent i = new Intent(context,DatePickerActivity.class);
        i.putExtra(strDtae,date);
        return i;

    }
}
