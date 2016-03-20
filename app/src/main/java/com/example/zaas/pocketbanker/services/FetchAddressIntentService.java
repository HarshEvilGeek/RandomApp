package com.example.zaas.pocketbanker.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by shseth on 3/20/2016.
 */
public class FetchAddressIntentService extends IntentService {

    public static final String RECEIVER = "receiver";
    public static final String LOCATION_DATA_EXTRA = "location_data";
    public static final int FAILURE_RESULT = 0;
    public static final int SUCCESS_RESULT = 1;
    public static final String RESULT_DATA_KEY = "result_data";
    protected ResultReceiver mReceiver;

    public FetchAddressIntentService() {
        super(FetchAddressIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        mReceiver = intent.getParcelableExtra(RECEIVER);
        Location location = intent.getParcelableExtra(
                LOCATION_DATA_EXTRA);
        if (location == null) {
            deliverResultToReceiver(FAILURE_RESULT, null);
        }

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
        } catch (Exception e) {
            // Catch any other exception
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0 || addresses.get(0) == null) {
            deliverResultToReceiver(FAILURE_RESULT, null);
        } else {
            Address address = addresses.get(0);
            deliverResultToReceiver(SUCCESS_RESULT,
                    address);
        }
    }

    private void deliverResultToReceiver(int resultCode, Address address) {
        Bundle bundle = new Bundle();
        if (address != null) {
            String addressString = address.getSubLocality();
            if (TextUtils.isEmpty(addressString)) {
                addressString = address.getThoroughfare();
            }
            if (TextUtils.isEmpty(addressString)) {
                addressString = address.getLocality();
            }
            bundle.putString(RESULT_DATA_KEY, addressString);
        }
            mReceiver.send(resultCode, bundle);
    }
}
