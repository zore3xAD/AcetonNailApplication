package com.android.zore3x.acetonnailapplication.timetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.zore3x.acetonnailapplication.clients.Client;
import com.android.zore3x.acetonnailapplication.database.BaseHelper;
import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.database.DbSchema.VisitStatusTable;

import java.util.List;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 20.12.2017.
 */

public class VisitStatusLab {

    private static VisitStatusLab sVisitStatusLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private VisitStatusLab(Context context) {
        mContext = context;
        mDatabase = new BaseHelper(mContext).getWritableDatabase();
    }


    public static VisitStatusLab get(Context context) {
        if(sVisitStatusLab == null) {
            sVisitStatusLab = new VisitStatusLab(context);
        }
        return sVisitStatusLab;
    }

    public List<Visit> getAll() {
        List<Visit> visits = VisitLab.get(mContext).getAll();

        for (int i = 0; i < visits.size(); i++) {

            String visitUuidString = visits.get(i).getId().toString();
            Cursor cursor = getCursor(
                    VisitStatusTable.Cols.UUID_VISIT + " = ?",
                    new String[]{visitUuidString}
            );
            try {
                if (cursor.getCount() == 0) {
                    continue;
                }
                cursor.moveToFirst();
                visits.get(i).setStatus(cursor.getInt(cursor.getColumnIndex(VisitStatusTable.Cols.STATUS)));
            } finally {
                cursor.close();
            }
        }
        return visits;
    }

    public List<Visit> getAllFromClient(Client client) {
        List<Visit> visits = VisitLab.get(mContext).getAllFromClient(client.getId());

        for (int i = 0; i < visits.size(); i++) {
            String visitUuidString = visits.get(i).getId().toString();
            Cursor cursor = getCursor(
                    VisitStatusTable.Cols.UUID_VISIT + " = ?",
                    new String[]{visitUuidString}
            );
            try {
                if (cursor.getCount() == 0) {
                    continue;
                }
                cursor.moveToFirst();
                visits.get(i).setStatus(cursor.getInt(cursor.getColumnIndex(VisitStatusTable.Cols.STATUS)));
            } finally {
                cursor.close();
            }
        }
        return visits;
    }

    public void add(Visit visit) {
        ContentValues cv = getContentValues(visit);

        mDatabase.insert(VisitStatusTable.NAME, null, cv);
    }

    private ContentValues getContentValues(Visit visit) {
        ContentValues values = new ContentValues();

        values.put(VisitStatusTable.Cols.UUID_VISIT, visit.getId().toString());
        values.put(VisitStatusTable.Cols.STATUS, visit.getStatus());

        return values;
    }

    private Cursor getCursor(String whereCause, String whereArgs[]) {
        Cursor cursor = mDatabase.query(
                VisitStatusTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null
        );
        return cursor;
    }

}
