package com.android.zore3x.acetonnailapplication.masters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.zore3x.acetonnailapplication.database.BaseHelper;
import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.database.DbSchema.MasterTypeTable;
import com.android.zore3x.acetonnailapplication.procedure.Procedure;
import com.android.zore3x.acetonnailapplication.procedure.ProcedureCursorWrapper;
import com.android.zore3x.acetonnailapplication.procedure.ProcedureLab;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class MasterTypeLab {

    private static MasterTypeLab sMasterTypeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private MasterTypeLab(Context context) {
        mContext = context;
        mDatabase = new BaseHelper(mContext).getWritableDatabase();
    }

    public static MasterTypeLab get(Context context) {
        if(sMasterTypeLab == null) {
            sMasterTypeLab = new MasterTypeLab(context);
        }
        return sMasterTypeLab;
    }

    public List<Procedure> getMasterType(UUID masterId) {
        List<Procedure> masterType = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                MasterTypeTable.NAME,
                null,
                MasterTypeTable.Cols.UUID_MASTER + " = ?",
                new String[]{masterId.toString()},
                null,
                null,
                null
        );
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String stringProcedureId = cursor.getString(cursor.getColumnIndex(MasterTypeTable.Cols.UUID_PROCEDURE));
                masterType.add(ProcedureLab.get(mContext).getItem(UUID.fromString(stringProcedureId)));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return masterType;
    }

    public List<Master> getMasters(UUID procedureId) {
        List<String> masterIdList = new ArrayList<>();
        List<Master> masterList = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                MasterTypeTable.NAME,
                new String[]{MasterTypeTable.Cols.UUID_MASTER},
                MasterTypeTable.Cols.UUID_PROCEDURE + " = ?",
                new String[]{procedureId.toString()},
                null,
                null,
                null
        );
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                masterIdList.add(cursor.getString(cursor.getColumnIndex(MasterTypeTable.Cols.UUID_MASTER)));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        for(String id : masterIdList) {
            masterList.add(MasterLab.get(mContext).getItem(UUID.fromString(id)));
        }
        return masterList;
    }

    private ProcedureCursorWrapper query(String whereCause, String whereArgs[]) {
        Cursor cursor = mDatabase.query(
                MasterTypeTable.NAME,
                null,whereCause,
                whereArgs,
                null,
                null,
                null
        );

        return new ProcedureCursorWrapper(cursor);
    }

    public void add(Master master) {
        String stringMasterUuid = master.getId().toString();
        for(int i = 0; i < master.getMasterType().size(); i++) {
            ContentValues values = getContentValues(stringMasterUuid, master.getMasterType().get(i));
            mDatabase.insert(MasterTypeTable.NAME, null, values);
        }
    }

    public void delete(Master master) {
        mDatabase.delete(MasterTypeTable.NAME,
                MasterTypeTable.Cols.UUID_MASTER + " = ?",
                new String[]{master.getId().toString()});
    }

    private ContentValues getContentValues(String masterId, Procedure procedure) {
        ContentValues values = new ContentValues();
        values.put(MasterTypeTable.Cols.UUID, UUID.randomUUID().toString());
        values.put(MasterTypeTable.Cols.UUID_MASTER, masterId);
        values.put(MasterTypeTable.Cols.UUID_PROCEDURE, procedure.getId().toString());

        return values;
    }

}
