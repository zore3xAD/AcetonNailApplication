package com.android.zore3x.acetonnailapplication.timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.zore3x.acetonnailapplication.R;

/**
 * Created by DobrogorskiyAA on 27.11.2017.
 */

public class TimetableListFragment extends Fragment {

    public static final String ID = "TimetableListFragment";

    public static final String TAG = "TimetableListFragment";

    public static TimetableListFragment newInstance() {

        Bundle args = new Bundle();
        args.putString("id", ID);

        TimetableListFragment fragment = new TimetableListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Create timetable fragment");
        View v = inflater.inflate(R.layout.fragment_timetable_list, container, false);

        return v;
    }
}
