package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.AddPayeeNFCActivity;
import com.example.zaas.pocketbanker.activities.TransferFundsToPayeeActivity;
import com.example.zaas.pocketbanker.adapters.PayeeAdapter;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.interfaces.IFloatingButtonListener;
import com.example.zaas.pocketbanker.models.local.Payee;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Fragment to show transfer funds UI Created by zaas on 3/17/16.
 */
public class TransferFundsFragment extends Fragment implements PayeeAdapter.OnClickListener, IFloatingButtonListener
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

        getActivity().setTitle(R.string.action_transfer);

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
        mAdapter = new PayeeAdapter(getActivity(), PocketBankerDBHelper.getInstance().getAllPayees());
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
    }

    private void syncPayees()
    {
        new NetworkLoadTask().execute();
    }

    @Override
    public void onClick(Payee payee)
    {
        Intent intent = new Intent(getActivity(), TransferFundsToPayeeActivity.class);
        intent.putExtra(TransferFundsToPayeeActivity.PAYEE_LOCAL_ID, payee.getId());
        startActivity(intent);
    }

    @Override
    public Drawable getFloatingButtonDrawable(Context context)
    {
        return ContextCompat.getDrawable(context, R.drawable.plus_32);
    }

    @Override
    public void onFloatingButtonPressed()
    {
        startActivity(new Intent(getActivity(), AddPayeeNFCActivity.class));
    }

    public class NetworkLoadTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            if (!mSwipeContainer.isRefreshing()) {
                mSwipeContainer.setRefreshing(true);
            }
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            NetworkHelper networkHelper = new NetworkHelper();

            String custId = Constants.CUST_ID;
            networkHelper.fetchRegisteredPayees(custId);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            mSwipeContainer.setRefreshing(false);
            mAdapter.setData(PocketBankerDBHelper.getInstance().getAllPayees());
        }
    }
}
