package com.example.zaas.pocketbanker.activities;

import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
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
public class BranchAtmMapActivity extends AppCompatActivity implements OnMapReadyCallback
{

    public static final String SINGLE_BRANCH_ATM_KEY = "singleBranchATMID";
    public static final String SINGLE_MAP_KEY = "SingleMapMode";
    public static final String CURRENT_LOCATION_KEY = "currentLocation";

    private boolean mIsSingleMode;
    private int mSingleBranchAtmID;
    private List<BranchAtm> mBranchATMs;
    // TODO: implement this
    private LatLng mCurrentLocation;
    private GoogleMap mMap;
    private boolean mCurrent = true;

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
        mBranchATMs = PocketBankerDBHelper.getInstance().getAllBranchAtms(this);
        mIsSingleMode = extras.getBoolean(SINGLE_MAP_KEY);
        handleExtras(extras);
        setContentView(R.layout.activity_atm_branch_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (savedInstanceState == null) {
            // First incarnation of this activity.
            mapFragment.setRetainInstance(true);
        }
        mapFragment.getMapAsync(this);
    }

    private void handleExtras(Bundle extras)
    {
        if (mIsSingleMode) {
            mSingleBranchAtmID = extras.getInt(SINGLE_BRANCH_ATM_KEY);
        }
        mCurrent = true;
        mCurrentLocation = (LatLng) extras.get(CURRENT_LOCATION_KEY);
        if (mCurrentLocation == null) {
            setCurrentLocation();
        }
    }

    private void setCurrentLocation()
    {
        mCurrent = false;
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
            public boolean onMyLocationButtonClick() {
                if (mMap.getMyLocation() != null) {
                    mCurrent = true;
                    mCurrentLocation = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                }
                if (!mCurrent) {
                    Toast.makeText(getApplicationContext(), "Current not set, using random", Toast.LENGTH_SHORT).show();
                }
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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(branchAtm.getMapLocation(), 15));
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
