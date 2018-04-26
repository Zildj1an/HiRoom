package com.cgaxtr.hiroom.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.cgaxtr.hiroom.R;
import com.cgaxtr.hiroom.SessionManager;
import com.cgaxtr.hiroom.fragment.AboutFragment;
import com.cgaxtr.hiroom.fragment.AddAdvertisementFragment;
import com.cgaxtr.hiroom.fragment.ListAdvertisementsFragment;
import com.cgaxtr.hiroom.fragment.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SAVED_FRAGMENT = "fragmentState";

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView avatar;
    private View headerView;
    private String[] titles;
    private int navIndex = 0;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            navIndex = savedInstanceState.getInt(SAVED_FRAGMENT);
        }

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        headerView = navigationView.getHeaderView(0);
        avatar = headerView.findViewById(R.id.imageView);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                 startActivity(i);
            }
        });

        titles = getResources().getStringArray(R.array.titles);
        loadFragment();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (navIndex != 0){
            navIndex = 0;
            loadFragment();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navSearchAdvertisements:
                navIndex = 0;
                break;
            case R.id.navAddAdvertisement:
                navIndex = 1;
                break;
            case R.id.navYourAdvertisements:
                navIndex = 2;
                break;
            case R.id.nav_logout:
                sessionManager.logOut();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.nav_about:
                navIndex = 3;
                break;
        }

        loadFragment();
        loadTitle();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(){
        Fragment fg = null;
        switch (navIndex){
            case 0:
                fg = new SearchFragment();
                break;
            case 1:
                fg = new AddAdvertisementFragment();
                break;
            case 2:
                fg = new ListAdvertisementsFragment();
                break;
            case 3:
                fg = new AboutFragment();
                break;
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fg);
        transaction.commit();
    }

    private void loadTitle(){
        setTitle(titles[navIndex]);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_FRAGMENT, navIndex);
    }
}
