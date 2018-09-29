package com.example.mac_os.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeListActivity extends  SingleFragmentActivity {
    @Override
    protected Fragment CreateFragment() {
        return new CrimeListFragment();
    }
}
