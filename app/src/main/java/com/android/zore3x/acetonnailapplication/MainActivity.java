package com.android.zore3x.acetonnailapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.android.zore3x.acetonnailapplication.clients.ClientsListFragment;
import com.android.zore3x.acetonnailapplication.masters.MastersListFragment;
import com.android.zore3x.acetonnailapplication.timetable.TimetableListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private String mFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // проверяем на каком фрагменте была нажата fab
                switch (mFragmentId) {
                    case ClientsListFragment.ID:
                        Log.i(TAG, "Add client");
                        return;
                    case MastersListFragment.ID:
                        Log.i(TAG, "Add master");
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
                break;
            case R.id.nav_masters:
                Log.i(TAG, "Masters tab");
                fillMainContent(MastersListFragment.newInstance());
                break;
            case R.id.nav_timetable:
                Log.i(TAG, "Timetable tab");
                fillMainContent(TimetableListFragment.newInstance());
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fillMainContent(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragmentContainer = fm.findFragmentById(R.id.fragment_container);

//        fragmentContainer = fragment;
        mFragmentId = fragment.getArguments().getString("id");
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
