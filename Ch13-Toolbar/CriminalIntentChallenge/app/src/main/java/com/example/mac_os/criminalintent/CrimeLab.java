package com.example.mac_os.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    // @todo rename to getInstance
    public static CrimeLab getInstance(Context context) {

        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public Crime getCrime(UUID uuid) {

//        for (Crime crime : mCrimes) {
//            if (crime.getId().equals(uuid)) {
//                return crim e;
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

    public void deleteCrime(Crime crime){
        mCrimes.remove(crime);
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    // @todo add comment to illustrate singletone usage

    /**
     * @param context here in this class the constructor is private that mean u can only make one instance of that class .
     *                u do that by calling getInstance that will call the constructor for u
     */

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Crime mCrime = new Crime();
//            mCrime.setTitle("Crime # " + i);
//            mCrime.setSolved(i % 2 == 0);
//            mCrimes.add(mCrime);
//
//        }

    }
}
