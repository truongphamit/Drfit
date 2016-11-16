package com.drteam.truongpq.drfit;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.drteam.truongpq.drfit.activities.MonthActivity;
import com.drteam.truongpq.drfit.activities.ProfileActivity;
import com.drteam.truongpq.drfit.activities.TodayActivity;
import com.drteam.truongpq.drfit.activities.WeekActivity;
import com.drteam.truongpq.drfit.services.StepCounterService;
import com.drteam.truongpq.drfit.utilities.MySharedPreferences;
import com.github.lzyzsd.circleprogress.ArcProgress;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArcProgress arcProgress;
    private ToggleButton btnStart;

    private StepCounterService stepCounterService;
    boolean isBound = false;
    private ServiceConnection myConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepCounterService.MyLocalBinder binder = (StepCounterService.MyLocalBinder) service;
            stepCounterService = binder.getService();
            isBound = true;
            initView();
            stepCounterService.setCallBack(arcProgress);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            stepCounterService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (MySharedPreferences.getName(this) == null) {
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        } else {
            View hView =  navigationView.getHeaderView(0);
            TextView tv_name = (TextView) hView.findViewById(R.id.tv_name);
            TextView tv_info = (TextView) hView.findViewById(R.id.tv_info);
            tv_name.setText(MySharedPreferences.getName(this));
            tv_info.setText(MySharedPreferences.getHeight(this) + "cm - " + MySharedPreferences.getWeight(this) + "kg");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(MainActivity.this, StepCounterService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(myConnection);
    }

    private void initView() {
        arcProgress = (ArcProgress) findViewById(R.id.arc_progress);
        btnStart = (ToggleButton) findViewById(R.id.btn_start);
        if (stepCounterService.getFLAG()) {
            btnStart.setChecked(true);
        } else {
            btnStart.setChecked(false);
        }
        btnStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(MainActivity.this, StepCounterService.class);
                if (isChecked) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        });
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_today:
                startActivity(new Intent(this, TodayActivity.class));
                break;
            case R.id.nav_week:
                startActivity(new Intent(this, WeekActivity.class));
                break;
            case R.id.nav_month:
                startActivity(new Intent(this, MonthActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
