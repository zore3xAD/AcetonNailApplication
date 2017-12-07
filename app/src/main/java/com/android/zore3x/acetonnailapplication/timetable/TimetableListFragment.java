package com.android.zore3x.acetonnailapplication.timetable;

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

import com.android.zore3x.acetonnailapplication.R;
import com.android.zore3x.acetonnailapplication.TimetableInformationActivity;

import java.util.List;

/**
 * Created by DobrogorskiyAA on 27.11.2017.
 */

public class TimetableListFragment extends Fragment {

    public static final String ID = "TimetableListFragment";
    public static final String TAG = "TimetableListFragment";

    private RecyclerView mRecyclerViewTimetableList;
    private TimetableAdapter mAdapter;

    public static TimetableListFragment newInstance() {

        Bundle args = new Bundle();
        args.putString("id", ID);

        TimetableListFragment fragment = new TimetableListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Create timetable fragment");
        View v = inflater.inflate(R.layout.fragment_recycler_view_list, container, false);

        mRecyclerViewTimetableList = (RecyclerView)v.findViewById(R.id.recyclerView_list);
        mRecyclerViewTimetableList.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class TimetableHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTextViewClientPersonal;
        private TextView mTextViewMasterPersonal;
        private TextView mTextViewVisitDate;
        private TextView mTextViewVisitTime;

        private Visit mVisit;

        public TimetableHolder(View itemView) {
            super(itemView);

            mTextViewClientPersonal = (TextView)itemView.findViewById(R.id.textView_list_item_timetable_client_personal);
            mTextViewMasterPersonal = (TextView)itemView.findViewById(R.id.textView_list_item_timetable_master_personal);
            mTextViewVisitDate = (TextView)itemView.findViewById(R.id.textView_list_item_timetable_visit_date);
            mTextViewVisitTime = (TextView)itemView.findViewById(R.id.textView_list_item_timetable_visit_time);

            itemView.setOnClickListener(this);
        }

        private void bindVisit(Visit visit) {
            mVisit = visit;

            mTextViewClientPersonal.setText(mVisit.getClient().getPersonal());
            mTextViewMasterPersonal.setText(mVisit.getMaster().getPersonal());
            mTextViewVisitDate.setText(mVisit.getStringDate());
            mTextViewVisitTime.setText(mVisit.getStringTime());
        }

        @Override
        public void onClick(View v) {
            startActivity(TimetableInformationActivity.newIntent(getActivity(), mVisit.getId()));
        }
    }

    private class TimetableAdapter extends RecyclerView.Adapter<TimetableHolder> {

        private List<Visit> mVisits;

        public TimetableAdapter(List<Visit> visits) {
            mVisits = visits;
        }

        @Override
        public TimetableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_visit_card, parent, false);

            return new TimetableHolder(view);
        }

        @Override
        public void onBindViewHolder(TimetableHolder holder, int position) {
            Visit visit = mVisits.get(position);
            holder.bindVisit(visit);
        }

        @Override
        public int getItemCount() {
            return mVisits.size();
        }

        public void setVisits(List<Visit> visits) {
            mVisits = visits;
        }
    }

    private void updateUI() {
        List<Visit> visits = VisitLab.get(getActivity()).getAll();
        if(mAdapter == null) {
            mAdapter = new TimetableAdapter(visits);
            mRecyclerViewTimetableList.setAdapter(mAdapter);
        } else {
            mAdapter.setVisits(visits);
            mAdapter.notifyDataSetChanged();
        }
    }
}
