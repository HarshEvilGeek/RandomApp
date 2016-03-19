package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.PayeeAdapter;
import com.example.zaas.pocketbanker.models.local.Payee;

/**
 * Fragment to show transfer funds UI Created by zaas on 3/17/16.
 */
public class TransferFundsFragment extends Fragment implements PayeeAdapter.OnClickListener
{
    SwipeRefreshLayout mSwipeContainer;
    RecyclerView mRecyclerView;
    PayeeAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_transfer_funds, container, false);

        setupSwipeContainer(rootView);
        setupRecyclerView(rootView);

        return rootView;
    }

    private void setupSwipeContainer(View rootView)
    {
        mSwipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                syncPayees();
            }
        });
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    private void setupRecyclerView(View rootView)
    {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mAdapter = new PayeeAdapter(getActivity());
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
    }

    private void syncPayees()
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
    public void onClick(Payee payee)
    {
        Toast.makeText(getActivity(), payee.getName() + " clicked!", Toast.LENGTH_SHORT).show();
    }
}
