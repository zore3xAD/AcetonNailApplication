package com.android.zore3x.acetonnailapplication.clients;

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

public class EditClientInformationFragment extends Fragment {

    public static final String TAG = "EditClientFragment";

    public static EditClientInformationFragment newInstance() {

        Bundle args = new Bundle();

        EditClientInformationFragment fragment = new EditClientInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Create fragment EditClientInformation");
        View v = inflater.inflate(R.layout.fragment_edit_client_information, container, false);

        return v;
    }
}
