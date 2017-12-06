package com.android.zore3x.acetonnailapplication.masters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.android.zore3x.acetonnailapplication.database.BaseHelper;
import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.database.DbSchema.MasterTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 01.12.2017.
 */

public class MasterLab {

    private static MasterLab sMasterLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private MasterLab(Context context) {
        mContext = context;
        mDatabase = new BaseHelper(mContext).getWritableDatabase();
    }

    public static MasterLab get(Context context) {
        if(sMasterLab == null) {
            sMasterLab = new MasterLab(context);
        }
        return sMasterLab;
    }

    public List<Master> getAll() {
        Master master;
        List<Master>  masters = new ArrayList<>();
        MasterCursorWrapper cursor = query(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                master = cursor.getMaster();
                master.setMasterType(MasterTypeLab.get(mContext).getMasterType(master.getId()));
                masters.add(master);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return masters;
    }

    public List<Master> getMastersWithType(UUID procedureId) {
        return MasterTypeLab.get(mContext).getMasters(procedureId);
    }

    public Master getItem(UUID id) {
        MasterCursorWrapper cursor = query(MasterTable.Cols.UUID + " = ?",
                new String[]{id.toString()});
        try {
            if(cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            Master master = cursor.getMaster();
            master.setMasterType(MasterTypeLab.get(mContext).getMasterType(master.getId()));
            return master;
        } finally {
            cursor.close();
        }
    }

    public void add(Master master) {
        ContentValues values = getContentValues(master);
        mDatabase.insert(
                MasterTable.NAME,
                null,
                values
        );
        MasterTypeLab.get(mContext).add(master);
    }

    public void update(Master master) {
        ContentValues values = getContentValues(master);
        MasterTypeLab.get(mContext).delete(master);
        mDatabase.update(
                MasterTable.NAME,
                values,
                MasterTable.Cols.UUID + " = ?",
                new String[]{master.getId().toString()}
        );
        MasterTypeLab.get(mContext).add(master);
    }

    public void delete(Master master) {
        mDatabase.delete(
                MasterTable.NAME,
                MasterTable.Cols.UUID + " = ?",
                new String[]{master.getId().toString()}
        );
        MasterTypeLab.get(mContext).delete(master);
    }

    private MasterCursorWrapper query(String whereCause, String whereArgs[]) {
        Cursor cursor = mDatabase.query(
                MasterTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null
        );

        return new MasterCursorWrapper(cursor);
    }

    private ContentValues getContentValues(Master master) {
        ContentValues values = new ContentValues();

        values.put(MasterTable.Cols.UUID, master.getId().toString());
        values.put(MasterTable.Cols.NAME, master.getName());
        values.put(MasterTable.Cols.SURNAME, master.getSurname());
        values.put(MasterTable.Cols.PHONE, master.getPhone());

        return values;
    }

}
