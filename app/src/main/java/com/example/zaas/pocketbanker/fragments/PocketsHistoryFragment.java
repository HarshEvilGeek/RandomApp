package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;

/**
 * Created by akhil on 3/26/16.
 */
public class PocketsHistoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pockets_history, container,false);
        return rootView;
    }
}
