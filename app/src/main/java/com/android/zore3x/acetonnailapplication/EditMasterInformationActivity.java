package com.android.zore3x.acetonnailapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.zore3x.acetonnailapplication.SingleFragmentActivity;
import com.android.zore3x.acetonnailapplication.masters.EditMasterInformationFragment;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class EditMasterInformationActivity extends SingleFragmentActivity {

    public static final String EXTRA_MASTER_ID = "com.android.xore3x.acetonnailapplication.masters/master_id";

    @Override
    protected Fragment createFragment() {

        UUID masterId = (UUID)getIntent().getSerializableExtra(EXTRA_MASTER_ID);

        return EditMasterInformationFragment.newInstance(masterId);
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EditMasterInformationActivity.class);

        return intent;
    }

    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = newIntent(packageContext);
        intent.putExtra(EXTRA_MASTER_ID, id);

        return intent;
    }
}
