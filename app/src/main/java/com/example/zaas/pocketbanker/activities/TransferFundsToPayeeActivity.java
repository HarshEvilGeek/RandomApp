package com.example.zaas.pocketbanker.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.Payee;

/**
 * Created by adugar on 3/20/16.
 */
public class TransferFundsToPayeeActivity extends AppCompatActivity
{
    public static final String PAYEE_LOCAL_ID = "PAYEE_LOCAL_ID";
    EditText mAmount;
    private Account mAccount;
    private Payee mPayee;

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

        mPayee = new Payee("1", "Shayoni Seth", "510000245001", "Shy", System.currentTimeMillis(), "SHY245");
        Account account = new Account();
        account.setAccountNumber("120938029383");
        account.setBalance(203000);
        account.setType("Savings");
        account.setLastUpdateTime(System.currentTimeMillis());
        mAccount = account;

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
        TextView fromName = (TextView) findViewById(R.id.from_name);
        fromName.setText("Akshay Dugar");
        TextView fromAccountNumber = (TextView) findViewById(R.id.from_account_number);
        fromAccountNumber.setText(mAccount.getAccountNumber());
        TextView toName = (TextView) findViewById(R.id.to_name);
        toName.setText(mPayee.getName());
        TextView toAccountNumber = (TextView) findViewById(R.id.to_account_number);
        toAccountNumber.setText(mPayee.getAccountNo());

        mAmount = (EditText) findViewById(R.id.amount);

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
        builder.setMessage(String.format(getString(R.string.transfer_confirmation_message), amount, "Akshay Dugar",
                mPayee.getName()));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Do nothing
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
}
