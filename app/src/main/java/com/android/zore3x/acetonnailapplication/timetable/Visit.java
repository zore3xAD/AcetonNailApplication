package com.android.zore3x.acetonnailapplication.timetable;

import android.text.format.DateFormat;

import com.android.zore3x.acetonnailapplication.clients.Client;
import com.android.zore3x.acetonnailapplication.masters.Master;
import com.android.zore3x.acetonnailapplication.procedure.Procedure;

import java.util.Date;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 06.12.2017.
 */

public class Visit {

    private UUID mId;
    private Master mMaster;
    private Client mClient;
    private Procedure mProcedure;
    private Date mDate;

    public Visit() {
        mId = UUID.randomUUID();
    }

    public Visit(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public Master getMaster() {
        return mMaster;
    }

    public void setMaster(Master master) {
        mMaster = master;
    }

    public Client getClient() {
        return mClient;
    }

    public void setClient(Client client) {
        mClient = client;
    }

    public Procedure getProcedure() {
        return mProcedure;
    }

    public void setProcedure(Procedure procedure) {
        mProcedure = procedure;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getStringTime() {
        return (String) DateFormat.format("HH:mm", getDate());
    }

    public String getStringDate() {
        return (String) DateFormat.format("dd.MM.yy", getDate());
    }
}
