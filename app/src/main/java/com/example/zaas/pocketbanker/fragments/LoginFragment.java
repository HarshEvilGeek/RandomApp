package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zaas.pocketbanker.R;

/**
 * Created by akhil on 3/20/16.
 */
public class LoginFragment extends Fragment {

    EditText custIdField;
    EditText passwordField;
    Button loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container,false);
        custIdField = (EditText) rootView.findViewById(R.id.customer_id);
        passwordField = (EditText) rootView.findViewById(R.id.password);
        loginButton = (Button) rootView.findViewById(R.id.login_button);


        return rootView;
    }
}
