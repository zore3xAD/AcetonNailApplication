package com.android.zore3x.acetonnailapplication.timetable;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.zore3x.acetonnailapplication.R;
import com.android.zore3x.acetonnailapplication.clients.Client;
import com.android.zore3x.acetonnailapplication.clients.ClientLab;
import com.android.zore3x.acetonnailapplication.masters.Master;
import com.android.zore3x.acetonnailapplication.masters.MasterLab;
import com.android.zore3x.acetonnailapplication.procedure.Procedure;
import com.android.zore3x.acetonnailapplication.procedure.ProcedureLab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DobrogorskiyAA on 06.12.2017.
 */

public class EditTimetableFragment extends Fragment {

    private Spinner mSpinnerMaster;
    private Spinner mSpinnerClient;
    private Spinner mSpinnerProcedure;

    private Button mButtonConfirm;

    private MasterSpinnerAdapter mMasterSpinnerAdapter;
    private ClientSpinnerAdapter mClientSpinnerAdapter;
    private ProcedureSpinnerAdapter mProcedureSpinnerAdapter;

    public static EditTimetableFragment newInstance() {

        Bundle args = new Bundle();

        EditTimetableFragment fragment = new EditTimetableFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_visit, container, false);

        mSpinnerMaster = (Spinner)view.findViewById(R.id.spinner_edit_visit_master);
        mSpinnerClient = (Spinner)view.findViewById(R.id.spinner_edit_visit_client);
        mSpinnerProcedure = (Spinner)view.findViewById(R.id.spinner_edit_visit_procedure);

        mButtonConfirm = (Button)view.findViewById(R.id.button_edit_visit_confirm);

        updateSpin();

        return view;
    }

    private class MasterSpinnerAdapter extends ArrayAdapter<Master> {

        private List<Master> mMasterList = new ArrayList<>();

        public MasterSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Master> objects) {
            super(context, resource, objects);
            mMasterList.addAll(objects);
        }

        public void setMasterList(List<Master> masters) {
            mMasterList.clear();
            mMasterList.addAll(masters);
        }

        @Override
        public int getCount() {
            return mMasterList.size();
        }

        @Nullable
        @Override
        public Master getItem(int position) {
            return mMasterList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            TextView label = (TextView) LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_spinner_item, parent, false);
            label.setText(mMasterList.get(position).getPersonal());

            return label;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView label = (TextView)LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            label.setText(mMasterList.get(position).getPersonal());

            return label;
        }
    }
    private class ClientSpinnerAdapter extends ArrayAdapter<Client> {

        private List<Client> mClientList = new ArrayList<>();

        public ClientSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Client> objects) {
            super(context, resource, objects);
            mClientList.addAll(objects);
        }

        public void setClientList(List<Client> clients) {
            mClientList.clear();
            mClientList.addAll(clients);
        }

        @Override
        public int getCount() {
            return mClientList.size();
        }

        @Nullable
        @Override
        public Client getItem(int position) {
            return mClientList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            TextView label = (TextView) LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_spinner_item, parent, false);
            label.setText(mClientList.get(position).getPersonal());

            return label;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView label = (TextView)LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            label.setText(mClientList.get(position).getPersonal());

            return label;
        }
    }
    private class ProcedureSpinnerAdapter extends ArrayAdapter<Procedure> {

        private List<Procedure> mProcedureList = new ArrayList<>();

        public ProcedureSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Procedure> objects) {
            super(context, resource, objects);
            mProcedureList.addAll(objects);
        }

        public void setProcedureList(List<Procedure> procedures) {
            mProcedureList.clear();
            mProcedureList.addAll(procedures);
        }

        @Override
        public int getCount() {
            return mProcedureList.size();
        }

        @Nullable
        @Override
        public Procedure getItem(int position) {
            return mProcedureList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            TextView label = (TextView) LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_spinner_item, parent, false);
            label.setText(mProcedureList.get(position).getTitle());

            return label;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView label = (TextView)LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            label.setText(mProcedureList.get(position).getTitle());

            return label;
        }
    }


    public void updateSpin() {
        List<Master> masters = MasterLab.get(getActivity()).getAll();
        List<Procedure> procedures = ProcedureLab.get(getActivity()).getAll();
        List<Client> clients = ClientLab.get(getActivity()).getAll();
        if(mProcedureSpinnerAdapter == null) {
            mProcedureSpinnerAdapter = new ProcedureSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, procedures);
            mSpinnerProcedure.setAdapter(mProcedureSpinnerAdapter);
        } else {
            mProcedureSpinnerAdapter.setProcedureList(procedures);
            mProcedureSpinnerAdapter.notifyDataSetChanged();
        }
        if(mClientSpinnerAdapter == null) {
            mClientSpinnerAdapter = new ClientSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, clients);
            mSpinnerClient.setAdapter(mClientSpinnerAdapter);
        } else {
            mClientSpinnerAdapter.setClientList(clients);
            mClientSpinnerAdapter.notifyDataSetChanged();
        }
        if(mMasterSpinnerAdapter == null) {
            mMasterSpinnerAdapter = new MasterSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, masters);
            mSpinnerMaster.setAdapter(mMasterSpinnerAdapter);
        } else {
            mMasterSpinnerAdapter.setMasterList(masters);
            mMasterSpinnerAdapter.notifyDataSetChanged();
        }
    }
}
