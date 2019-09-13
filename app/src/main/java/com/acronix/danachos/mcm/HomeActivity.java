package com.acronix.danachos.mcm;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.acronix.danachos.mcm.fragments.AddBudgetFragment;
import com.acronix.danachos.mcm.fragments.AddExpenseFragment;
import com.acronix.danachos.mcm.fragments.HomeFragment;
import com.acronix.danachos.mcm.fragments.ReportExpenseFragment;
import com.acronix.danachos.mcm.fragments.UserSettingFragment;
import com.acronix.danachos.mcm.utils.Contant;
import com.acronix.danachos.mcm.utils.PrefUtils;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private LayoutInflater mLayoutInflater;
    private TextView tvAmoid;
    NavigationView navigationView;

    public static SQLiteDatabase dbmcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        inisialisasi_database();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_home, navigationView, false);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null){


            //cek




        }


            //start home page
            Fragment fragment = null;
            fragment = new HomeFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();





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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.homeAction) {



            unCheckAllMenuItems(navigationView.getMenu());

            Fragment fragment = null;
            fragment = new HomeFragment();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

          // return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        item.setChecked(true);


        Fragment fragment = null;

        if (id == R.id.btnAddExpense) {

            fragment = new AddExpenseFragment();


            // Handle the camera action
        } else if (id == R.id.btnAddBudget) {

            fragment = new AddBudgetFragment();

        } else if (id == R.id.btnReportExpense) {

            fragment = new ReportExpenseFragment();

        } else if (id == R.id.btnSettingUser) {


            fragment = new UserSettingFragment();


        } else if (id == R.id.btnLogout) {

            PrefUtils prefUtils = new PrefUtils(this);

            prefUtils.logout();

            //redirect ke halaman login
            Intent k = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(k);
            finish();

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        }

       // View view=;
        hideSoftKeyboard();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void unCheckAllMenuItems(@NonNull final Menu menu) {
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            final MenuItem item = menu.getItem(i);
            if(item.hasSubMenu()) {
                // Un check sub menu items
                unCheckAllMenuItems(item.getSubMenu());
            } else {
                item.setChecked(false);
            }
        }
    }


    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    private void inisialisasi_database() {

        dbmcm=openOrCreateDatabase("Mcm_lite_db", Context.MODE_PRIVATE, null);

       // dbrpxmobile.execSQL("CREATE TABLE IF NOT EXISTS t_holiday(date_holiday VARCHAR, holiday_desc VARCHAR, tipe_event VARCHAR, time_event VARCHAR, PRIMARY KEY (date_holiday,holiday_desc,tipe_event));");

        //dbrpxmobile.execSQL("CREATE TABLE IF NOT EXISTS t_calendar_deco(id VARCHAR, the_year VARCHAR, the_month VARCHAR, message VARCHAR, calendar_image VARCHAR);");

        dbmcm.execSQL("CREATE TABLE IF NOT EXISTS t_budget(the_date VARCHAR, amount VARCHAR, username VARCHAR, shareData VARCHAR, PRIMARY KEY (the_date,shareData));");

        dbmcm.execSQL("CREATE TABLE IF NOT EXISTS t_expense_report(expense_name VARCHAR, datex VARCHAR, timex VARCHAR, amount VARCHAR, username VARCHAR, sharedData VARCHAR, store_name VARCHAR, PRIMARY KEY (expense_name,datex,timex));");


    }

}
