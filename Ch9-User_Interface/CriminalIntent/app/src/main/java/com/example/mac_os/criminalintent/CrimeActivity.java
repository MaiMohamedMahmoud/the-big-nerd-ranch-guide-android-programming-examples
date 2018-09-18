package com.example.mac_os.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    public static final String mCrimeID ="crimeID";
    @Override
    protected Fragment CreateFragment() {
        return new CrimeFragment();
    }

    public Intent NewIntent(Context context, UUID crimeID) {
        Intent i = new Intent(context, CrimeActivity.class);
        i.putExtra(mCrimeID, crimeID);
        return i;
    }

}
