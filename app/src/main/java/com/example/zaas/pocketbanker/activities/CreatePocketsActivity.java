package com.example.zaas.pocketbanker.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.fragments.PinOrFingerprintFragment;
import com.example.zaas.pocketbanker.models.local.PocketAccount;
import com.example.zaas.pocketbanker.utils.SecurityUtils;

/**
 * Created by akhil on 3/25/16.
 */
public class CreatePocketsActivity extends BaseRestrictedActivity {

    public static final String LOGIN_KEY = "LOGIN_KEY";

    boolean isLogin;

    EditText firstName;
    EditText lastName;
    EditText phoneNumber;
<<<<<<< HEAD
    EditText email;

    ProgressDialog createProgressDialog;
    boolean createInProgress = false;

    Button createOrLogin;
=======
>>>>>>> 1c811ae89dcb244251dd95651d4a866c084b3289

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_pockets);

        Intent intent = getIntent();
        isLogin = intent != null && intent.getBooleanExtra(LOGIN_KEY, false);

        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        email = (EditText) findViewById(R.id.email_address);

        createOrLogin = (Button) findViewById(R.id.create_or_login);

        if (isLogin) {
            firstName.setVisibility(View.GONE);
            lastName.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            createOrLogin.setText("LOGIN");
        }
        createOrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createInProgress) return;
                if (!isLogin) {
                    if (TextUtils.isEmpty(firstName.getText().toString())
                            || TextUtils.isEmpty(lastName.getText().toString())
                            || TextUtils.isEmpty(email.getText().toString())
                            || TextUtils.isEmpty(phoneNumber.getText().toString())) {
                        Toast.makeText(CreatePocketsActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                    }
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                createInProgress = true;
                createProgressDialog = new ProgressDialog(CreatePocketsActivity.this);
                createProgressDialog.setMessage("Logging in...");
                createProgressDialog.show();
                if (isLogin) {
                    new CreatePocketsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                            phoneNumber.getText().toString());
                } else {
                    new CreatePocketsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                            firstName.getText().toString(), lastName.getText().toString(),
                            email.getText().toString(), phoneNumber.getText().toString());
                }

            }
        });
    }

    class CreatePocketsTask extends AsyncTask<String, String, String> {

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
            createProgressDialog.dismiss();
            createInProgress = false;
            if ("SUCCESS".equals(s)) {
                PocketAccount pocketAccount = new PocketAccount();
                pocketAccount.setFirstName(firstName.getText().toString());
                pocketAccount.setLastName(lastName.getText().toString());
                pocketAccount.setEmailAddress(email.getText().toString());
                pocketAccount.setPhoneNumber(phoneNumber.getText().toString());
                pocketAccount.setBalance(0);
                pocketAccount.setMerchantId("OSIDJW34");
                SecurityUtils.savePocketsAccount(pocketAccount);
                Intent intent = new Intent(CreatePocketsActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.TARGET_FRAGMENT_KEY, MainActivity.FRAGMENT_POCKETS_HOME);
                CreatePocketsActivity.this.startActivity(intent);
            }
        }
    }
}
