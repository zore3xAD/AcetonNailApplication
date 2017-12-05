package com.android.zore3x.acetonnailapplication.masters;

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

import com.android.zore3x.acetonnailapplication.EditMasterInformationActivity;
import com.android.zore3x.acetonnailapplication.R;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 04.12.2017.
 */

public class MasterInformationFragment extends Fragment {

    private static final String ARG_MASTER_ID = "master_id";

    private TextView mTextViewMasterPersonal;
    private TextView mTextViewMasterPhone;
    private TextView mTextViewMasterType;

    private Master mMaster;

    public static MasterInformationFragment newInstance(UUID masterId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_MASTER_ID, masterId);

        MasterInformationFragment fragment = new MasterInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID masterId = (UUID) getArguments().getSerializable(ARG_MASTER_ID);
        if (masterId != null) {
            mMaster = MasterLab.get(getActivity()).getItem(masterId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UUID masterId = (UUID) getArguments().getSerializable(ARG_MASTER_ID);
        if (masterId != null) {
            mMaster = MasterLab.get(getActivity()).getItem(masterId);
        }
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_master_information, container, false);

        mTextViewMasterPersonal = (TextView) v.findViewById(R.id.textView_master_information_personal);
        mTextViewMasterPhone = (TextView) v.findViewById(R.id.textView_master_information_phone);
        mTextViewMasterType = (TextView)v.findViewById(R.id.textView_master_information_type);

        updateUI();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.master_information, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_master_edit:
                Intent intent = EditMasterInformationActivity.newIntent(getActivity(), mMaster.getId());
                startActivity(intent);

                return true;
            case R.id.menu_item_master_delete:
                MasterLab.get(getActivity()).delete(mMaster);
                Toast.makeText(getActivity(), "master " + mMaster.getPersonal() + " was deleted", Toast.LENGTH_SHORT).show();
                getActivity().finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        mTextViewMasterPersonal.setText(mMaster.getPersonal());
        mTextViewMasterPhone.setText(mMaster.getPhone());
        mTextViewMasterType.setText(mMaster.getStringMasterType());
    }
}
