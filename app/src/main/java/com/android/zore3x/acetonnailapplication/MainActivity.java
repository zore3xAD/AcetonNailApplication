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
import com.android.zore3x.acetonnailapplication.masters.MasterTypeSpinnerAdapter;
import com.android.zore3x.acetonnailapplication.masters.MastersListFragment;
import com.android.zore3x.acetonnailapplication.procedure.Procedure;
import com.android.zore3x.acetonnailapplication.procedure.ProcedureLab;
import com.android.zore3x.acetonnailapplication.timetable.TimetableListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final String SPINNER_ALL_MASTERS = "All";

    private Spinner mSpinnerToolbar;
    private MasterTypeSpinnerAdapter mAdapter;

    private String mFragmentId;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

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
                        startActivity(EditTimetableActivity.newIntent(getApplication()));
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("My clients");
        mSpinnerToolbar.setVisibility(View.GONE);
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
                // отображаем фрагмент со всеми клиентами
                fillMainContent(ClientsListFragment.newInstance());
                // убираем из видимости Спиннер выбора типа мастера
                mSpinnerToolbar.setVisibility(View.GONE);
                getSupportActionBar().setTitle("My clients");
                break;
            case R.id.nav_masters:
                Log.i(TAG, "Masters tab");
                // отображаем фрагмент со всеми мастерами
                fillMainContent(MastersListFragment.newInstance());
                // делаем видимым Спиннер с выбором типа мастера
                mSpinnerToolbar.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle("");
                // обновляем содержимое спиннера
                updateSpin();
                // назначем слушателя на выбор элемента из спиннера
                mSpinnerToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Procedure procedure = mAdapter.getItem(position);

                        if(procedure.getTitle().equals(SPINNER_ALL_MASTERS)) {
                            fillMainContent(MastersListFragment.newInstance());
                        } else {
                            fillMainContent(MastersListFragment.newInstance(procedure.getId()));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case R.id.nav_timetable:
                Log.i(TAG, "Timetable tab");
                mSpinnerToolbar.setVisibility(View.GONE);
                fillMainContent(TimetableListFragment.newInstance());
                getSupportActionBar().setTitle("Timetable");
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
