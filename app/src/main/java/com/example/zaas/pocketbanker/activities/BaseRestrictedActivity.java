package com.example.zaas.pocketbanker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zaas.pocketbanker.utils.SecurityUtils;

/**
 * Base class to ensure that authorization is granted to the user for all activities
 *
 * Created by akhil on 3/17/16.
 */
public class BaseRestrictedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SecurityUtils.isAccessAuthorize()) {
            Intent intent = new Intent(this, AuthorizationActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SecurityUtils.isAccessAuthorize()) {
            Intent intent = new Intent(this, AuthorizationActivity.class);
            startActivity(intent);
        }
    }
}
