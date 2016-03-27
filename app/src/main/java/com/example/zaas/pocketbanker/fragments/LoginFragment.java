package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.AuthorizationActivity;

/**
 * Created by akhil on 3/20/16.
 */
public class LoginFragment extends Fragment {

    EditText nameField;
    EditText custIdField;
    EditText passwordField;
    TextView loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container,false);
        nameField = (EditText) rootView.findViewById(R.id.name);
        custIdField = (EditText) rootView.findViewById(R.id.customer_id);
        passwordField = (EditText) rootView.findViewById(R.id.password);
        loginButton = (TextView) rootView.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                String custId = custIdField.getText().toString();
                String password = passwordField.getText().toString();
                if (!TextUtils.isEmpty(custId) && !TextUtils.isEmpty(password)) {
                    ((AuthorizationActivity)getActivity()).login(name, custId, password, v);
                } else{
                    Toast.makeText(getActivity(), "Must enter customer ID and password", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }
}
