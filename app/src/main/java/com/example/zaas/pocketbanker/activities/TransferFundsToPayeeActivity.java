package com.example.zaas.pocketbanker.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.Payee;
import com.example.zaas.pocketbanker.preferences.PocketBankerPreferences;
import com.example.zaas.pocketbanker.sync.NetworkHelper;

/**
 * Created by adugar on 3/20/16.
 */
public class TransferFundsToPayeeActivity extends AppCompatActivity
{
    public static final String PAYEE_LOCAL_ID = "PAYEE_LOCAL_ID";
    EditText mAmount;
    EditText mDescription;
    private Spinner mAccountSpinner;
    private Spinner mTransactionTypeSpinner;
    private List<Account> mAccounts;
    private Account mSelectedAccount;
    private String mSelectedTransactionType;
    private Payee mPayee;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_fund_to_payee);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        int payeeLocalId = getIntent().getIntExtra(PAYEE_LOCAL_ID, -1);
        if (payeeLocalId == -1) {
            finish();
            return;
        }

        mPayee = PocketBankerDBHelper.getInstance().getPayeeForLocalId(payeeLocalId);
        mAccounts = PocketBankerDBHelper.getInstance().getAllAccounts();
        if (mPayee == null || mAccounts.size() == 0) {
            finish();
            return;
        }

        mSelectedAccount = mAccounts.get(0);

        setupViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case android.R.id.home:
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViews()
    {
        mAccountSpinner = (Spinner) findViewById(R.id.from_account_spinner);
        mAccountSpinner.setAdapter(new AccountSpinnerAdapter(this, R.layout.account_spinner_dropdown_item, mAccounts));
        mAccountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                mSelectedAccount = mAccounts.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                mSelectedAccount = null;
            }
        });

        TextView toName = (TextView) findViewById(R.id.to_name);
        toName.setText(mPayee.getName());
        TextView toAccountNumber = (TextView) findViewById(R.id.to_account_number);
        toAccountNumber.setText(mPayee.getAccountNo());

        mAmount = (EditText) findViewById(R.id.amount);
        mDescription = (EditText) findViewById(R.id.description);

        mTransactionTypeSpinner = (Spinner) findViewById(R.id.transaction_type_spinner);
        final List<String> transactionTypeStrings = new ArrayList<>();
        TransactionType[] types = TransactionType.values();
        for (TransactionType type : types) {
            transactionTypeStrings.add(type.toString());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                transactionTypeStrings);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTransactionTypeSpinner.setAdapter(dataAdapter);
        mTransactionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                mSelectedTransactionType = transactionTypeStrings.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                mSelectedTransactionType = TransactionType.PMR.toString();
            }
        });

        final Button transfer = (Button) findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                initiateTransfer();
            }
        });
    }

    private void initiateTransfer()
    {
        String amount = mAmount.getText().toString();
        if (TextUtils.isEmpty(amount)) {
            showErrorDialog();
            return;
        }

        showConfirmationDialog(amount);
    }

    private void showErrorDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.enter_amount));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Do nothing
            }
        });
        builder.show();
    }

    private void showConfirmationDialog(String amount)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format(getString(R.string.transfer_confirmation_message), amount,
                PocketBankerPreferences.get(PocketBankerPreferences.NAME), mPayee.getName()));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String srcAccount = mSelectedAccount.getAccountNumber();
                String destAccount = mPayee.getAccountNo();
                String amount = mAmount.getText().toString();
                String description = mDescription.getText().toString();
                String payeeId = mPayee.getPayeeId();
                String type = mSelectedTransactionType;

                new TransferFundsTask().execute(srcAccount, destAccount, amount, description, payeeId, type);
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Do nothing
            }
        });
        builder.show();
    }

    private void showResultDialog(String referenceNumber)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (!TextUtils.isEmpty(referenceNumber)) {
            builder.setMessage(String.format(getString(R.string.transfer_result_message), referenceNumber));
        }
        else {
            builder.setMessage(getString(R.string.funds_transfer_failed_msg));
        }
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        builder.show();
    }

    private void showProgressDialog()
    {
        try {
            mProgressDialog = ProgressDialog.show(this, null, getString(R.string.transferring_funds));
        }
        catch (Exception e) {
            // Ignore
        }
    }

    private void stopProgressDialog()
    {
        if (mProgressDialog != null) {
            try {
                mProgressDialog.dismiss();
            }
            catch (Exception e) {
                // Ignore
            }
            finally {
                mProgressDialog = null;
            }
        }
    }

    public enum TransactionType
    {
        PMR("PMR"),
        DIRECT_TO_HOME("Direct-To-Home payments"),
        SCHOOL_FEE_PAYMENT("School fee payment"),
        MOVIE_TICKET("Movie Ticket"),
        ELECTRICITY("Electricity"),
        RESTAURANT_TICKET("Restaurant ticket"),
        FUEL("Fuel"),
        GROCERIES("Groceries"),
        HOME_LOAN_EMI("Home Loan EMI"),
        INSURANCE_PAYMENT("Insurance Payment"),
        CAR_INSURANCE("Car Insurance"),
        MUTUAL_FUND_PAYMENTS("Mutual Fund Payments");

        private final String value;

        TransactionType(String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return value;
        }
    }

    public class TransferFundsTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... params)
        {
            NetworkHelper networkHelper = new NetworkHelper();

            long amount = 0;
            int payeeId = -1;
            try {
                amount = Long.parseLong(params[2]);
                payeeId = Integer.parseInt(params[4]);
            }
            catch (Exception e) {
                // DO nothing
            }
            String refNo = networkHelper.transferFunds(params[0], params[1], amount, params[3], payeeId, params[5]);

            return refNo;
        }

        @Override
        protected void onPostExecute(String result)
        {
            stopProgressDialog();
            showResultDialog(result);
        }
    }

    public class AccountSpinnerAdapter extends ArrayAdapter<Account>
    {
        public AccountSpinnerAdapter(Context context, int resource, List<Account> objects)
        {
            super(context, resource, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.account_spinner_dropdown_item, parent, false);
            Account account = mAccounts.get(position);

            TextView accountNumber = (TextView) view.findViewById(R.id.account_number);
            accountNumber.setText(account.getAccountNumber());

            TextView accountType = (TextView) view.findViewById(R.id.account_type);
            accountType.setText(account.getType());

            return view;
        }

        @Override
        public View getView(int position, View cnvtView, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.account_spinner_selected_item, parent, false);
            Account account = mAccounts.get(position);

            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(PocketBankerPreferences.get(PocketBankerPreferences.NAME));

            TextView accountNumber = (TextView) view.findViewById(R.id.account_number);
            accountNumber.setText(account.getAccountNumber());

            TextView accountType = (TextView) view.findViewById(R.id.account_type);
            accountType.setText(account.getType());

            return view;
        }
    }
}
