package com.example.zaas.pocketbanker.sync;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.RestAdapter;

import android.text.TextUtils;
import android.util.Log;

import com.example.zaas.pocketbanker.models.network.AccountSummary;
import com.example.zaas.pocketbanker.models.network.AuthToken;
import com.example.zaas.pocketbanker.models.network.BalanceEnquiry;
import com.example.zaas.pocketbanker.models.network.BehaviorScore;
import com.example.zaas.pocketbanker.models.network.BranchAtmLocations;
import com.example.zaas.pocketbanker.models.network.CardAccDetailsResponse;
import com.example.zaas.pocketbanker.models.network.FundTransfer;
import com.example.zaas.pocketbanker.models.network.LoanAccountSummary;
import com.example.zaas.pocketbanker.models.network.LoanEMIDetails;
import com.example.zaas.pocketbanker.models.network.LoanTransactionDetails;
import com.example.zaas.pocketbanker.models.network.RegisteredPayees;
import com.example.zaas.pocketbanker.models.network.Transactions;

/**
 * Created by zaraahmed on 3/18/16.
 */
public class NetworkHelper
{

    private static final String LOG_TAG = NetworkHelper.class.getSimpleName();
    RestAdapter restAdapter;
    UCWARequestInterceptor requestInterceptor;
    IUCWAAPIInterface methods;

    private static final String DEFAULT_ENDPOINT = "https://retailbanking.mybluemix.net";
    private static final String ALPHA_ENDPOINT = "http://alphaiciapi2.mybluemix.net";
    private static final String TOKEN = "090414bcb424";

    public NetworkHelper()
    {
        requestInterceptor = new UCWARequestInterceptor();

    }

    public void fetchAuthToken()
    {
        try {
            Log.i(LOG_TAG, "Fetching auth token");
            setupRetrofitParamsForRequest(null, null);
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
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
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

    public void fetchAccountSummary(String accountNumber, String customerId)
    {
        try {
            Log.i(LOG_TAG, "Fetching account summary for : " + accountNumber + " " + customerId);
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "";

            if (!TextUtils.isEmpty(accountNumber) && TextUtils.isEmpty(customerId)) {

                href = "banking/icicibank/account_summary" + "?" + "client_id=" + clientId + "&token=" + token
                        + "&custid=" + "&accountno=" + accountNumber;
            }
            else if (TextUtils.isEmpty(accountNumber) && !TextUtils.isEmpty(customerId)) {

                href = "banking/icicibank/account_summary" + "?" + "client_id=" + clientId + "&token=" + token
                        + "&custid=" + customerId + "&accountno=";
            }
            else if (!TextUtils.isEmpty(accountNumber) && !TextUtils.isEmpty(customerId)) {

                href = "banking/icicibank/account_summary" + "?" + "client_id=" + clientId + "&token=" + token
                        + "&custid=" + customerId + "&accountno=" + accountNumber;
            }
            Log.i(LOG_TAG, "Fetching account summary for href : " + href);

            AccountSummary accountSummary = methods.getAccountSummary(href);

            if (accountSummary != null) {
                Log.i(LOG_TAG, "data : " + accountSummary);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching account summary", e);
        }

    }

    public void fetchTransactionHistoryForDays(String accountNumber, int noOfDays)
    {
        try {
            Log.i(LOG_TAG, "Fetching account transaction history for : " + accountNumber + " and number of days : "
                    + noOfDays);
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "banking/icicibank/ndaystransaction" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&accountno=" + accountNumber + "&days=" + noOfDays;
            Log.i(LOG_TAG, "Fetching transaction history for href : " + href);

            Transactions transactions = methods.getTransactions(href);

            if (transactions != null) {
                Log.i(LOG_TAG, "data : " + transactions);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching transactions", e);
        }

    }

    public void fetchTransactionHistoryForPeriod(String accountNumber, Date startDate, Date endDate)
    {
        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Log.i(LOG_TAG,
                    "Fetching account transaction history for : " + accountNumber + " and period : "
                            + df.format(startDate) + df.format(endDate));
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "banking/icicibank/transactioninterval" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&accountno=" + accountNumber + "&fromdate=" + df.format(startDate) + "&todate="
                    + df.format(endDate);
            Log.i(LOG_TAG, "Fetching transaction history for href : " + href);

            Transactions transactions = methods.getTransactions(href);

            if (transactions != null) {
                Log.i(LOG_TAG, "data : " + transactions);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching transactions", e);
        }

    }

    public void fetchBehaviorScore(String accountNumber)
    {
        try {

            Log.i(LOG_TAG, "Fetching behavior for : " + accountNumber);
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "banking/icicibank/behaviour_score" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&accountno=" + accountNumber;
            Log.i(LOG_TAG, "Fetching behaviour score for href : " + href);

            BehaviorScore behaviour = methods.getBehaviorScore(href);

            if (behaviour != null) {
                Log.i(LOG_TAG, "data : " + behaviour);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching bevahiour score", e);
        }

    }

    public void getRegisteredPayees(String customerId)
    {
        try {

            Log.i(LOG_TAG, "Fetching payees for : " + customerId);
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "banking/icicibank/listpayee" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&custid=" + customerId;
            Log.i(LOG_TAG, "Fetching registered payees for href : " + href);

            RegisteredPayees registeredPayees = methods.getRegisteredPayees(href);

            if (registeredPayees != null) {
                Log.i(LOG_TAG, "data : " + registeredPayees);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching registeredPayees", e);
        }

    }

    public void getBranchAtmLocations(String type, long latitude, long longitude)
    {
        try {

            Log.i(LOG_TAG, "Fetching branch/atm locations for : " + type);
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "banking/icicibank/BranchAtmLocator" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&locate=" + type + "&lat=" + latitude + "&long=" + longitude;
            Log.i(LOG_TAG, "Fetching branch atm locations for href : " + href);

            BranchAtmLocations branchAtmLocations = methods.getBranchAtmLocations(href);

            if (branchAtmLocations != null) {
                Log.i(LOG_TAG, "data : " + branchAtmLocations);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching branchAtmLocations", e);
        }

    }

    public void getLoanAccountSummary(String custId)
    {
        try {

            Log.i(LOG_TAG, "Fetching loan account details for : " + custId);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "rest/Loan/getLoanDetails/" + custId + "/" + clientId + "/" + token;
            Log.i(LOG_TAG, "Fetching loan account details for href : " + href);

            LoanAccountSummary loanAccountSummary = methods.getLoanAccountSummary(href);

            if (loanAccountSummary != null) {
                Log.i(LOG_TAG, "data : " + loanAccountSummary);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching loanAccountSummary", e);
        }

    }

    public void getLoanEMIDetails(String accountNo)
    {
        try {

            Log.i(LOG_TAG, "Fetching loan EMI details for : " + accountNo);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "rest/Loan/EMIDetails/L001/" + accountNo + "/" + clientId + "/" + token;
            Log.i(LOG_TAG, "Fetching loan EMI details for href : " + href);

            LoanEMIDetails loanEMIDetails = methods.getLoanEMIDetails(href);

            if (loanEMIDetails != null) {
                Log.i(LOG_TAG, "data : " + loanEMIDetails);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching loanEMIDetails", e);
        }

    }

    public void transferFunds(String customerId, String srcAccount, String destAccount, long amount, String payeeDesc,
            int payeeId, String typeOfTrans)
    {
        try {

            Log.i(LOG_TAG, "Transferring funds ");
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;

            String pd = "";
            if (!TextUtils.isEmpty(payeeDesc)) {
                pd = payeeDesc;
            }
            int pid = -1;
            if (payeeId != -1) {
                pid = payeeId;
            }
            String tot = "";
            if (!TextUtils.isEmpty(typeOfTrans)) {
                tot = typeOfTrans;
            }

            // TODO : what we need to do if payeeId is not given by user
            String href = "banking/icicibank/fundTransfer" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&srcAccount=" + srcAccount + "&destAccount=" + destAccount + "&amt=" + amount + "&payeeDesc="
                    + pd + "&payeeId=" + pid + "&type_of_transaction=" + tot;

            Log.i(LOG_TAG, "transferring funds for href : " + href);

            FundTransfer fundTransfer = methods.makeTransaction(href);

            if (fundTransfer != null) {
                Log.i(LOG_TAG, "data : " + fundTransfer);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while making transaction", e);
        }

    }

    public void getLoanTransactionDetails(String accountNo)
    {
        try {

            Log.i(LOG_TAG, "Fetching loan transaction details for : " + accountNo);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "rest/Loan/transDetails/L001/" + accountNo + "/" + clientId + "/" + token;
            Log.i(LOG_TAG, "Fetching loan transaction details for href : " + href);

            LoanTransactionDetails loanTransactionDetails = methods.getLoanTransactionDetails(href);

            if (loanTransactionDetails != null) {
                Log.i(LOG_TAG, "data : " + loanTransactionDetails);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching loanTransactionDetails", e);
        }

    }

    public void getCardAccountDetails(String custId)
    {
        try {

            Log.i(LOG_TAG, "Fetching card details for : " + custId);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "rest/Card/getCardDetails/" + custId + "/" + clientId + "/" + token;
            Log.i(LOG_TAG, "Fetching card details for href : " + href);

            CardAccDetailsResponse cardAccDetailsResponse = methods.getCardDetails(href);

            if (cardAccDetailsResponse != null) {
                Log.i(LOG_TAG, "data : " + cardAccDetailsResponse);

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching cardAccDetailsResponse", e);
        }

    }

    private void setupRetrofitParamsForRequest(String contentType, String endPoint)
    {

        if (TextUtils.isEmpty(endPoint)) {
            endPoint = DEFAULT_ENDPOINT;
        }
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
