package com.example.mac_os.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimePickerFragment extends DialogFragment {

    public static String strTime = "Time Picker";
    public static String timeIntent = "Time";

    View v;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_time, null);
        final TimePicker timePicker = v.findViewById(R.id.dialog_time_picker);
        final Date date = (Date) getArguments().getSerializable(strTime);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        timePicker.setHour(calendar.get(Calendar.HOUR));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.time_picker_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = timePicker.getHour();
                        int minutes = timePicker.getMinute();
                        Date time_date = new GregorianCalendar(year, month, day, hour, minutes).getTime();
                        sendResult(Activity.RESULT_OK, time_date);
                    }
                })
                .create();
        return alertDialog;

    }

    private void sendResult(int resultOk, Date date) {
        if (resultOk != Activity.RESULT_OK) {
            return;
        }
        Intent i = new Intent();
        i.putExtra(timeIntent, date);
        Log.i("this is sendResult", getTargetRequestCode() + "");
        Log.i("this is sendResult", DateFormat.getTimeInstance().format(date));
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, i);

    }

    public static TimePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putSerializable(strTime, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
