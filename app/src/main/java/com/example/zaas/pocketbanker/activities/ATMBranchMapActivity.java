package com.example.zaas.pocketbanker.activities;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.fragments.ATMBranchLocatorFragment;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by shseth on 3/19/2016.
 */
public class ATMBranchMapActivity extends AppCompatActivity implements OnMapReadyCallback
{

    public static final String SINGLE_BRANCH_ATM_KEY = "singleBranchATMID";
    public static final String SINGLE_MAP_KEY = "SingleMapMode";

    private boolean mIsSingleMode;
    private int mSingleBranchAtmID;
    private List<BranchAtm> mBranchATMs;
    // TODO: implement this
    private LatLng mCurrentLocation;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mBranchATMs = ATMBranchLocatorFragment.createDummyBranchAtmList();
        mIsSingleMode = extras.getBoolean(SINGLE_MAP_KEY);
        handleExtras(extras);
        setCurrentLocation();
        setContentView(R.layout.activity_atm_branch_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void handleExtras(Bundle extras)
    {
        if (mIsSingleMode) {
            mSingleBranchAtmID = extras.getInt(SINGLE_BRANCH_ATM_KEY);
        }
    }

    private void setCurrentLocation()
    {
        // For now, doing an average of test data to fetch current location
        if (mIsSingleMode) {
            mCurrentLocation = mBranchATMs.get(mSingleBranchAtmID).getMapLocation();
        }
        else {
            mCurrentLocation = mBranchATMs.get(0).getMapLocation();
        }
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        mMap = map;
        mMap.clear();
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick()
            {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15));
                return true;
            }
        });
        mMap.getUiSettings().setZoomControlsEnabled(true);

        addMarkers();
    }

    private void addMarkers()
    {
        if (mIsSingleMode) {
            BranchAtm branchAtm = mBranchATMs.get(mSingleBranchAtmID);
            addMarker(branchAtm);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15));
        }
        else {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (BranchAtm branchAtm : mBranchATMs) {
                addMarker(branchAtm);
                builder.include(branchAtm.getMapLocation());
            }
            LatLngBounds bounds = builder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            // CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 12));
        }
    }

    private void addMarker(BranchAtm branchAtm)
    {
        if (branchAtm.getType() == BranchAtm.Type.ATM) {
            mMap.addMarker(new MarkerOptions().position(branchAtm.getMapLocation()).snippet(branchAtm.getAddress())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atm)).title(branchAtm.getName()));
        }
        else {
            mMap.addMarker(new MarkerOptions().position(branchAtm.getMapLocation()).snippet(branchAtm.getAddress())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_branch)).title(branchAtm.getName()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case android.R.id.home:
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
