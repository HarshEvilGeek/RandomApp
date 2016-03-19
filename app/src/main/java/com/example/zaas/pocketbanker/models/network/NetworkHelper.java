package com.example.zaas.pocketbanker.models.network;

import retrofit.RestAdapter;

import android.text.TextUtils;
import android.util.Log;

import com.example.zaas.pocketbanker.models.local.AuthToken;
import com.example.zaas.pocketbanker.models.local.BalanceEnquiry;

/**
 * Created by zaraahmed on 3/18/16.
 */
public class NetworkHelper
{

    private static final String LOG_TAG = NetworkHelper.class.getSimpleName();
    RestAdapter restAdapter;
    UCWARequestInterceptor requestInterceptor;
    IUCWAAPIInterface methods;

    public NetworkHelper()
    {
        requestInterceptor = new UCWARequestInterceptor();

    }

    public void fetchAuthToken()
    {
        try {
            Log.i(LOG_TAG, "Fetching auth token");
            setupRetrofitParamsForRequest(null);
            AuthToken token = methods
                    .getToken("corporate_banking/mybank/authenticate_client?client_id=akhilcherian@gmail.com&password=ICIC7202");

            if (token != null) {
                Log.i(LOG_TAG, "auth token : " + token.getToken());

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching auth token", e);
        }

    }

    public void fetchAccountBalance(String accountNumber)
    {
        try {
            Log.i(LOG_TAG, "Fetching account balance for : " + accountNumber);
            setupRetrofitParamsForRequest(null);
            String clientId = "akhilcherian@gmail.com";
            String token = "090414bcb424";
            String href = "banking/icicibank/balanceenquiry" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&accountno=" + accountNumber;
            Log.i(LOG_TAG, "Fetching account balance for : " + accountNumber + " and href : " + href);

            BalanceEnquiry balanceEnquiry = methods.getBalanceEnquiry(href);

            if (balanceEnquiry != null) {
                Log.i(LOG_TAG, "data : " + balanceEnquiry);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching balance enquiry", e);
        }

    }

    private void setupRetrofitParamsForRequest(String contentType)
    {
        String endPoint = "https://retailbanking.mybluemix.net";

        try {
            restAdapter = new RestAdapter.Builder().setRequestInterceptor(requestInterceptor).setEndpoint(endPoint)
                    .setLogLevel(RestAdapter.LogLevel.FULL).build();

            methods = restAdapter.create(IUCWAAPIInterface.class);
            if (!TextUtils.isEmpty(contentType)) {
                requestInterceptor.setContentType(contentType);
            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, " Error while setting up request params", e);
        }
    }
}
