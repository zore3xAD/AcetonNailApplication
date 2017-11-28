package com.android.zore3x.acetonnailapplication.clients;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.zore3x.acetonnailapplication.database.BaseHelper;
import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.database.DbSchema.ClientTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 28.11.2017.
 */

public class ClientLab {

    private static ClientLab sClientLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private ClientLab(Context context) {
        mContext = context;
        mDatabase = new BaseHelper(mContext).getWritableDatabase();
    }

    public static ClientLab get(Context context) {
        if(sClientLab == null) {
            sClientLab = new ClientLab(context);
        }
        return sClientLab;
    }

    // получение влех записей из базы данных
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        ClientCursorWrapper cursor = query(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                clients.add(cursor.getClient());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return clients;
    }

    // получение записи с определеным uuid
    public Client getItem(UUID uuid) {
        ClientCursorWrapper cursor = query(ClientTable.Cols.UUID + " = ?",
                new String[]{uuid.toString()});
            try {
                if (cursor.getCount() == 0) {
                    return null;
                }
                cursor.moveToFirst();
                return cursor.getClient();
            } finally {
                cursor.close();
            }
    }

    // добавление записи в базу данных
    public void add(Client client) {
        ContentValues values = getContentValues(client);
        mDatabase.insert(ClientTable.NAME, null, values);
    }

    // обновление записи в базе данных
    public void update(Client client) {
        ContentValues values = getContentValues(client);

        mDatabase.update(ClientTable.NAME,
                values,
                ClientTable.Cols.UUID + " = ?",
                new String[]{client.getId().toString()}
        );
    }

    // удаление записи из базы данных
    public void delete(Client client) {

        mDatabase.delete(ClientTable.NAME,
                ClientTable.Cols.UUID + " = ?",
                new String[]{client.getId().toString()}
        );
    }

    private ContentValues getContentValues(Client client) {
        ContentValues values = new ContentValues();

        values.put(ClientTable.Cols.UUID, client.getId().toString());
        values.put(ClientTable.Cols.NAME, client.getName());
        values.put(ClientTable.Cols.SURNAME, client.getSurname());
        values.put(ClientTable.Cols.PHONE, client.getPhone());

        return values;

    }

    private ClientCursorWrapper query(String whereCause, String whereArgs[]) {
        Cursor cursor = mDatabase.query(
                ClientTable.NAME,
                null,whereCause,
                whereArgs,
                null,
                null,
                null
        );

        return new ClientCursorWrapper(cursor);
    }
}
