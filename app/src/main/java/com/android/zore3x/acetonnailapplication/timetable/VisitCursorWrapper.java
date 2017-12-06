package com.android.zore3x.acetonnailapplication.timetable;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.zore3x.acetonnailapplication.clients.Client;
import com.android.zore3x.acetonnailapplication.clients.ClientLab;
import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.database.DbSchema.VisitTable;
import com.android.zore3x.acetonnailapplication.masters.Master;
import com.android.zore3x.acetonnailapplication.masters.MasterLab;
import com.android.zore3x.acetonnailapplication.procedure.Procedure;
import com.android.zore3x.acetonnailapplication.procedure.ProcedureLab;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 06.12.2017.
 */

public class VisitCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */

    private Context mContext;

    public VisitCursorWrapper(Cursor cursor, Context context) {
        super(cursor);
        mContext = context;
    }

    public Visit getVisit() {

        String visitIdString = getString(getColumnIndex(VisitTable.Cols.UUID));
        String maserIdString = getString(getColumnIndex(VisitTable.Cols.UUID_MASTER));
        String clientIdString = getString(getColumnIndex(VisitTable.Cols.UUID_CLIENT));
        String procedureIdString = getString(getColumnIndex(VisitTable.Cols.UUID_PROCEDURE));
        long dateTime = getLong(getColumnIndex(VisitTable.Cols.DATE));

        Master master = MasterLab.get(mContext).getItem(UUID.fromString(maserIdString));
        Client client = ClientLab.get(mContext).getItem(UUID.fromString(clientIdString));
        Procedure procedure = ProcedureLab.get(mContext).getItem(UUID.fromString(procedureIdString));
        Date date = new Date(dateTime);

        Visit visit = new Visit(UUID.fromString(visitIdString));
        visit.setMaster(master);
        visit.setClient(client);
        visit.setProcedure(procedure);
        visit.setDate(date);

        return visit;
    }
}
