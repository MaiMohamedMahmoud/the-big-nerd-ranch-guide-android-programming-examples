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

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    ViewPager mViewPager;
    UUID crimeId;
    List<Crime> mCrimes;
    Crime mcrime;
    public static final String mCrimeID = "crimeID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.getInstance(getApplicationContext()).getCrimes();
        crimeId = (UUID) getIntent().getSerializableExtra(mCrimeID);
        mcrime = CrimeLab.getInstance(getApplicationContext()).getCrime(crimeId);

        FragmentManager fragment_manger = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragment_manger) {

            @Override
            public int getCount() {
                return mCrimes.size();
            }

            @Override
            public Fragment getItem(int position) {
                return CrimeFragment.newInstance(mCrimes.get(position).getId());
            }
        });

        mViewPager.setCurrentItem(mCrimes.indexOf(mcrime));

    }

    public Intent newIntent(Context context, UUID crimeID) {
        Intent i = new Intent(context, CrimePagerActivity.class);
        i.putExtra(mCrimeID, crimeID);
        return i;
    }
}
