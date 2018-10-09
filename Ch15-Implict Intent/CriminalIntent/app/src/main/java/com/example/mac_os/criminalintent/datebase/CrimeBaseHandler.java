package com.example.mac_os.criminalintent.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mac_os.criminalintent.datebase.crimeDbSchema.crimeTable;

public class CrimeBaseHandler extends SQLiteOpenHelper {

    private static final int Version = 1;
    private static final String DataBase_Name = "crimeBase.db";


    public CrimeBaseHandler(Context context) {
        super(context, DataBase_Name, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + crimeTable.Name + "(" + " _id integer primary key autoincrement, " +
                crimeTable.Cols.UUID + "," +
                crimeTable.Cols.Title + "," +
                crimeTable.Cols.Date + "," +
                crimeTable.Cols.Solved + ","+
                crimeTable.Cols.Suspect + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
