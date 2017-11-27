package com.android.zore3x.acetonnailapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.zore3x.acetonnailapplication.clients.EditClientInformationFragment;

/**
 * Created by DobrogorskiyAA on 27.11.2017.
 */

public class EditClientInformationActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return EditClientInformationFragment.newInstance();
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EditClientInformationActivity.class);

        return intent;
    }
}
