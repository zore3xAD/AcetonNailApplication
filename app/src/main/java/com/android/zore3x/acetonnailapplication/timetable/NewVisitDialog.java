package com.android.zore3x.acetonnailapplication.timetable;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.zore3x.acetonnailapplication.R;
import com.android.zore3x.acetonnailapplication.clients.Client;
import com.android.zore3x.acetonnailapplication.clients.ClientLab;
import com.android.zore3x.acetonnailapplication.masters.Master;
import com.android.zore3x.acetonnailapplication.masters.MasterLab;
import com.android.zore3x.acetonnailapplication.masters.MasterTypeSpinnerAdapter;
import com.android.zore3x.acetonnailapplication.masters.MastersListSpinnerAdapter;
import com.android.zore3x.acetonnailapplication.procedure.Procedure;
import com.android.zore3x.acetonnailapplication.procedure.ProcedureLab;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 14.12.2017.
 */

public class NewVisitDialog extends DialogFragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private static final String EXTRA_CLIENT = "client";

    Button mButtonSelectTime;
    Button mButtonSelectDate;

    Spinner mSpinnerProcedureType;
    Spinner mSpinnerMasterList;

    Client mClient;
    Visit mVisit;
    Master mMaster;
    Procedure mProcedure;

    Calendar mVisitDate;

    MasterTypeSpinnerAdapter mMasterTypeAdapter;
    MastersListSpinnerAdapter mMastersListAdapter;

    boolean mSpinChanged = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        UUID clientId = (UUID) getArguments().getSerializable(EXTRA_CLIENT);

        mClient = ClientLab.get(getActivity()).getItem(clientId);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_client_visit, null);

        mButtonSelectDate = (Button) view.findViewById(R.id.button_dialog_new_client_visit_date);
        mButtonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // открытие окна выбора даты визита
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dateDialog = DatePickerFragment.newInstance(new Date());
                dateDialog.setTargetFragment(NewVisitDialog.this, REQUEST_DATE);
                dateDialog.show(fm, DIALOG_DATE);
            }
        });
        mButtonSelectTime = (Button) view.findViewById(R.id.button_dialog_new_client_visit_time);
        mButtonSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // открытие окна выбора времени визита
                FragmentManager fm = getFragmentManager();
                TimePickerFragment timeDialog = TimePickerFragment.newInstance(new Date());
                timeDialog.setTargetFragment(NewVisitDialog.this, REQUEST_TIME);
                timeDialog.show(fm, DIALOG_TIME);
            }
        });
        mSpinnerMasterList = (Spinner) view.findViewById(R.id.spinner_dialog_new_client_visit_master);
//        mSpinnerMasterList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mMaster = (Master) parent.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        mSpinnerMasterList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMaster = (Master)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMaster = (Master)parent.getItemAtPosition(0);
            }
        });
        mSpinnerProcedureType = (Spinner) view.findViewById(R.id.spinner_dialog_new_client_visit_procedure);
        mSpinnerProcedureType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProcedure = mMasterTypeAdapter.getItem(position);

                if (mProcedure.getTitle().equals(MasterTypeSpinnerAdapter.SPINNER_ALL_MASTERS)) {
                    if (mMastersListAdapter == null) {
                        mMastersListAdapter = new MastersListSpinnerAdapter(getActivity(),
                                android.R.layout.simple_spinner_item,
                                MasterLab.get(getActivity()).getAll());
                        mSpinnerMasterList.setAdapter(mMastersListAdapter);
                    } else {
                        mMastersListAdapter.setList(MasterLab.get(getActivity()).getAll());
                        mMastersListAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (mMastersListAdapter == null) {
                        mMastersListAdapter = new MastersListSpinnerAdapter(getActivity(),
                                android.R.layout.simple_spinner_item,
                                MasterLab.get(getActivity()).getMastersWithType(mProcedure.getId()));
                        mSpinnerMasterList.setAdapter(mMastersListAdapter);
                    } else {
                        mMastersListAdapter.setList(MasterLab.get(getActivity()).getMastersWithType(mProcedure.getId()));
                        mMastersListAdapter.notifyDataSetChanged();
                    }
                }
                mMaster = mMastersListAdapter.getItem(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mClient.getPersonal())
                .setView(view)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), mVisit.getStringDate() + " " + mVisit.getStringTime(), Toast.LENGTH_SHORT).show();
                        mVisit.setClient(mClient);
                        mVisit.setMaster(mMaster);
                        mVisit.setProcedure(mProcedure);
                        VisitLab.get(getActivity()).add(mVisit);
                        dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        updateSpin();

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            int year = data.getIntExtra(DatePickerFragment.EXTRA_YEAR, -1);
            int month = data.getIntExtra(DatePickerFragment.EXTRA_MONTH, -1);
            int day = data.getIntExtra(DatePickerFragment.EXTRA_DAY, -1);

            if (mVisitDate == null) {
                mVisitDate = Calendar.getInstance();
                mVisit = new Visit();
            }
            mVisitDate.set(Calendar.MONTH, month);
            mVisitDate.set(Calendar.YEAR, year);
            mVisitDate.set(Calendar.DAY_OF_MONTH, day);

            mVisit.setDate(mVisitDate.getTime());

            mButtonSelectDate.setText(mVisit.getStringDate());
        }

        if (requestCode == REQUEST_TIME) {
            int hour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR, -1);
            int minute = data.getIntExtra(TimePickerFragment.EXTRA_MINUTE, -1);

            if (mVisitDate == null) {
                mVisitDate = Calendar.getInstance();
                mVisit = new Visit();
            }
            mVisitDate.set(Calendar.HOUR_OF_DAY, hour);
            mVisitDate.set(Calendar.MINUTE, minute);

            mVisit.setDate(mVisitDate.getTime());

            mButtonSelectTime.setText(mVisit.getStringTime());

        }
    }

    public static NewVisitDialog newInstance(UUID clientId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CLIENT, clientId);
        NewVisitDialog fragment = new NewVisitDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public void updateSpin() {
        ProcedureLab procedureLab = ProcedureLab.get(getActivity());
        List<Procedure> procedures = procedureLab.getAll();
        if (mMasterTypeAdapter == null) {
            mMasterTypeAdapter = new MasterTypeSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, procedures);
            mSpinnerProcedureType.setAdapter(mMasterTypeAdapter);
        } else {
            mMasterTypeAdapter.setProcedureList(procedures);
            mMasterTypeAdapter.notifyDataSetChanged();
        }
    }
}
