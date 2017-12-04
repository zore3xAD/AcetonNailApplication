package com.android.zore3x.acetonnailapplication.procedure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.zore3x.acetonnailapplication.database.BaseHelper;
import com.android.zore3x.acetonnailapplication.database.DbSchema.ProcedureTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class ProcedureLab {

    private static ProcedureLab sProcedureLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private ProcedureLab(Context context) {
        mContext = context;
        mDatabase = new BaseHelper(mContext).getWritableDatabase();
    }

    public static ProcedureLab get(Context context) {
        if(sProcedureLab == null) {
            sProcedureLab = new ProcedureLab(context);
        }
        return sProcedureLab;
    }

    public List<Procedure> getAll() {
        List<Procedure> procedures = new ArrayList<>();
        ProcedureCursorWrapper cursor = query(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                procedures.add(cursor.getProcedure());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return procedures;
    }

    public Procedure getItem(UUID uuid) {
        ProcedureCursorWrapper cursor = query(ProcedureTable.Cols.UUID + " = ?",
                new String[]{uuid.toString()});
        try {
            if(cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getProcedure();
        } finally {
            cursor.close();
        }
    }

    public void add(Procedure procedure) {
        ContentValues values = getContentValues(procedure);
        mDatabase.insert(ProcedureTable.NAME, null, values);
    }

    public void update(Procedure procedure) {
        ContentValues values = getContentValues(procedure);
        mDatabase.update(
                ProcedureTable.NAME,
                values,
                ProcedureTable.Cols.UUID + " = ?",
                new String[]{procedure.getId().toString()}
        );
    }

    public void delete(Procedure procedure) {
        mDatabase.delete(
                ProcedureTable.NAME,
                ProcedureTable.Cols.TITLE + " = ?",
                new String[]{procedure.getId().toString()}
        );
    }

    private ContentValues getContentValues(Procedure procedure) {
        ContentValues values = new ContentValues();

        values.put(ProcedureTable.Cols.UUID, procedure.getId().toString());
        values.put(ProcedureTable.Cols.TITLE, procedure.getTitle());


        return values;

    }

    private ProcedureCursorWrapper query(String whereCause, String whereArgs[]) {
        Cursor cursor = mDatabase.query(
                ProcedureTable.NAME,
                null,whereCause,
                whereArgs,
                null,
                null,
                null
        );

        return new ProcedureCursorWrapper(cursor);
    }

}
