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
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    ViewPager mViewPager;
    UUID crimeId;
    List<Crime> mCrimes;
    Crime mcrime;
    CrimeLab mCrimeLab;
    public static final String mCrimeID = "crimeID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mCrimeLab =  CrimeLab.getInstance(getApplicationContext());
        mCrimes = mCrimeLab.getCrimes();
        crimeId = (UUID) getIntent().getSerializableExtra(mCrimeID);
        mcrime = mCrimeLab.getCrime(crimeId);
        FragmentManager fragment_manger = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragment_manger) {

            @Override
            public int getCount() {

                Log.i("UUID CrimePageActivity 3","ay 7aga" + mCrimes.size() );
                return mCrimes.size();
            }

            @Override
            public Fragment getItem(int position) {
                Log.i("UUID CrimePageActivity 4","ay 7aga");
                Log.i("UUID CrimePageActivity 5", mCrimes.get(position).getId() +"");
                return CrimeFragment.newInstance(mCrimes.get(position).getId());
            }
        });


        mViewPager.setCurrentItem(getCurrentindex(mCrimes,crimeId));
        //mViewPager.setCurrentItem(mCrimes.indexOf(crimeId));

    }

    private int getCurrentindex(List<Crime> crimes, UUID crimeId) {
        for(int i=0; i<crimes.size() ;i++)
        {
            if(crimes.get(i).getId().equals(crimeId))
            {
                return i;
            }
        }
        return -1;
    }

    public Intent newIntent(Context context, UUID crimeID) {
        Intent i = new Intent(context, CrimePagerActivity.class);
        Log.i("UUID CrimePageActivity", crimeID +"");
        i.putExtra(mCrimeID, crimeID);
        return i;
    }
}
