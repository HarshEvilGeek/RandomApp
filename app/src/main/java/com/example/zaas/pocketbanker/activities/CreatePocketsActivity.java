package com.example.zaas.pocketbanker.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.fragments.PinOrFingerprintFragment;
import com.example.zaas.pocketbanker.models.local.PocketAccount;
import com.example.zaas.pocketbanker.utils.Constants;
import com.example.zaas.pocketbanker.utils.SecurityUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by akhil on 3/25/16.
 */
public class CreatePocketsActivity extends BaseRestrictedActivity {

    EditText firstName;
    EditText lastName;
    EditText phoneNumber;
    EditText email;
    TextView birthDate;
    RadioGroup genderGroup;
    RadioButton genderMale;
    RadioButton genderFemale;

    private long birthdayValue = System.currentTimeMillis() - (21 * Constants.ONE_YEAR_IN_MILLIS);

    ProgressDialog createProgressDialog;
    boolean createInProgress = false;

    Button createOrLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_pockets);

        Intent intent = getIntent();

        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        email = (EditText) findViewById(R.id.email_address);
        birthDate = (TextView) findViewById(R.id.birth_date);
        genderGroup = (RadioGroup) findViewById(R.id.gender_group);
        genderMale = (RadioButton) findViewById(R.id.gender_male);
        genderFemale = (RadioButton) findViewById(R.id.gender_female);

        createOrLogin = (Button) findViewById(R.id.create_or_login);

        createOrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createInProgress) return;
                if (TextUtils.isEmpty(firstName.getText().toString())
                        || TextUtils.isEmpty(lastName.getText().toString())
                        || TextUtils.isEmpty(email.getText().toString())
                        || TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    Toast.makeText(CreatePocketsActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                createInProgress = true;
                createProgressDialog = new ProgressDialog(CreatePocketsActivity.this);
                createProgressDialog.setMessage("Creating...");
                createProgressDialog.show();
                PocketAccount pocketAccount = new PocketAccount();
                pocketAccount.setFirstName(firstName.getText().toString());
                pocketAccount.setLastName(lastName.getText().toString());
                pocketAccount.setEmailAddress(email.getText().toString());
                pocketAccount.setPhoneNumber(phoneNumber.getText().toString());
                pocketAccount.setBalance(0);
                pocketAccount.setMerchantId("OSIDJW34");
                pocketAccount.setBirthday(birthdayValue);
                if (genderMale.isSelected()) {
                    pocketAccount.setGender(PocketAccount.Gender.MALE);
                } else {
                    pocketAccount.setGender(PocketAccount.Gender.FEMALE);
                }
                new CreatePocketsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                        pocketAccount);
            }
        });
        setDatePickers();
    }


    private void setDatePickers() {
        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On button click show datepicker dialog
                createBirthdayDialog().show();
            }
        });
        updateDates();
    }

    private void updateDates() {
        birthDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(birthdayValue)));
    }

    protected Dialog createBirthdayDialog() {
        Time initialDate = new Time();
        initialDate.set(birthdayValue);
        return new DatePickerDialog(this, birthdayPickerListener, initialDate.year, initialDate.month,
                initialDate.monthDay);
    }

    private DatePickerDialog.OnDateSetListener birthdayPickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            Time updateDate = new Time();
            updateDate.set(selectedDay, selectedMonth, selectedYear);
            birthdayValue = updateDate.normalize(true);
            updateDates();
        }
    };

    class CreatePocketsTask extends AsyncTask<PocketAccount, String, PocketAccount> {

        boolean success;

        @Override
        protected PocketAccount doInBackground(PocketAccount... params) {
            success = true;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(PocketAccount pocketAccount) {
            createProgressDialog.dismiss();
            createInProgress = false;
            if (success) {
                SecurityUtils.savePocketsAccount(pocketAccount);
                Intent intent = new Intent(CreatePocketsActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.TARGET_FRAGMENT_KEY, MainActivity.FRAGMENT_POCKETS_HOME);
                CreatePocketsActivity.this.startActivity(intent);
            } else {
                Toast.makeText(CreatePocketsActivity.this, "Pockets creation or linking failed.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
