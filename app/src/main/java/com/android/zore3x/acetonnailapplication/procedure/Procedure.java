package com.android.zore3x.acetonnailapplication.procedure;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class Procedure {

    private UUID mId;
    private String mTitle;

    public Procedure() {
        mId = UUID.randomUUID();
    }

    public Procedure(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
