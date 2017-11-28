package com.android.zore3x.acetonnailapplication.clients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.zore3x.acetonnailapplication.R;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 27.11.2017.
 */

public class EditClientInformationFragment extends Fragment {

    public static final String TAG = "EditClientFragment";

    private static final String ARG_CLIENT_ID = "client_id";

    private EditText mEditTextClientName;
    private EditText mEditTextClientSurname;
    private EditText mEditTextClientPhone;

    private Button mButtonConfirm;

    private Client mClient;
    private boolean isEditable = false;

    public static EditClientInformationFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CLIENT_ID, id);

        EditClientInformationFragment fragment = new EditClientInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID clientId = (UUID) getArguments().getSerializable(ARG_CLIENT_ID);
        if(clientId != null) {
            mClient = ClientLab.get(getActivity()).getItem(clientId);
            isEditable = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Create fragment EditClientInformation");
        View v = inflater.inflate(R.layout.fragment_edit_client_information, container, false);

        mEditTextClientName = (EditText)v.findViewById(R.id.editText_editable_client_name);
        mEditTextClientSurname = (EditText)v.findViewById(R.id.editText_editable_client_surname);
        mEditTextClientPhone = (EditText)v.findViewById(R.id.editText_editable_client_phone);

        mButtonConfirm = (Button)v.findViewById(R.id.button_editeble_confirm);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEditTextClientName.getText().toString();
                String surname = mEditTextClientSurname.getText().toString();
                String phone = mEditTextClientPhone.getText().toString();

                if(isEditable) {
                    mClient.setName(name);
                    mClient.setSurname(surname);
                    mClient.setPhone(phone);
                    ClientLab.get(getActivity()).update(mClient);
                    Toast.makeText(getActivity(), "Client edited", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else {
                    mClient = new Client();
                    mClient.setName(name);
                    mClient.setSurname(surname);
                    mClient.setPhone(phone);
                    ClientLab.get(getActivity()).add(mClient);
                    Toast.makeText(getActivity(), "Client " + mClient.getPersonal() + " was added", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }
        });

        if (isEditable) {
            mEditTextClientName.setText(mClient.getName());
            mEditTextClientSurname.setText(mClient.getSurname());
            mEditTextClientPhone.setText(mClient.getPhone());
        }

        return v;
    }
}
