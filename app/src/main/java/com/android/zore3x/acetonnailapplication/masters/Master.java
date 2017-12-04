package com.android.zore3x.acetonnailapplication.masters;

import com.android.zore3x.acetonnailapplication.procedure.Procedure;

import java.util.List;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 01.12.2017.
 */

public class Master {

    private UUID mId;
    private String mName;
    private String mSurname;
    private String mPhone;
    private List<Procedure> mMasterType;

    public Master() {
        mId = UUID.randomUUID();
    }

    public Master(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
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

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPersonal() {
        return mName + " " + mSurname;
    }

    public List<Procedure> getMasterType() {
        return mMasterType;
    }

    public void setMasterType(List<Procedure> masterType) {
        mMasterType = masterType;
    }
}
