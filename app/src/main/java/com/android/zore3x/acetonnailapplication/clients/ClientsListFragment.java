package com.android.zore3x.acetonnailapplication.clients;

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

import com.android.zore3x.acetonnailapplication.ClientInformationActivity;
import com.android.zore3x.acetonnailapplication.R;

import java.util.List;

/**
 * Created by DobrogorskiyAA on 27.11.2017.
 */

public class ClientsListFragment extends Fragment {

    public static final String ID = "ClientListFragment";

    private static final String TAG = "ClientsListFragment";

    private RecyclerView mRecyclerViewClientList;

    private ClientAdapter mAdapter;

    public static ClientsListFragment newInstance() {

        Bundle args = new Bundle();
        args.putString("id", ID);

        ClientsListFragment fragment = new ClientsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "All client list fragment");

        View v = inflater.inflate(R.layout.fragment_recycler_view_list, container, false);
        mRecyclerViewClientList = (RecyclerView)v.findViewById(R.id.recyclerView_list);
        mRecyclerViewClientList.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class ClientHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTextViewItemClientPersonal;
        private TextView mTextViewItemClientPhone;

        private Client mClient;

        public ClientHolder(View itemView) {
            super(itemView);

            mTextViewItemClientPersonal = (TextView)itemView.findViewById(R.id.textView_list_item_client_personal);
            mTextViewItemClientPhone = (TextView)itemView.findViewById(R.id.textView_list_item_client_phone);

            itemView.setOnClickListener(this);
        }

        private void bindClient(Client client) {
            mClient = client;

            mTextViewItemClientPersonal.setText(mClient.getPersonal());
            mTextViewItemClientPhone.setText(mClient.getPhone());
        }

        @Override
        public void onClick(View v) {
            Intent intent = ClientInformationActivity.newIntent(getActivity(), mClient.getId());
            startActivity(intent);
        }
    }

    private class ClientAdapter extends RecyclerView.Adapter<ClientHolder> {

        private List<Client> mClients;

        public ClientAdapter(List<Client> clients){
            mClients = clients;
        }

        @Override
        public ClientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_client_card, parent, false);

            return new ClientHolder(view);
        }

        @Override
        public void onBindViewHolder(ClientHolder holder, int position) {
            Client client = mClients.get(position);
            holder.bindClient(client);
        }

        @Override
        public int getItemCount() {
            return mClients.size();
        }

        public void setClients(List<Client> clients) {
            mClients = clients;
        }
    }

    public void updateUI() {
        ClientLab clientLab = ClientLab.get(getActivity());
        List<Client> clients = clientLab.getAll();
        if(mAdapter == null) {
            mAdapter = new ClientAdapter(clients);
            mRecyclerViewClientList.setAdapter(mAdapter);
        } else {
            mAdapter.setClients(clients);
            mAdapter.notifyDataSetChanged();
        }
    }

}
