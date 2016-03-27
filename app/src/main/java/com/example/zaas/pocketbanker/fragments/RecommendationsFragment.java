package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.RecommendationAdapter;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.models.local.Recommendation;

/**
 * Created by zaas on 3/17/16.
 */
public class RecommendationsFragment extends Fragment implements RecommendationAdapter.OnClickListener
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
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
    }

    @Override
    public void onClick(Recommendation recommendation)
    {
        if (recommendation != null && !TextUtils.isEmpty(recommendation.getOpenUrl())) {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recommendation.getOpenUrl()));
                startActivity(browserIntent);
            }
            catch (Exception e) {
                // Do nothing
            }
        }
    }
}
