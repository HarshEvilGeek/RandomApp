package com.example.zaas.pocketbanker.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
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
import com.example.zaas.pocketbanker.models.local.PocketAccount;
import com.example.zaas.pocketbanker.preferences.PocketBankerPreferences;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;

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
    ProgressDialog createProgressDialog;
    boolean createInProgress = false;
    Button createOrLogin;
    private long birthdayValue = System.currentTimeMillis() - (21 * Constants.ONE_YEAR_IN_MILLIS);
    private DatePickerDialog.OnDateSetListener birthdayPickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
        {
            Time updateDate = new Time();
            updateDate.set(selectedDay, selectedMonth, selectedYear);
            birthdayValue = updateDate.normalize(true);
            updateDates();
        }
    };

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

        String name = PocketBankerPreferences.get(PocketBankerPreferences.NAME);
        if (!TextUtils.isEmpty(name)) {
            if (name.contains(" ")) {
                firstName.setText(name.substring(0, name.indexOf(" ")));
                lastName.setText(name.substring(name.indexOf(" ") + 1, name.length()));
            }
            else {
                firstName.setText(name);
            }
        }

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
        return new DatePickerDialog(this, R.style.DatePickerTheme, birthdayPickerListener, initialDate.year, initialDate.month,
                initialDate.monthDay);
    }

    private void startPocketsActivity()
    {
        Intent intent = new Intent(CreatePocketsActivity.this, MainActivity.class);
        intent.putExtra(MainActivity.TARGET_FRAGMENT_KEY, MainActivity.FRAGMENT_POCKETS_HOME);
        CreatePocketsActivity.this.startActivity(intent);

    }

    class CreatePocketsTask extends AsyncTask<PocketAccount, String, String> {


        @Override
        protected String doInBackground(PocketAccount... params) {
            NetworkHelper networkHelper = new NetworkHelper();
            PocketAccount pocketAccount = params[0];
            String response = networkHelper.createWallet(pocketAccount.getFirstName(), pocketAccount.getLastName(), pocketAccount.getEmailAddress(), pocketAccount.getPhoneNumber(), new Date(pocketAccount.getBirthday()), pocketAccount.getGender().name());
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            createProgressDialog.dismiss();
            createInProgress = false;
            if(!TextUtils.isEmpty(response)) {
                if(Constants.WALLET_ALREADY_EXISTS.equals(response)) {
                    Toast.makeText(CreatePocketsActivity.this, "Pockets account already exists.", Toast.LENGTH_LONG).show();
                    startPocketsActivity();
                }
                else if (Constants.WALLET_CREATED_SUCCESSFULLY.equals(response)) {
                    Toast.makeText(CreatePocketsActivity.this, "Pockets account created.", Toast.LENGTH_LONG).show();
                    startPocketsActivity();
                }
                else if (Constants.WALLET_CREATION_FAILED.equals(response)) {
                    Toast.makeText(CreatePocketsActivity.this, "Pockets creation or linking failed.", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(CreatePocketsActivity.this, "Pockets creation or linking failed.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
