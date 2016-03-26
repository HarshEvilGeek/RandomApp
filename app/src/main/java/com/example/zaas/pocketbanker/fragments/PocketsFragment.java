package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.CreatePocketsActivity;

/**
 * Created by zaas on 3/17/16.
 */
public class PocketsFragment extends Fragment {

    Button getStarted;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pockets, container,false);
        getStarted = (Button) rootView.findViewById(R.id.pockets_get_started);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreatePocketsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return rootView;
    }
}
