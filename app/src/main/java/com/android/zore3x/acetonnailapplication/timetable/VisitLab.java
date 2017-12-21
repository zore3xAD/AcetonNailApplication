package com.android.zore3x.acetonnailapplication.timetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.zore3x.acetonnailapplication.database.BaseHelper;
import com.android.zore3x.acetonnailapplication.database.DbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.android.zore3x.acetonnailapplication.database.DbSchema.*;

/**
 * Created by DobrogorskiyAA on 06.12.2017.
 */

public class VisitLab {

    private static VisitLab sVisitLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private VisitLab(Context context) {
        mContext = context;
        mDatabase = new BaseHelper(mContext).getWritableDatabase();
    }

    public static VisitLab get(Context context) {
        if(sVisitLab == null) {
            sVisitLab = new VisitLab(context);
        }
        return sVisitLab;
    }

    public List<Visit> getAll() {
        List<Visit> visits = new ArrayList<>();
        VisitCursorWrapper cursor = query(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                visits.add(cursor.getVisit());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return visits;
    }

    public List<Visit> getAllFromClient(UUID clientId) {
        List<Visit> visits = new ArrayList<>();
        VisitCursorWrapper cursor = query(
                VisitTable.Cols.UUID_CLIENT + " = ?",
                new String[]{clientId.toString()}
                );
        try {
            if(cursor.getCount() == 0) {
                return visits;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                visits.add(cursor.getVisit());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return visits;
    }

    public Visit getItem(UUID visitId) {
        VisitCursorWrapper cursor = query(VisitTable.Cols.UUID + " = ?",
                new String[]{visitId.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getVisit();
        } finally {
            cursor.close();
        }
    }

    public void add(Visit visit) {
        ContentValues values = getContentValues(visit);

        mDatabase.insert(VisitTable.NAME, null, values);
    }

    public void update(Visit visit) {

    }

    public void delete(Visit visit) {

    }

    private ContentValues getContentValues(Visit visit) {
        ContentValues values = new ContentValues();

        values.put(VisitTable.Cols.UUID, visit.getId().toString());
        values.put(VisitTable.Cols.UUID_CLIENT, visit.getClient().getId().toString());
        values.put(VisitTable.Cols.UUID_MASTER, visit.getMaster().getId().toString());
        values.put(VisitTable.Cols.UUID_PROCEDURE, visit.getProcedure().getId().toString());
        values.put(VisitTable.Cols.DATE, visit.getDate().getTime());

        return values;
    }

    private VisitCursorWrapper query(String whereCause, String whereArgs[]) {

        Cursor cursor = mDatabase.query(
                VisitTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null
        );
        return new VisitCursorWrapper(cursor, mContext);
    }

}
