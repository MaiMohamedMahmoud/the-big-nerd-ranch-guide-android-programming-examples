package com.example.mac_os.criminalintent;

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

public class CrimeFragment extends Fragment {

    Crime mCrime;
    EditText mTitle;
    CheckBox mSolved;
    Button mDate;
    String mCrimeID ="crimeID";
    UUID crimeID;
    CrimeLab crimeLab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mCrime = new Crime();
        crimeID = (UUID)getActivity().getIntent().getSerializableExtra(CrimeActivity.mCrimeID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeID);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime,container,false);
        mTitle = v.findViewById(R.id.title_edit_text);
        mSolved = v.findViewById(R.id.solved_checked);
        mDate = v.findViewById(R.id.date_button);
        mTitle.setText(mCrime.getTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              mTitle.setText(mCrime.getTitle());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDate.setText(mCrime.getDate().toString());
        mDate.setEnabled(false);
        mSolved.setChecked(mCrime.isSolved());
        mSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }
}
