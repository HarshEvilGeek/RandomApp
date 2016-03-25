package com.example.zaas.pocketbanker.activities;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by shseth on 3/20/2016.
 */
public class BranchAtmDetailActivity extends AppCompatActivity {

    private ImageButton mMapButton;
    private BranchAtm mBranchAtm;
    private LatLng mCurrentLocation;
    private ImageButton mPhoneButton;
    private TextView mAddressHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_atm_detail);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }
        int id = extras.getInt(BranchAtmMapActivity.SINGLE_BRANCH_ATM_KEY);
        Location loc = (Location) extras.get(BranchAtmMapActivity.CURRENT_LOCATION_KEY);
        if (loc != null) {
            mCurrentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
        }
        mBranchAtm = PocketBankerDBHelper.getInstance().getBranchAtmForLocalId(id);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(mBranchAtm.getName());
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mAddressHolder = (TextView) findViewById(R.id.address_holder);
        mAddressHolder.setText(mBranchAtm.getAddress() + ", " + mBranchAtm.getCity());
        mMapButton = (ImageButton) findViewById(R.id.map_button);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(v.getContext(), BranchAtmMapActivity.class);
                mapIntent.putExtra(BranchAtmMapActivity.CURRENT_LOCATION_KEY, mCurrentLocation);
                mapIntent.putExtra(BranchAtmMapActivity.SINGLE_MAP_KEY, true);
                mapIntent.putExtra(BranchAtmMapActivity.SINGLE_BRANCH_ATM_KEY, mBranchAtm.getId());
                startActivity(mapIntent);
            }
        });
        mPhoneButton = (ImageButton) findViewById(R.id.phone_button);
        mPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });
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
}
