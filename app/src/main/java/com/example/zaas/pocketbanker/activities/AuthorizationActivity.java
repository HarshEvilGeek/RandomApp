package com.example.zaas.pocketbanker.activities;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.fragments.LoginFragment;
import com.example.zaas.pocketbanker.fragments.PinOrFingerprintFragment;
import com.example.zaas.pocketbanker.preferences.PocketBankerPreferences;
import com.example.zaas.pocketbanker.utils.SecurityUtils;

/**
 * Created by akhil on 3/17/16.
 */
public class AuthorizationActivity extends AppCompatActivity {

    private static final int FRAGMENT_LOGIN = 0;
    private static final int FRAGMENT_PIN = 1;

    String userName;
    String password;

    ProgressDialog loginInProgressDialog;
    boolean loginInProgress = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        if (SecurityUtils.isLoginDataStored()) {
            Bundle args = new Bundle();
            args.putBoolean(PinOrFingerprintFragment.CREATE_PIN_KEY, false);
            goToFragment(FRAGMENT_PIN, args);
        } else {
            goToFragment(FRAGMENT_LOGIN, null);
        }
    }

    private void goToFragment(int fragmentIndex, Bundle args) {
        Fragment fragment = (fragmentIndex == FRAGMENT_PIN)? new PinOrFingerprintFragment() : new LoginFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    public void login(String name, String custId, String pass, View view)
    {
        if (loginInProgress) return;
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        userName = custId;
        password = pass;
        loginInProgress = true;
        loginInProgressDialog = new ProgressDialog(this);
        loginInProgressDialog.setMessage("Logging in...");
        loginInProgressDialog.show();
        PocketBankerPreferences.put(PocketBankerPreferences.NAME, name);
        new LoginTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userName, password);
    }

    class LoginTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "SUCCESS";
        }

        @Override
        protected void onPostExecute(String s) {
            loginInProgressDialog.dismiss();
            loginInProgress = false;
            if ("SUCCESS".equals(s)) {
                SecurityUtils.setAccessToken("AccessToken");
                Bundle args = new Bundle();
                args.putBoolean(PinOrFingerprintFragment.CREATE_PIN_KEY, true);
                args.putString(PinOrFingerprintFragment.USER_NAME_KEY, userName);
                args.putString(PinOrFingerprintFragment.PASSWORD_KEY, password);
                goToFragment(FRAGMENT_PIN, args);
            }
        }
    }
}
