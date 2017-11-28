package com.android.zore3x.acetonnailapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.zore3x.acetonnailapplication.database.DbSchema.ClientTable;

/**
 * Created by DobrogorskiyAA on 28.11.2017.
 */

public class BaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "AcetonNailApp.db";

    public BaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + ClientTable.NAME + "( "
                + "_id primary key autoincrement, "
                + ClientTable.Cols.UUID + ", "
                + ClientTable.Cols.NAME + ", "
                + ClientTable.Cols.SURNAME + ", "
                + ClientTable.Cols.PHONE
                + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
