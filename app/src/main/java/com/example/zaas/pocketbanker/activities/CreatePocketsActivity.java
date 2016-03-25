package com.example.zaas.pocketbanker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.zaas.pocketbanker.R;

/**
 * Created by akhil on 3/25/16.
 */
public class CreatePocketsActivity extends BaseRestrictedActivity {

    public static final String LOGIN_KEY = "LOGIN_KEY";

    boolean isLogin;

    EditText firstName;
    EditText lastName;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_pockets);

        Intent intent = getIntent();

        isLogin = intent != null && intent.getBooleanExtra(LOGIN_KEY, false);

    }
}
