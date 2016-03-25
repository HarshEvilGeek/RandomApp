package com.example.zaas.pocketbanker.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.BranchAtmDetailActivity;
import com.example.zaas.pocketbanker.activities.BranchAtmMapActivity;
import com.example.zaas.pocketbanker.adapters.BranchAtmAdapter;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.interfaces.IFloatingButtonListener;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.example.zaas.pocketbanker.services.FetchAddressIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Fragment to show branch/ATM locations Created by akhil on 3/17/16.
 */
public class ATMBranchLocatorFragment extends Fragment implements BranchAtmAdapter.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, IFloatingButtonListener
{
    SwipeRefreshLayout mSwipeContainer;
    RecyclerView mRecyclerView;
    BranchAtmAdapter mAdapter;
    private List<BranchAtm> mAtmList = new ArrayList<>();
    private TextView mAtm;
    private List<BranchAtm> mBranchList = new ArrayList<>();
    private TextView mBranch;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private TextView mCurrentLocationHeader;

    private ProgressDialog mProgressDialog;
    private AlertDialog mErrorDialog;
    private SwitchCompat mBranchAtmToggle;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_atm_branch_locator, container, false);

        getActivity().setTitle(R.string.action_locator);
        mCurrentLocationHeader = (TextView) rootView.findViewById(R.id.current_location);
        mBranchAtmToggle = (SwitchCompat) rootView.findViewById(R.id.branch_atm_toggle);
        mAtm = (TextView) rootView.findViewById(R.id.atm);
        mBranch = (TextView) rootView.findViewById(R.id.branch);
        mBranch.setTextColor(getResources().getColor(R.color.colorPrimary));
        setClickListeners();
        setupSwipeContainer(rootView);
        setupRecyclerView(rootView);

        return rootView;
    }

    private void setClickListeners() {
        mBranchAtmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleList(isChecked ? BranchAtm.Type.ATM : BranchAtm.Type.BRANCH);
            }
        });
        mAtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBranchAtmToggle.setChecked(true);
                toggleList(BranchAtm.Type.ATM);
            }
        });
        mBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBranchAtmToggle.setChecked(false);
                toggleList(BranchAtm.Type.BRANCH);
            }
        });
    }

    private void toggleList(BranchAtm.Type type) {
        if (type == BranchAtm.Type.ATM) {
            mAdapter.updateList(mAtmList);
            mAtm.setTextColor(getResources().getColor(R.color.colorPrimary));
            mBranch.setTextColor(Color.GRAY);
        } else {
            mAdapter.updateList(mBranchList);
            mAtm.setTextColor(Color.GRAY);
            mBranch.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        syncBranchAtms();
    }

    @Override
    public void onStop()
    {
        stopLocationProgressDialog();
        dismissErrorDialog();
        super.onStop();
    }

    private void setupSwipeContainer(View rootView)
    {
        mSwipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // get updated location every time user swipes
                mCurrentLocation = null;
                syncBranchAtms();
            }
        });
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void setupRecyclerView(View rootView)
    {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        List<BranchAtm> listAll = PocketBankerDBHelper.getInstance().getAllBranchAtms();
        splitList(listAll);
        mAdapter = new BranchAtmAdapter(getActivity(), mBranchList);
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
    }

    private void splitList(List<BranchAtm> listAll) {
        for (BranchAtm branchAtm : listAll) {
           if (branchAtm.getType() == BranchAtm.Type.ATM) {
               mAtmList.add(branchAtm);
           } else {
               mBranchList.add(branchAtm);
           }
        }
    }

    private void syncBranchAtms()
    {
        if (mCurrentLocation == null) {
            // Don't have current location. First determine that.
            mSwipeContainer.setRefreshing(false);
            setupCurrentLocation();
        }
        else {
            // Current location available. Do network call
            if (!mSwipeContainer.isRefreshing()) {
                mSwipeContainer.setRefreshing(true);
            }
            mSwipeContainer.postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    mSwipeContainer.setRefreshing(false);
                }
            }, 4000);
        }
    }

    @Override
    public void onClick(BranchAtm branchAtm)
    {
        Intent detailIntent = new Intent(getActivity(), BranchAtmDetailActivity.class);
        detailIntent.putExtra(BranchAtmMapActivity.SINGLE_BRANCH_ATM_KEY, branchAtm.getId());
        detailIntent.putExtra(BranchAtmMapActivity.CURRENT_LOCATION_KEY, mCurrentLocation);
        startActivity(detailIntent);
    }

    private void setupCurrentLocation()
    {
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            showLocationProgressDialog();
            connectGoogleApiClient();
        }
        else {
            showLocationProgressDialog();
            fetchLocation();
        }
    }

    private synchronized void connectGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        fetchLocation();
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        stopLocationProgressDialog();
        showErrorDialog();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        stopLocationProgressDialog();
        showErrorDialog();
    }

    private void fetchLocation()
    {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            stopLocationProgressDialog();
            showErrorDialog();
        }
        else {
            //Toast.makeText(getActivity(), "Current location determined", Toast.LENGTH_SHORT).show();
            mCurrentLocation = location;
            startIntentService(location);
            syncBranchAtms();
        }
    }

    private void startIntentService(Location location) {
        Intent intent = new Intent(getActivity(), FetchAddressIntentService.class);
        intent.putExtra(FetchAddressIntentService.RECEIVER, new AddressResultReceiver(new Handler()));
        intent.putExtra(FetchAddressIntentService.LOCATION_DATA_EXTRA, location);
        getActivity().startService(intent);
    }

    private void showLocationProgressDialog()
    {
        try {
            mProgressDialog = ProgressDialog.show(getActivity(), null, getString(R.string.fetching_location));
        }
        catch (Exception e) {
            // Ignore
        }
    }

    private void stopLocationProgressDialog()
    {
        if (mProgressDialog != null) {
            try {
                mProgressDialog.dismiss();
            }
            catch (Exception e) {
                // Ignore
            }
            finally {
                mProgressDialog = null;
            }
        }
    }

    private void showErrorDialog()
    {
        mErrorDialog = new AlertDialog.Builder(getActivity()).setMessage(getString(R.string.unable_to_fetch_location))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                }).setNegativeButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(viewIntent);
                        dialog.cancel();
                    }
                }).create();
        mErrorDialog.show();
    }

    private void dismissErrorDialog()
    {
        if (mErrorDialog != null) {
            try {
                mErrorDialog.dismiss();
            }
            catch (Exception e) {
                // Ignore
            }
            finally {
                mErrorDialog = null;
            }
        }
    }

    @Override
    public Drawable getFloatingButtonDrawable(Context context)
    {
        return context.getResources().getDrawable(R.drawable.ic_fab_map);
    }

    @Override
    public void onFloatingButtonPressed() {
        Intent mapIntent = new Intent(getActivity(), BranchAtmMapActivity.class);
        mapIntent.putExtra(BranchAtmMapActivity.CURRENT_LOCATION_KEY, mCurrentLocation);
        mapIntent.putExtra(BranchAtmMapActivity.SINGLE_MAP_KEY, false);
        startActivity(mapIntent);
    }

    @SuppressLint ("ParcelCreator")
    class AddressResultReceiver extends ResultReceiver
    {
        public AddressResultReceiver(Handler handler)
        {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData)
        {
            stopLocationProgressDialog();

            if (resultCode == FetchAddressIntentService.SUCCESS_RESULT) {
                String name = resultData.getString(FetchAddressIntentService.RESULT_DATA_KEY);
                mCurrentLocationHeader.setText(name);
            }
            else {
                showErrorDialog();
            }
        }
    }
}
