package com.android.zore3x.acetonnailapplication.masters;

import android.content.Context;
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

public class MastersListSpinnerAdapter extends ArrayAdapter<Master>{

    private Context mContext;

    private List<Master> mMasterList = new ArrayList<>();

    public MastersListSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Master> objects) {
        super(context, resource, objects);
        mContext = context;
        mMasterList.addAll(objects);
    }

    public void setList(List<Master> masters) {
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
        TextView label = (TextView) LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, parent, false);
//        label.setTextColor(Color.WHITE);
        label.setText(mMasterList.get(position).getPersonal());

        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView)LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        label.setText(mMasterList.get(position).getPersonal());

        return label;
    }

}
