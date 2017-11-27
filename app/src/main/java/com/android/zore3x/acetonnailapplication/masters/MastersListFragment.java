package com.android.zore3x.acetonnailapplication.masters;

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

public class MastersListFragment extends Fragment {

    public static final String ID = "MastersListFragment";

    private static final String TAG = "MastersListFragment";

    public static MastersListFragment newInstance() {

        Bundle args = new Bundle();
        args.putString("id", ID);

        MastersListFragment fragment = new MastersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "master list fragment");

        View v = inflater.inflate(R.layout.fragment_masters_list, container, false);

        return v;
    }
}
