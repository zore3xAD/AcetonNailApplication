package com.android.zore3x.acetonnailapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.android.zore3x.acetonnailapplication.timetable.EditTimetableFragment;

/**
 * Created by DobrogorskiyAA on 06.12.2017.
 */

public class EditTimetableActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, EditTimetableActivity.class);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return EditTimetableFragment.newInstance();
    }
}
