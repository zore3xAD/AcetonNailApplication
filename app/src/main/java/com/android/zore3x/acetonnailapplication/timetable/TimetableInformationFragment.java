package com.android.zore3x.acetonnailapplication.timetable;

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

import com.android.zore3x.acetonnailapplication.R;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 06.12.2017.
 */

public class TimetableInformationFragment extends Fragment {

    private static final String ARG_VISIT_ID = "visit_id";

    private TextView mTextViewClientPersonal;
    private TextView mTextViewMasterPersonal;
    private TextView mTextViewProcedure;

    private Visit mVisit;

    public static TimetableInformationFragment newInstance(UUID visitId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_VISIT_ID, visitId);

        TimetableInformationFragment fragment = new TimetableInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID visitId = (UUID)getArguments().getSerializable(ARG_VISIT_ID);
        if(visitId != null) {
            mVisit = VisitLab.get(getActivity()).getItem(visitId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UUID visitId = (UUID)getArguments().getSerializable(ARG_VISIT_ID);
        if(visitId != null) {
            mVisit = VisitLab.get(getActivity()).getItem(visitId);
        }
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visit_information, container, false);

        mTextViewClientPersonal = (TextView)view.findViewById(R.id.textView_visit_information_client);
        mTextViewMasterPersonal = (TextView)view.findViewById(R.id.textView_visit_information_master);
        mTextViewProcedure = (TextView)view.findViewById(R.id.textView_visit_information_procedure);

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.visit_information, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_visit_edit:
                return true;
            case R.id.menu_item_visit_delete:
                VisitLab.get(getActivity()).delete(mVisit);
                getActivity().finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        mTextViewClientPersonal.setText(mVisit.getClient().getPersonal());
        mTextViewMasterPersonal.setText(mVisit.getMaster().getPersonal());
        mTextViewProcedure.setText(mVisit.getProcedure().getTitle());
    }
}
