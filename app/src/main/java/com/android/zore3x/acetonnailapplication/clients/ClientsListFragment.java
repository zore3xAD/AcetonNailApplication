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

public class ClientsListFragment extends Fragment {

    private static final String TAG = "ClientsListFragment";

    public static ClientsListFragment newInstance() {

        Bundle args = new Bundle();

        ClientsListFragment fragment = new ClientsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "All client list fragment");

        View v = inflater.inflate(R.layout.fragment_clients_list, container, false);

        return v;
    }
}
