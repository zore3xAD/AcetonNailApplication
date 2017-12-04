package com.android.zore3x.acetonnailapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.zore3x.acetonnailapplication.database.DbSchema.ClientTable;
import com.android.zore3x.acetonnailapplication.database.DbSchema.MasterTable;
import com.android.zore3x.acetonnailapplication.database.DbSchema.MasterTypeTable;
import com.android.zore3x.acetonnailapplication.database.DbSchema.ProcedureTable;

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

        db.execSQL("create table " + ClientTable.NAME + "("
                + " _id integer primary key autoincrement, "
                + ClientTable.Cols.UUID + ", "
                + ClientTable.Cols.NAME + ", "
                + ClientTable.Cols.SURNAME + ", "
                + ClientTable.Cols.PHONE
                + ")"
        );

        db.execSQL("create table " + MasterTable.NAME + "("
                + " _id integer primary key autoincrement, "
                + MasterTable.Cols.UUID + ", "
                + MasterTable.Cols.NAME + ", "
                + MasterTable.Cols.SURNAME + ", "
                + MasterTable.Cols.PHONE
                + ")"
        );

        db.execSQL("create table " + ProcedureTable.NAME + "("
                + " _id integer primary key autoincrement, "
                + ProcedureTable.Cols.UUID + ", "
                + ProcedureTable.Cols.TITLE
                + ")"
        );

        db.execSQL("create table " + MasterTypeTable.NAME + "( "
                + " _id integer primary key autoincrement, "
                + MasterTypeTable.Cols.UUID + ", "
                + MasterTypeTable.Cols.UUID_MASTER + ", "
                + MasterTypeTable.Cols.UUID_PROCEDURE
                + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
