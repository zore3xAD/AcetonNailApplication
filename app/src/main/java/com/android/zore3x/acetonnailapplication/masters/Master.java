package com.android.zore3x.acetonnailapplication.masters;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 01.12.2017.
 */

public class Master {

    private UUID mId;
    private String mName;
    private String mSurname;
    private String mPhone;

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
}
