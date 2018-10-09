package com.example.mac_os.criminalintent;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.mac_os.criminalintent.datebase.CrimeBaseHandler;
import com.example.mac_os.criminalintent.datebase.crimeDbSchema.crimeTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    SQLiteDatabase mDatabase;
    Context mContext;

    // @todo rename to getInstance
    public static CrimeLab getInstance(Context context) {

        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValue(c);
        mDatabase.insert(crimeTable.Name, null, values);
    }

    public void updateCrime(Crime crime) {
        ContentValues values = getContentValue(crime);
        mDatabase.update(crimeTable.Name, values, crimeTable.Cols.UUID + " = ?", new String[]{crime.getId().toString()});
    }

    public Crime getCrime(UUID uuid) {
        Crime crime;
        Cursor cursor = queryCrimes(crimeTable.Cols.UUID + " = ?", new String[]{uuid.toString()});
        try {
            cursor.moveToFirst();
            String _id = cursor.getString(cursor.getColumnIndex(crimeTable.Cols.UUID));
            Log.i("UUID CrimeLab getcrime", _id);
            String _title = cursor.getString(cursor.getColumnIndex(crimeTable.Cols.Title));
            int _isSolved = cursor.getInt(cursor.getColumnIndex(crimeTable.Cols.Solved));
            long _date = cursor.getLong(cursor.getColumnIndex(crimeTable.Cols.Date));
            crime = new Crime(UUID.fromString(_id));
            crime.setTitle(_title);
            crime.setSolved(_isSolved != 0);
            crime.setDate(new Date(_date));


        } finally {
            cursor.close();
        }
        Log.i("UUID CrimeLab befor end ", crime.getId()+"");
        return crime;
    }

    private Cursor queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(crimeTable.Name, null, whereClause, whereArgs, null, null, null);
        return cursor;
    }


    private ContentValues getContentValue(Crime crime) {
        ContentValues values = new ContentValues();
        Log.i("UUID CrimeLab", crime.getId().toString());
        values.put(crimeTable.Cols.UUID, crime.getId().toString());
        values.put(crimeTable.Cols.Title, crime.getTitle());
        values.put(crimeTable.Cols.Date, crime.getDate().getTime());
        values.put(crimeTable.Cols.Solved, crime.isSolved());
        return values;
    }

    public void deleteCrime(Crime crime) {
        mDatabase.delete(crimeTable.Name, crimeTable.Cols.UUID + " = ?", new String[]{crime.getId().toString()});
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        Cursor cursor = queryCrimes(null, null);
        try {
            if (cursor.getCount() >= 0) {
                Log.i("UUID CrimeLab",cursor.getCount()+"");
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    String _id = cursor.getString(cursor.getColumnIndex(crimeTable.Cols.UUID));
                    Log.i("UUID CrimeLab string",_id+"");
                    String _title = cursor.getString(cursor.getColumnIndex(crimeTable.Cols.Title));
                    Log.i("UUID CrimeLab string",_title+"");
                    int _isSolved = cursor.getInt(cursor.getColumnIndex(crimeTable.Cols.Solved));
                    long _date = cursor.getLong(cursor.getColumnIndex(crimeTable.Cols.Date));
                    Crime crime = new Crime(UUID.fromString(_id));
                    crime.setTitle(_title);
                    crime.setSolved(_isSolved != 0);
                    crime.setDate(new Date(_date));
                    crimes.add(crime);
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    // @todo add comment to illustrate singletone usage

    /**
     * @param context here in this class the constructor is private that mean u can only make one instance of that class .
     *                u do that by calling getInstance that will call the constructor for u
     */

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHandler(mContext).getWritableDatabase();
//        for (int i = 0; i < 100; i++) {
//            Crime mCrime = new Crime();
//            mCrime.setTitle("Crime # " + i);
//            mCrime.setSolved(i % 2 == 0);
//            mCrimes.add(mCrime);
//
//        }

    }
}
