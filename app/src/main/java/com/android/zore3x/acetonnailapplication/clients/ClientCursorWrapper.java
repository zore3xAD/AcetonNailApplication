package com.android.zore3x.acetonnailapplication.clients;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.database.DbSchema.ClientTable;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 28.11.2017.
 */

public class ClientCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ClientCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // возвращает сформированный объект Client из курсора
    public Client getClient() {

        String uuidString = getString(getColumnIndex(ClientTable.Cols.UUID));
        String name = getString(getColumnIndex(ClientTable.Cols.NAME));
        String surname = getString(getColumnIndex(ClientTable.Cols.SURNAME));
        String phone = getString(getColumnIndex(ClientTable.Cols.PHONE));

        Client client = new Client(UUID.fromString(uuidString));
        client.setName(name);
        client.setSurname(surname);
        client.setPhone(phone);

        return client;
    }

}
