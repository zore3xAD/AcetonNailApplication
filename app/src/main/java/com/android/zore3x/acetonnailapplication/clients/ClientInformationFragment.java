package com.android.zore3x.acetonnailapplication.clients;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zore3x.acetonnailapplication.EditClientInformationActivity;
import com.android.zore3x.acetonnailapplication.R;
import com.android.zore3x.acetonnailapplication.database.DbSchema;
import com.android.zore3x.acetonnailapplication.timetable.NewVisitDialog;
import com.android.zore3x.acetonnailapplication.timetable.Visit;
import com.android.zore3x.acetonnailapplication.timetable.VisitStatusLab;

import java.util.List;
import java.util.UUID;

/**
 * Created by DobrogorskiyAA on 28.11.2017.
 */

public class ClientInformationFragment extends Fragment {

    private static final String ARG_CLIENT_ID = "client_id";

    private TextView mTextViewClientPhone;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton mFloatingActionButtonNewVisit;
    private RecyclerView mRecyclerViewClientVisitHistory;

    private Client mClient;

    private ClientVisitHistoryAdapter mAdapter;

    public static ClientInformationFragment newInstance(UUID clientId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CLIENT_ID, clientId);

        ClientInformationFragment fragment = new ClientInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // вывод из арггументов фрагмента ИД клиента для вывода на экран информации
        UUID clientId = (UUID)getArguments().getSerializable(ARG_CLIENT_ID);
        if(clientId != null) {
            mClient = ClientLab.get(getActivity()).getItem(clientId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UUID clientId = (UUID)getArguments().getSerializable(ARG_CLIENT_ID);
        if(clientId != null) {
            mClient = ClientLab.get(getActivity()).getItem(clientId);
        }
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_client_information, container, false);

        Toolbar toolbar = (Toolbar)v.findViewById(R.id.toolbar_client_information);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);
        mRecyclerViewClientVisitHistory = (RecyclerView) v.findViewById(R.id.recyclerView_client_information_visit);
        mRecyclerViewClientVisitHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTextViewClientPhone = (TextView)v.findViewById(R.id.textView_client_information_phone);
        mFloatingActionButtonNewVisit = (FloatingActionButton)v.findViewById(R.id.fab_write_client_to_visit);
        mFloatingActionButtonNewVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewVisitDialog();
            }
        });

//        updateUI();

        return v;
    }

    private void openNewVisitDialog() {
        NewVisitDialog dialog = NewVisitDialog.newInstance(mClient.getId());
        dialog.setTargetFragment(this, 1);
        dialog.show(getFragmentManager(), dialog.getClass().getName());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.client_information, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // нажатие на кнопку меню "Редактировать"
            case R.id.menu_item_client_edit:
                Intent intent = EditClientInformationActivity.newIntent(getActivity(), mClient.getId());
                startActivity(intent);

                return true;
            // нажатие на кнопку меню "Удалить"
            case R.id.menu_item_client_delete:
                ClientLab.get(getActivity()).delete(mClient);
                Toast.makeText(getActivity(), "Client " + mClient.getPersonal() + "was deleted", Toast.LENGTH_SHORT).show();
                getActivity().finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // обноаление содержимого активности
    private void updateUI() {
        mCollapsingToolbarLayout.setTitle(mClient.getPersonal());
        mTextViewClientPhone.setText(mClient.getPhone());

        VisitStatusLab visitLab = VisitStatusLab.get(getActivity());
        List<Visit> visits = visitLab.getAllFromClient(mClient);
        if(mAdapter == null) {
            mAdapter = new ClientVisitHistoryAdapter(visits);
            mRecyclerViewClientVisitHistory.setAdapter(mAdapter);
        } else {
            mAdapter.setVisits(visits);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ClientVisitHistoryHolder extends RecyclerView.ViewHolder {

        ImageView mImageViewVisitStatus;

        TextView mTextViewProcedure;
        TextView mTextViewVisitDate;
        TextView mTextViewVisitTime;

        public ClientVisitHistoryHolder(View itemView) {
            super(itemView);

            mImageViewVisitStatus = (ImageView)itemView.findViewById(R.id.imageView_item_client_information_visit_card_status);

            mTextViewProcedure = (TextView)itemView.findViewById(R.id.textView_list_item_client_information_visit_procedure);
            mTextViewVisitDate = (TextView)itemView.findViewById(R.id.textView_list_item_client_information_visit_date);
            mTextViewVisitTime = (TextView)itemView.findViewById(R.id.textView_list_item_client_information_visit_time);

        }

        private void bindHistory(Visit visit) {

            mTextViewVisitTime.setText(visit.getStringTime());
            mTextViewVisitDate.setText(visit.getStringDate());
            mTextViewProcedure.setText(visit.getProcedure().getTitle());

            switch (visit.getStatus()) {
                case DbSchema.VisitStatusTable.STATUS_OK:
                    mImageViewVisitStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_visit_status_ok));
                    break;
                case DbSchema.VisitStatusTable.STATUS_CANCEL:
                    mImageViewVisitStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_visit_status_cancel));
                    break;
                case DbSchema.VisitStatusTable.STATUS_WAIT:
                    mImageViewVisitStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_visit_status_wait));
                    break;
                default:
                    mImageViewVisitStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_visit_status_wait));
            }

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

    private class ClientVisitHistoryAdapter extends RecyclerView.Adapter<ClientVisitHistoryHolder> {

        private List<Visit> mVisits;

        public ClientVisitHistoryAdapter(List<Visit> visits) {
            mVisits = visits;
        }

        public void setVisits(List<Visit> visits) {
            mVisits = visits;
        }


        @Override
        public ClientVisitHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_client_information_visit_card, parent, false);

            return new ClientVisitHistoryHolder(view);
        }

        @Override
        public void onBindViewHolder(ClientVisitHistoryHolder holder, int position) {
            Visit visit = mVisits.get(position);
            holder.bindHistory(visit);
        }

        @Override
        public int getItemCount() {
            return mVisits.size();
        }
    }

}
