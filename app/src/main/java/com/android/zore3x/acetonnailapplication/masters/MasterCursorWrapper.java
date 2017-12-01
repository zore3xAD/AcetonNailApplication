package com.android.zore3x.acetonnailapplication.masters;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.database.DbSchema.MasterTable;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 01.12.2017.
 */

public class MasterCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MasterCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Master getMaster() {

        String uuidString = getString(getColumnIndex(MasterTable.Cols.UUID));
        String name = getString(getColumnIndex(MasterTable.Cols.NAME));
        String surname = getString(getColumnIndex(MasterTable.Cols.SURNAME));
        String phone = getString(getColumnIndex(MasterTable.Cols.PHONE));

        Master master = new Master(UUID.fromString(uuidString));
        master.setName(name);
        master.setSurname(surname);
        master.setPhone(phone);

        return master;
    }
}
