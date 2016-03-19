package com.example.zaas.pocketbanker.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.Payee;

/**
 * Activity that lets user add a new payee via NFC
 * 
 * Created by adugar on 3/19/16.
 */
public class AddPayeeNFCActivity extends BaseRestrictedActivity implements NfcAdapter.CreateNdefMessageCallback
{
    NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payee_nfc);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, R.string.nfc_not_available, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Register callback
        mNfcAdapter.setNdefPushMessageCallback(this, this);
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

    @Override
    public NdefMessage createNdefMessage(NfcEvent event)
    {
        Payee self = new Payee("1", "Akshay Dugar", "510110", "Agz", System.currentTimeMillis(), "AKI110");
        String text = (self.getName() + ";" + self.getAccountNo());
        return new NdefMessage(new NdefRecord[] { NdefRecord.createMime(
                "application/vnd.com.example.zaas.pocketbanker", text.getBytes()) });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        boolean showBackButton = true;

        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            showBackButton = false;
            processIntent(getIntent());
        }

        if (showBackButton) {
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setHomeButtonEnabled(true);
                ab.setDisplayHomeAsUpEnabled(true);
            }
        }

        if (!mNfcAdapter.isEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.nfc_not_enabled));
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                }
            });
            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    finish();
                }
            });
            builder.show();
        }
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent)
    {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage ndefMsg = (NdefMessage) rawMsgs[0];
        String msg = new String(ndefMsg.getRecords()[0].getPayload());

        String[] msgParts = msg.split(";");
        if (msgParts.length < 2) {
            Toast.makeText(this, R.string.unable_to_add_payee, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.payee_added_title));
        builder.setMessage(String.format(getString(R.string.payee_added_msg), msgParts[0], msgParts[1]));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        builder.show();
    }
}
