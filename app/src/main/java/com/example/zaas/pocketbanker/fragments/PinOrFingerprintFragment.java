package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;

/**
 * Created by akhil on 3/20/16.
 */
public class PinOrFingerprintFragment extends Fragment {

    public static final String CREATE_PIN_KEY = "CREATE_PIN_KEY";
    public static final String USER_NAME_KEY = "USER_NAME_KEY";
    public static final String PASSWORD_KEY = "PASSWORD_KEY";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pin_fingerprint, container,false);
        return rootView;
    }
}
