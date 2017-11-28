package com.android.zore3x.acetonnailapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.zore3x.acetonnailapplication.clients.ClientInformationFragment;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 28.11.2017.
 */

public class ClientInformationActivity extends SingleFragmentActivity {
    private static final String EXTRA_CLIENT_ID = "com.android.zore3x.acetonnailapplication.clients.client_id";

    @Override
    protected Fragment createFragment() {
        UUID clientId = (UUID)getIntent().getSerializableExtra(EXTRA_CLIENT_ID);

        return ClientInformationFragment.newInstance(clientId);
    }

    public static Intent newIntent(Context packageContext, UUID clientId) {
        Intent intent = new Intent(packageContext, ClientInformationActivity.class);
        intent.putExtra(EXTRA_CLIENT_ID, clientId);

        return intent;
    }
}
