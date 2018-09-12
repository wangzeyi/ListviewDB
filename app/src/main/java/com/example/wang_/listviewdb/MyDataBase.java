package com.example.wang_.listviewdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBase extends SQLiteOpenHelper{

    public static final String MYDATABASE = "mydatabase";
    public static final String MYNAME = "myname";
    public static final String MYEAT = "myeat";
    public static final String MYDRINK = "mydrink";
    public static final String MYTABLENAME = "mytablename";
    public static final String KEY_ID = "mykeyid";
    public static final int MYVERSION = 1;


    public MyDataBase(Context context) {
        super(context, MYDATABASE, null, MYVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + MYTABLENAME + "(" +
                              KEY_ID + " INTEGER PRIMARY KEY," +
                              MYNAME + " NAME," +
                              MYEAT + " EAT," +
                              MYDRINK + " DRINK" + ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MYTABLENAME);
        onCreate(db);
    }
}
