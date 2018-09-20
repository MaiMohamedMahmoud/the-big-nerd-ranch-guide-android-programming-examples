package com.example.mac_os.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    ViewPager mViewPager;
    UUID crimeId;
    List<Crime> mCrimes;
    Crime mcrime;
    Button firstCrime;
    Button lastCrime;
    public static final String mCrimeID = "crimeID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.get(getApplicationContext()).getCrimes();
        crimeId = (UUID) getIntent().getSerializableExtra(mCrimeID);
        mcrime = CrimeLab.get(getApplicationContext()).getCrime(crimeId);

        firstCrime = (Button) findViewById(R.id.first_crime);
        lastCrime = (Button) findViewById(R.id.last_crime);
        FragmentManager fragment_manger = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragment_manger) {
            @Override
            public int getCount() {
                return mCrimes.size();
            }

            @Override
            public Fragment getItem(int position) {
                setButtonsDisable(mViewPager.getCurrentItem());
                return CrimeFragment.newInstance(mCrimes.get(position).getId());
            }
        });

        firstCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        lastCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size() - 1);
            }
        });
        mViewPager.setCurrentItem(mCrimes.indexOf(mcrime));

    }

    public void setButtonsDisable(int position) {
        Toast.makeText(getApplicationContext(),"position " + mViewPager.getCurrentItem(),Toast.LENGTH_LONG).show();
        if(position !=0 || position != (mCrimes.size() - 1)){
            firstCrime.setEnabled(true);
            lastCrime.setEnabled(true);
        }
        if (position == 0) {
            firstCrime.setEnabled(false);
        }
        if (position == mCrimes.size() - 1) {
            lastCrime.setEnabled(false);
        }
    }

    public Intent NewIntent(Context context, UUID crimeID) {
        Intent i = new Intent(context, CrimePagerActivity.class);
        i.putExtra(mCrimeID, crimeID);
        return i;
    }
}
