package com.example.mac_os.criminalintent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class CrimeFragment extends Fragment {

    Crime mCrime;
    EditText mTitle;
    CheckBox mSolved;
    Button mDate;
    Button mTime;
    public static String mCrimeID = "crimeID";
    public int Request_Date = 0;
    public int Request_time = 1;
    UUID crimeID;
    CrimeLab crimeLab;
    public static String dateIntent = "Date";
    public static String timeIntent = "Time";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * when we use getActivity.getIntent to get the crimeID we made this fragment depend on the activity it host
         * but we don't want this we want the fragment to be independent so we gonna use the uncommented code
         *
         * crimeID = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.mCrimeID);
         */

        crimeID = (UUID) getArguments().getSerializable(mCrimeID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeID);
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(mCrimeID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void getChangedCrimeID(UUID crime_ID) {
        Intent data = new Intent();
        data.putExtra(mCrimeID, crime_ID);
        getActivity().setResult(RESULT_OK, data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitle = v.findViewById(R.id.title_edit_text);
        mSolved = v.findViewById(R.id.solved_checked);
        mDate = v.findViewById(R.id.date_button);
        mTime = v.findViewById(R.id.time_button);
        mTitle.setText(mCrime.getTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                getChangedCrimeID(mCrime.getId());
            }
        });


        updateTime();

        updateDate();
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mCrime.getDate());
                datePickerFragment.setTargetFragment(CrimeFragment.this, Request_Date);
                datePickerFragment.show(fm, "Dialog Date");


            }
        });
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                TimePickerFragment timePickerFragment = new TimePickerFragment().newInstance(mCrime.getDate());
                timePickerFragment.setTargetFragment(CrimeFragment.this, Request_time);
                timePickerFragment.show(fragmentManager, "Dialog Time");
            }
        });


        mSolved.setChecked(mCrime.isSolved());
        mSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
                getChangedCrimeID(mCrime.getId());
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("this is request 1",requestCode+"");
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == Request_Date) {
            Date date = (Date) data.getSerializableExtra(dateIntent);
            mCrime.setDate(date);
            updateDate();
        }
        if (requestCode == Request_time) {
            Date date = (Date) data.getSerializableExtra(timeIntent);
            Log.i("this is the date selected",DateFormat.getTimeInstance().format(date));
            mCrime.setDate(date);
            updateTime();
            updateDate();
        }
    }

    private void updateTime() {
        getChangedCrimeID(mCrime.getId());
        mTime.setText(DateFormat.getTimeInstance().format(mCrime.getDate()));
    }

    private void updateDate() {
        getChangedCrimeID(mCrime.getId());
        mDate.setText(mCrime.getDate().toString());
    }
}
