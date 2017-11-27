package com.android.zore3x.acetonnailapplication.clients;

import java.util.Date;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 27.11.2017.
 */

public class Client {

    private UUID mId;
    private String mName;
    private String mSurname;
    private Date mDate;

    public Client() {
        mId = UUID.randomUUID();
    }

    public Client(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getPersonal() {
        return mName + " " + mSurname;
    }
}
