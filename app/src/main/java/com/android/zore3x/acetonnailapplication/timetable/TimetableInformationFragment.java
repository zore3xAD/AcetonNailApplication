package com.android.zore3x.acetonnailapplication.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zore3x.acetonnailapplication.R;
import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.github.clans.fab.FloatingActionButton;

import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 06.12.2017.
 */

public class TimetableInformationFragment extends Fragment {

    private static final String ARG_VISIT_ID = "visit_id";

    private TextView mTextViewMasterPersonal;
    private TextView mTextViewProcedure;
    private TextView mTextViewClientPhone;
    private TextView mTextViewVisitDate;
    private TextView mTextViewVisitTime;

    private FloatingActionButton mFabOk;
    private FloatingActionButton mFabCancel;
    private FloatingActionButton mFabDelay;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

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

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar_visit_information);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbar_visit_information);

        mTextViewMasterPersonal = (TextView)view.findViewById(R.id.textView_visit_information_master);
        mTextViewProcedure = (TextView)view.findViewById(R.id.textView_visit_information_procedure);
        mTextViewClientPhone = (TextView)view.findViewById(R.id.textView_visit_information_client_phone);
        mTextViewVisitDate = (TextView)view.findViewById(R.id.textView_visit_information_date);
        mTextViewVisitTime = (TextView)view.findViewById(R.id.textView_visit_information_time);

        mFabCancel = (FloatingActionButton)view.findViewById(R.id.fab_visit_information_cancel);
        mFabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVisit.setStatus(DbSchema.VisitStatusTable.STATUS_CANCEL);
                VisitStatusLab.get(getActivity()).update(mVisit);
                Toast.makeText(getActivity(), "Client canceled", Toast.LENGTH_SHORT).show();
            }
        });
        mFabOk = (FloatingActionButton)view.findViewById(R.id.fab_visit_information_ok);
        mFabOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVisit.setStatus(DbSchema.VisitStatusTable.STATUS_OK);
                VisitStatusLab.get(getActivity()).update(mVisit);
                Toast.makeText(getActivity(), "Complete", Toast.LENGTH_SHORT).show();
            }
        });

        mFabDelay = (FloatingActionButton)view.findViewById(R.id.fab_visit_information_delay);
        mFabDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDelayDialog();
            }
        });
        updateUI();

        return view;
    }

    private void openDelayDialog() {
        NewVisitDialog dialog = NewVisitDialog.newInstance(mVisit);
        dialog.setTargetFragment(this, 1);
        dialog.show(getFragmentManager(), dialog.getClass().getName());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    updateUI();
                    break;
            }
        }
    }

    private void updateUI() {

        mVisit = VisitLab.get(getActivity()).getItem(mVisit.getId());

        mCollapsingToolbarLayout.setTitle(mVisit.getClient().getPersonal());
        mTextViewMasterPersonal.setText(mVisit.getMaster().getPersonal());
        mTextViewProcedure.setText(mVisit.getProcedure().getTitle());
        mTextViewClientPhone.setText(mVisit.getClient().getPhone());
        mTextViewVisitDate.setText(mVisit.getStringDate());
        mTextViewVisitTime.setText(mVisit.getStringTime());
    }
}
