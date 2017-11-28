package com.android.zore3x.acetonnailapplication.clients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zore3x.acetonnailapplication.EditClientInformationActivity;
import com.android.zore3x.acetonnailapplication.R;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 28.11.2017.
 */

public class ClientInformationFragment extends Fragment {

    private static final String ARG_CLIENT_ID = "client_id";

    private TextView mTextViewClientPersonal;
    private TextView mTextViewClientPhone;

    private Client mClient;

    public static ClientInformationFragment newInstance(UUID clientId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CLIENT_ID, clientId);

        ClientInformationFragment fragment = new ClientInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID clientId = (UUID)getArguments().getSerializable(ARG_CLIENT_ID);
        if(clientId != null) {
            mClient = ClientLab.get(getActivity()).getItem(clientId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UUID clientId = (UUID)getArguments().getSerializable(ARG_CLIENT_ID);
        if(clientId != null) {
            mClient = ClientLab.get(getActivity()).getItem(clientId);
        }
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_client_information, container, false);

        mTextViewClientPersonal = (TextView)v.findViewById(R.id.textView_client_information_personal);
        mTextViewClientPhone = (TextView)v.findViewById(R.id.textView_client_information_phone);

        updateUI();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.client_information, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_client_edit:
                Intent intent = EditClientInformationActivity.newIntent(getActivity(), mClient.getId());
                startActivity(intent);

                return true;
            case R.id.menu_item_client_delete:
                ClientLab.get(getActivity()).delete(mClient);
                Toast.makeText(getActivity(), "Client " + mClient.getPersonal() + "was deleted", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        mTextViewClientPersonal.setText(mClient.getPersonal());
        mTextViewClientPhone.setText(mClient.getPhone());
    }
}
