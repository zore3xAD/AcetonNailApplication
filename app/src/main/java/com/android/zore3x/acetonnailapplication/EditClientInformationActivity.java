package com.android.zore3x.acetonnailapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.zore3x.acetonnailapplication.clients.EditClientInformationFragment;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 27.11.2017.
 */

public class EditClientInformationActivity extends SingleFragmentActivity{

    private static final String EXTRA_CLIENT_ID = "com.android.zore3x.acetonnailapplication.clients.client_id";

    @Override
    protected Fragment createFragment() {
        UUID clientId = (UUID)getIntent().getSerializableExtra(EXTRA_CLIENT_ID);

        return EditClientInformationFragment.newInstance(clientId);
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EditClientInformationActivity.class);

        return intent;
    }

    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = newIntent(packageContext);
        intent.putExtra(EXTRA_CLIENT_ID, id);

        return intent;
    }
}
