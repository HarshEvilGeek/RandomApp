package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.interfaces.IFloatingButtonListener;

/**
 * Created by zaas on 3/17/16.
 */
public class AnalyticsFragment extends Fragment implements IFloatingButtonListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analytics, container,false);
        return rootView;
    }

    @Override
    public Drawable getFloatingButtonDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.plus_32);
    }

    @Override
    public void onFloatingButtonPressed() {
        Toast.makeText(getActivity(), "BLET", Toast.LENGTH_LONG).show();
    }
}
