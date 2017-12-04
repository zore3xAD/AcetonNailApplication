package com.android.zore3x.acetonnailapplication.procedure;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.database.DbSchema.ProcedureTable;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class ProcedureCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ProcedureCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Procedure getProcedure() {

        String stringProcedureId = getString(getColumnIndex(ProcedureTable.Cols.UUID));
        String title = getString(getColumnIndex(ProcedureTable.Cols.TITLE));

        Procedure procedure = new Procedure(UUID.fromString(stringProcedureId));
        procedure.setTitle(title);

        return procedure;
    }
}
