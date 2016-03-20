package com.example.zaas.pocketbanker.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.fragments.LoginFragment;
import com.example.zaas.pocketbanker.fragments.PinOrFingerprintFragment;
import com.example.zaas.pocketbanker.utils.SecurityUtils;

/**
 * Created by akhil on 3/17/16.
 */
public class AuthorizationActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        goToFragment();
    }

    private void goToFragment() {
        Fragment fragment;
        Bundle args = new Bundle();
        if (SecurityUtils.isLoginDataStored()) {
            fragment = new PinOrFingerprintFragment();
        } else {
            fragment = new LoginFragment();
        }
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }
}
