package com.android.zore3x.acetonnailapplication.masters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.zore3x.acetonnailapplication.procedure.Procedure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DobrogorskiyAA on 18.12.2017.
 */

public class MasterTypeSpinnerAdapter extends ArrayAdapter<Procedure> {

    public static final String SPINNER_ALL_MASTERS = "All";

    private Context mContext;

    private List<Procedure> mProcedureList = new ArrayList<>();

    public MasterTypeSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Procedure> objects) {
        super(context, resource, objects);
        mContext = context;
        Procedure stub = new Procedure();
        stub.setTitle(SPINNER_ALL_MASTERS);
        mProcedureList.add(stub);
        mProcedureList.addAll(objects);
    }

    public void setProcedureList(List<Procedure> procedures) {
        mProcedureList.clear();
        Procedure stub = new Procedure();
        stub.setTitle(SPINNER_ALL_MASTERS);
        mProcedureList.add(stub);
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
        TextView label = (TextView) LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, parent, false);
//        label.setTextColor(Color.WHITE);
        label.setText(mProcedureList.get(position).getTitle());

        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView)LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        label.setText(mProcedureList.get(position).getTitle());

        return label;
    }

}
