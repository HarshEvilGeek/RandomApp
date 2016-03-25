package com.example.zaas.pocketbanker.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.fragments.ATMBranchLocatorFragment;
import com.example.zaas.pocketbanker.fragments.AccountSummaryFragment;
import com.example.zaas.pocketbanker.fragments.AnalyticsFragment;
import com.example.zaas.pocketbanker.fragments.PocketsFragment;
import com.example.zaas.pocketbanker.fragments.RecommendationsFragment;
import com.example.zaas.pocketbanker.fragments.TransactionsFragment;
import com.example.zaas.pocketbanker.fragments.TransferFundsFragment;
import com.example.zaas.pocketbanker.interfaces.IFloatingButtonListener;

public class MainActivity extends BaseRestrictedActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    FrameLayout fragmentContainer;
    IFloatingButtonListener floatingButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floatingButtonListener != null) {
                    floatingButtonListener.onFloatingButtonPressed();
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

        goToFragment(new AccountSummaryFragment());
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

        Fragment fragment = null;
        if (id == R.id.account_summary) {
            fragment = new AccountSummaryFragment();
        } else if (id == R.id.transactions) {
            fragment = new TransactionsFragment();
        } else if (id == R.id.transfer_funds) {
            fragment = new TransferFundsFragment();
        } else if (id == R.id.atm_branch_locator) {
            fragment = new ATMBranchLocatorFragment();
        } else if (id == R.id.analytics) {
            fragment = new AnalyticsFragment();
        } else if (id == R.id.recommendations) {
            fragment = new RecommendationsFragment();
        } else if (id == R.id.pockets) {
            fragment = new PocketsFragment();
        }

        goToFragment(fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void goToFragment(Fragment fragment) {
        if (fragment != null) {
            if (fragment instanceof IFloatingButtonListener) {
                setFloatingButtonListener((IFloatingButtonListener)fragment);
            } else {
                setFloatingButtonListener(null);
            }
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    public void setFloatingButtonListener(IFloatingButtonListener listener) {
        floatingButtonListener = listener;
        if (floatingButtonListener != null) {
            Drawable floatingButtonDrawable = floatingButtonListener.getFloatingButtonDrawable(this);
            if (floatingButtonDrawable == null) {
                floatingButtonListener = null;
                fab.setVisibility(View.GONE);
            } else {
                fab.setVisibility(View.VISIBLE);
                fab.setImageDrawable(floatingButtonDrawable);
            }
        } else {
            floatingButtonListener = null;
            fab.setVisibility(View.GONE);
        }
    }
}
