package com.example.mac_os.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class CrimeFragment extends Fragment {

    Crime mCrime;
    EditText mTitle;
    CheckBox mSolved;
    Button mDate;
    public static String mCrimeID = "crimeID";
    UUID crimeID;
    CrimeLab crimeLab;

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


        mDate.setText(mCrime.getDate().toString());
        mDate.setEnabled(false);
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
}
