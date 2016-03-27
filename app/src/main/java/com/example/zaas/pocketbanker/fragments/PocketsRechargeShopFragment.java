package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.PocketShopAdapter;
import com.example.zaas.pocketbanker.adapters.RecommendationAdapter;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;

/**
 * Created by akhil on 3/25/16.
 */
public class PocketsRechargeShopFragment extends Fragment {
    RecyclerView mRecyclerView;
    PocketShopAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_pockets_shop_recharge, container, false);

        getActivity().setTitle(R.string.action_pockets_recharge_shop);

        setupRecyclerView(rootView);

        return rootView;
    }

    private void setupRecyclerView(View rootView)
    {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mAdapter = new PocketShopAdapter(getActivity(), PocketBankerDBHelper.getInstance().getAllShops());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
    }

}
