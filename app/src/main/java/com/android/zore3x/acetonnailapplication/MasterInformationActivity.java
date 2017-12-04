package com.android.zore3x.acetonnailapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.zore3x.acetonnailapplication.masters.MasterInformationFragment;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class MasterInformationActivity extends SingleFragmentActivity {

    private static final String EXTRA_MASTER_ID = "com.android.zore3x.acetonnailapplication.masters.master_id";

    @Override
    protected Fragment createFragment() {
        UUID masterId = (UUID)getIntent().getSerializableExtra(EXTRA_MASTER_ID);

        return MasterInformationFragment.newInstance(masterId);
    }

    public static Intent newIntent(Context packageContext, UUID masterId) {
        Intent intent = new Intent(packageContext, MasterInformationActivity.class);
        intent.putExtra(EXTRA_MASTER_ID, masterId);

        return intent;
    }
}
