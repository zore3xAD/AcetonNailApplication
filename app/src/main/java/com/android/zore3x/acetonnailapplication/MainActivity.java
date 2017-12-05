package com.android.zore3x.acetonnailapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.zore3x.acetonnailapplication.clients.ClientsListFragment;
import com.android.zore3x.acetonnailapplication.database.BaseHelper;
import com.android.zore3x.acetonnailapplication.masters.MastersListFragment;
import com.android.zore3x.acetonnailapplication.procedure.Procedure;
import com.android.zore3x.acetonnailapplication.procedure.ProcedureLab;
import com.android.zore3x.acetonnailapplication.timetable.TimetableListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private Spinner mSpinnerToolbar;
    private MasterTypeSpinnerAdapter mAdapter;

    private String mFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSpinnerToolbar = (Spinner)findViewById(R.id.toolbar_spinner);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // проверяем на каком фрагменте была нажата fab
                switch (mFragmentId) {
                    case ClientsListFragment.ID:
                        Log.i(TAG, "Add client");
                        startActivity(EditClientInformationActivity.newIntent(getApplication()));
                        return;
                    case MastersListFragment.ID:
                        Log.i(TAG, "Add master");
                        startActivity(EditMasterInformationActivity.newIntent(getApplication()));
                        return;
                    case TimetableListFragment.ID:
                        Log.i(TAG, "Add new record");
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("My clients");
//        mSpinnerToolbar.setVisibility(View.INVISIBLE);
        fillMainContent(ClientsListFragment.newInstance());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_clients:
                Log.i(TAG, "Clients tab");
                fillMainContent(ClientsListFragment.newInstance());
                getSupportActionBar().setTitle("My clients");
                mSpinnerToolbar.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_masters:
                Log.i(TAG, "Masters tab");
                fillMainContent(MastersListFragment.newInstance());
                getSupportActionBar().setTitle("");
                mSpinnerToolbar.setVisibility(View.VISIBLE);
                updateSpin();
                mSpinnerToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Procedure procedure = mAdapter.getItem(position);
                        fillMainContent(MastersListFragment.newInstance(procedure.getId()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case R.id.nav_timetable:
                Log.i(TAG, "Timetable tab");
                fillMainContent(TimetableListFragment.newInstance());
                getSupportActionBar().setTitle("Timetable");
                mSpinnerToolbar.setVisibility(View.INVISIBLE);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fillMainContent(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();

        mFragmentId = fragment.getArguments().getString("id");
        fm.beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .commit();
    }

    private class MasterTypeSpinnerAdapter extends ArrayAdapter<Procedure> {

        private List<Procedure> mProcedureList;

        public MasterTypeSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Procedure> objects) {
            super(context, resource, objects);
            mProcedureList = objects;
        }

        public void setProcedureList(List<Procedure> procedures) {
            mProcedureList = procedures;
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
            TextView label = (TextView)getLayoutInflater().inflate(android.R.layout.simple_spinner_item, parent, false);
            label.setTextColor(Color.WHITE);
            label.setText(mProcedureList.get(position).getTitle());

            return label;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView label = (TextView)getLayoutInflater().inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            label.setText(mProcedureList.get(position).getTitle());

            return label;
        }
    }

    public void updateSpin() {
        ProcedureLab procedureLab = ProcedureLab.get(getApplication());
        List<Procedure> procedures = procedureLab.getAll();
        if(mAdapter == null) {
            mAdapter = new MasterTypeSpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, procedures);
            mSpinnerToolbar.setAdapter(mAdapter);
        } else {
            mAdapter.setProcedureList(procedures);
            mAdapter.notifyDataSetChanged();
        }
    }

}
