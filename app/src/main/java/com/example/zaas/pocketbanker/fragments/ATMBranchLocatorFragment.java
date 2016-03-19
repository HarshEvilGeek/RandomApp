package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.ATMBranchMapActivity;
import com.example.zaas.pocketbanker.adapters.BranchAtmAdapter;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to show branch/ATM locations Created by akhil on 3/17/16.
 */
public class ATMBranchLocatorFragment extends Fragment implements BranchAtmAdapter.OnClickListener, BranchAtmAdapter.OnMapButtonClickListener
{
    SwipeRefreshLayout mSwipeContainer;
    RecyclerView mRecyclerView;
    BranchAtmAdapter mAdapter;
    private List<BranchAtm> mBranchAtmList;

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
        Button tempMapButton = (Button) rootView.findViewById(R.id.temp_button);
        tempMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        });
        setupSwipeContainer(rootView);
        setupRecyclerView(rootView);

        return rootView;
    }

    private void onFabClick() {
        Intent mapIntent = new Intent(getActivity(), ATMBranchMapActivity.class);
        mapIntent.putExtra(ATMBranchMapActivity.SINGLE_MAP_KEY, false);
        startActivity(mapIntent);
    }

    private void setupSwipeContainer(View rootView)
    {
        mSwipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
        mBranchAtmList = createDummyBranchAtmList();
        mAdapter = new BranchAtmAdapter(getActivity(), mBranchAtmList);
        mAdapter.setOnClickListener(this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
    }

    public static List<BranchAtm> createDummyBranchAtmList()
    {
        List<BranchAtm> branchAtmList = new ArrayList<>();
        branchAtmList.add(new BranchAtm("Richmond Road", "1, Richmond Circle, Richmond Road", "Bangalore",
                BranchAtm.Type.BRANCH, new LatLng(12.939848,77.5872505)));
        branchAtmList.add(new BranchAtm("Cubbonpet", "Cubbonpet Main Road, 199/1, 9th cross", "Bangalore",
                BranchAtm.Type.ATM, new LatLng(12.970534, 77.583551)));
        branchAtmList.add(new BranchAtm("Ejipura", "100 Ft Road, Ejipura", "Bangalore",
                BranchAtm.Type.ATM, new LatLng(12.938624, 77.631830)));
        branchAtmList.add(new BranchAtm("Adugodi", "Near Forum Mall, Adugodi", "Bangalore",
                BranchAtm.Type.ATM, new LatLng(12.939955, 77.614663)));
        branchAtmList.add(new BranchAtm("Lal Bagh", "Lal Bagh Road, Raja Ram Mohanroy Extension, Sudhama Nagar", "Bangalore",
                BranchAtm.Type.ATM, new LatLng(12.966250, 77.588004)));
        branchAtmList.add(new BranchAtm("Jayanagar", "Jayanagar 9th Block", "Bangalore",
                BranchAtm.Type.BRANCH, new LatLng(12.923837, 77.593396)));
        branchAtmList.add(new BranchAtm("HAL", "HAL Airport Rd, ISRO Colony, Domlur", "Bangalore",
                BranchAtm.Type.BRANCH, new LatLng(12.966355, 77.644921)));
        branchAtmList.add(new BranchAtm("Chickpet", "OTC Road, Chickpet", "Bangalore",
                BranchAtm.Type.BRANCH, new LatLng(12.977261, 77.574992)));
        return branchAtmList;
    }

    private void syncBranchAtms()
    {
        mSwipeContainer.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                mSwipeContainer.setRefreshing(false);
            }
        }, 4000);
    }

    @Override
    public void onClick(BranchAtm branchAtm)
    {
        Toast.makeText(getActivity(), branchAtm.getName() + " clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapButtonClick(int branchAtmID) {
        Intent mapIntent = new Intent(getActivity(), ATMBranchMapActivity.class);
        mapIntent.putExtra(ATMBranchMapActivity.SINGLE_MAP_KEY, true);
        mapIntent.putExtra(ATMBranchMapActivity.SINGLE_BRANCH_ATM_KEY, branchAtmID);
        startActivity(mapIntent);
    }
}
