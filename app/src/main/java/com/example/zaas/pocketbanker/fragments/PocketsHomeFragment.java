package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.MainActivity;
import com.example.zaas.pocketbanker.models.local.PocketAccount;
import com.example.zaas.pocketbanker.utils.SecurityUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by akhil on 3/25/16.
 */
public class PocketsHomeFragment extends Fragment {

    TextView name;
    TextView email;
    TextView phone;
    TextView balance;
    TextView dob;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pockets_home, container,false);
        name = (TextView) rootView.findViewById(R.id.pockets_name);
        email = (TextView) rootView.findViewById(R.id.pockets_email_id);
        phone = (TextView) rootView.findViewById(R.id.pockets_phone_number);
        balance = (TextView) rootView.findViewById(R.id.pockets_balance);
        dob = (TextView) rootView.findViewById(R.id.pockets_dob);

        PocketAccount pocketAccount = SecurityUtils.getPocketsAccount();

        if (pocketAccount == null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(MainActivity.TARGET_FRAGMENT_KEY, MainActivity.FRAGMENT_POCKETS_HOME);
            startActivity(intent);
        }

        name.setText(pocketAccount.getFirstName() + " " + pocketAccount.getLastName());
        email.setText(pocketAccount.getEmailAddress());
        phone.setText(pocketAccount.getPhoneNumber());
        balance.setText(String.valueOf(pocketAccount.getBalance()) + "/-");
        dob.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(pocketAccount.getBirthday())));
        return rootView;
    }
}
