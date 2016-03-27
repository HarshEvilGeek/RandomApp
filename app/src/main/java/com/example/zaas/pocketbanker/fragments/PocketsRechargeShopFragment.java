package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    EditText searchField;

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

        searchField = (EditText) rootView.findViewById(R.id.search_field);

        searchField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });


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
