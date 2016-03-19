package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.network.NetworkHelper;

/**
 * Created by zaas on 3/17/16.
 */
public class AccountSummaryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testAccountSummary();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_summary, container,false);
        return rootView;
    }

    private void testAccountSummary()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new NetworkHelper().fetchAccountBalance("5555666677770949");
            }
        }).start();
    }
}
