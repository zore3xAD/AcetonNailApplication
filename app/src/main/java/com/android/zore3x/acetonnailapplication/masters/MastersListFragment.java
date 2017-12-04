package com.android.zore3x.acetonnailapplication.masters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.zore3x.acetonnailapplication.MasterInformationActivity;
import com.android.zore3x.acetonnailapplication.R;

import java.util.List;

/**
 * Created by DobrogorskiyAA on 27.11.2017.
 */

public class MastersListFragment extends Fragment {

    public static final String ID = "MastersListFragment";

    private static final String TAG = "MastersListFragment";

    private RecyclerView mRecyclerViewMasterList;
    private MasterAdapter mAdapter;

    public static MastersListFragment newInstance() {

        Bundle args = new Bundle();
        args.putString("id", ID);

        MastersListFragment fragment = new MastersListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view_list, container, false);
        mRecyclerViewMasterList = (RecyclerView)v.findViewById(R.id.recyclerView_list);
        mRecyclerViewMasterList.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class MasterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextViewItemMasterPersonal;
        private TextView mTextViewItemMasterPhone;
        private TextView mTextViewItemMasterType;

        private Master mMaster;

        public MasterHolder(View itemView) {
            super(itemView);

            mTextViewItemMasterPersonal = (TextView)itemView.findViewById(R.id.textView_list_item_master_personal);
            mTextViewItemMasterPhone = (TextView)itemView.findViewById(R.id.textView_list_item_master_phone);
            mTextViewItemMasterType = (TextView)itemView.findViewById(R.id.textView_list_item_master_type);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = MasterInformationActivity.newIntent(getActivity(), mMaster.getId());
            startActivity(intent);
        }

        private void bindMaster(Master master) {
            mMaster = master;

            mTextViewItemMasterPersonal.setText(mMaster.getPersonal());
            mTextViewItemMasterPhone.setText(mMaster.getPhone());
        }
    }

    private class MasterAdapter extends RecyclerView.Adapter<MasterHolder> {

        private List<Master> mMasters;

        public MasterAdapter(List<Master> masters) {
            mMasters = masters;
        }

        @Override
        public MasterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_master_card, parent, false);

            return new MasterHolder(view);
        }

        @Override
        public void onBindViewHolder(MasterHolder holder, int position) {
            Master master = mMasters.get(position);
            holder.bindMaster(master);
        }

        @Override
        public int getItemCount() {
            return mMasters.size();
        }

        public void setMasters(List<Master> masters) {
            mMasters = masters;
        }
    }

    public void updateUI() {
        MasterLab masterLab = MasterLab.get(getActivity());
        List<Master> masters = masterLab.getAll();
        if(mAdapter == null) {
            mAdapter = new MasterAdapter(masters);
            mRecyclerViewMasterList.setAdapter(mAdapter);
        } else {
            mAdapter.setMasters(masters);
            mAdapter.notifyDataSetChanged();
        }
    }
}
