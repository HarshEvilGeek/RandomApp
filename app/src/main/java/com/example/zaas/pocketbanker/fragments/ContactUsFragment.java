package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.github.mikephil.charting.charts.PieChart;

/**
 * Created by shseth on 3/27/2016.
 */
public class ContactUsFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(R.string.action_contact_us);
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);

        return rootView;
    }
}
