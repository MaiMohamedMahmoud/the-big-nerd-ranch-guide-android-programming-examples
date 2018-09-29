package com.example.mac_os.criminalintent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

public class DatePickerFragment extends DialogFragment {


    public static String strDate = "Date Picker";
    public static String dateIntent = "Date";
    Button mButton_ok;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(strDate, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        Date date = (Date) getArguments().getSerializable(strDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = inflater.inflate(R.layout.dialog_date, null);

        final DatePicker datePicker = v.findViewById(R.id.dialog_date_picker);
        mButton_ok = v.findViewById(R.id.button_ok);
        datePicker.updateDate(year, month, day);
        mButton_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                Date date = new GregorianCalendar(year, month, day).getTime();
                sendResult(Activity.RESULT_OK, date);
            }
        });

        return v;
    }



//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        Date date = (Date) getArguments().getSerializable(strDate);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
//        final DatePicker datePicker = v.findViewById(R.id.dialog_date_picker);
//        datePicker.updateDate(year, month, day);
//        return new AlertDialog.Builder(getActivity())
//                .setView(v)
//                .setTitle(R.string.date_picker_title)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        int year = datePicker.getYear();
//                        int month = datePicker.getMonth();
//                        int day = datePicker.getDayOfMonth();
//                        Date date = new GregorianCalendar(year, month, day).getTime();
//                        sendResult(Activity.RESULT_OK, date);
//                    }
//                })
//                .create();
//
//    }



    public void sendResult(int result_code, Date date) {
        if (result_code != Activity.RESULT_OK) {
            return;
        }
        Intent i = new Intent();
        i.putExtra(dateIntent, date);
       // getTargetFragment().onActivityResult(getTargetRequestCode(), result_code, i);

        getActivity().setResult(Activity.RESULT_OK,i);
        getActivity().onBackPressed();
    }
}
