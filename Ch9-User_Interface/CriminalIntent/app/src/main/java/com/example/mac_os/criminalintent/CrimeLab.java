package com.example.mac_os.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    // @todo rename to getInstance
    public static CrimeLab get(Context context) {

        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public Crime getCrime(UUID uuid) {

//        for (Crime crime : mCrimes) {
//            if (crime.getId().equals(uuid)) {
//                return crime;
//
//            }
//        }
        /**
         * A nother way to implement this function is like the following comment
         */

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(uuid)) {
                return mCrimes.get(i);
            }
        }
        return null;

    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    // @todo add comment to illustrate singletone usage
    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Crime mCrime = new Crime();
            mCrime.setTitle("Crime # " + i);
            mCrime.setSolved(i % 2 == 0);
            mCrimes.add(mCrime);

        }

    }
}
