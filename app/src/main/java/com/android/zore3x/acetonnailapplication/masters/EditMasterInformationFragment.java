package com.android.zore3x.acetonnailapplication.masters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.zore3x.acetonnailapplication.R;
import com.android.zore3x.acetonnailapplication.procedure.ProcedureDialog;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class EditMasterInformationFragment extends Fragment {

    public static final String TAG = "EditMasterFragment";
    private static final String ARG_MASTER_ID = "master_id";
    private static final int REQUEST_PROCEDURE = 1;

    private EditText mEditTextMasterName;
    private EditText mEditTextMasterSurname;
    private EditText mEditTextMasterPhone;

    private Button mButtonConfirm;
    private Button mButtonMasterProcedure;

    private Master mMaster;
    private boolean isEditable = false;

    public static EditMasterInformationFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_MASTER_ID, id);

        EditMasterInformationFragment fragment = new EditMasterInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID masterId = (UUID)getArguments().getSerializable(ARG_MASTER_ID);
        if(masterId != null) {
            mMaster = MasterLab.get(getActivity()).getItem(masterId);
            isEditable = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_master_information, container, false);

        mEditTextMasterName = (EditText)v.findViewById(R.id.editText_editable_master_name);
        mEditTextMasterSurname = (EditText)v.findViewById(R.id.editText_editable_master_surname);
        mEditTextMasterPhone = (EditText)v.findViewById(R.id.editText_editable_master_phone);

        mButtonConfirm = (Button)v.findViewById(R.id.button_editable_master_confirm);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextMasterName.getText().toString();
                String surname = mEditTextMasterSurname.getText().toString();
                String phone = mEditTextMasterPhone.getText().toString();

                if(isEditable) {
                    mMaster.setName(name);
                    mMaster.setSurname(surname);
                    mMaster.setPhone(phone);

                    MasterLab.get(getActivity()).update(mMaster);
                    Toast.makeText(getActivity(), "Master edited", Toast.LENGTH_SHORT).show();

                    getActivity().finish();
                } else {
                    mMaster = new Master();
                    mMaster.setName(name);
                    mMaster.setSurname(surname);
                    mMaster.setPhone(phone);
                    MasterLab.get(getActivity()).add(mMaster);
                    Toast.makeText(getActivity(), "Master " + mMaster.getPersonal() + " was added", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }
        });

        mButtonMasterProcedure = (Button)v.findViewById(R.id.button_editable_master_procedure);
        mButtonMasterProcedure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProcedureFragment();
            }
        });

        if (isEditable) {
            mEditTextMasterName.setText(mMaster.getName());
            mEditTextMasterSurname.setText(mMaster.getSurname());
            mEditTextMasterPhone.setText(mMaster.getPhone());
        }

        return v;
    }

    private void openProcedureFragment() {
        DialogFragment procedureFragment = new ProcedureDialog();
        procedureFragment.setTargetFragment(this, REQUEST_PROCEDURE);
        procedureFragment.show(getFragmentManager(), procedureFragment.getClass().getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PROCEDURE:
                    String res = data.getStringExtra(ProcedureDialog.TAG_PROCEDURE_SELECTED);
                    Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
