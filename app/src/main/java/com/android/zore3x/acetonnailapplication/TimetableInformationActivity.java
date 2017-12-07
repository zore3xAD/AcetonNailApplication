package com.android.zore3x.acetonnailapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.zore3x.acetonnailapplication.masters.MasterInformationFragment;
import com.android.zore3x.acetonnailapplication.timetable.TimetableInformationFragment;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 06.12.2017.
 */

public class TimetableInformationActivity extends SingleFragmentActivity {

    private static final String EXTRA_VISIT_ID = "com.android/zore3x.acetonnailpplication.timetable.visit_id";

    @Override
    protected Fragment createFragment() {
        UUID visitId = (UUID)getIntent().getSerializableExtra(EXTRA_VISIT_ID);

        return TimetableInformationFragment.newInstance(visitId);
    }

    public static Intent newIntent(Context packageContext, UUID visitId) {
        Intent intent = new Intent(packageContext, TimetableInformationActivity.class);
        intent.putExtra(EXTRA_VISIT_ID, visitId);

        return intent;
    }


}
