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
import com.example.zaas.pocketbanker.adapters.RecommendationAdapter;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;

/**
 * Created by zaas on 3/17/16.
 */
public class RecommendationsFragment extends Fragment
{
    RecyclerView mRecyclerView;
    RecommendationAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_recommendations, container, false);

        getActivity().setTitle(R.string.action_recommendations);

        setupRecyclerView(rootView);

        return rootView;
    }

    private void setupRecyclerView(View rootView)
    {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mAdapter = new RecommendationAdapter(getActivity(), PocketBankerDBHelper.getInstance().getAllRecommendations());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
    }
}
