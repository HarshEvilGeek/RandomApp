package com.example.zaas.pocketbanker.sync;

import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

import retrofit.RestAdapter;
import retrofit.client.Response;

import android.text.TextUtils;
import android.util.Log;

import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.example.zaas.pocketbanker.models.local.CardAccount;
import com.example.zaas.pocketbanker.models.local.LoanAccount;
import com.example.zaas.pocketbanker.models.local.LoanEMI;
import com.example.zaas.pocketbanker.models.local.Payee;
import com.example.zaas.pocketbanker.models.local.PocketAccount;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.network.AccountSummary;
import com.example.zaas.pocketbanker.models.network.BalanceEnquiry;
import com.example.zaas.pocketbanker.models.network.BehaviorScore;
import com.example.zaas.pocketbanker.models.network.BranchAtmLocations;
import com.example.zaas.pocketbanker.models.network.CardAccDetailsResponse;
import com.example.zaas.pocketbanker.models.network.CardDetails;
import com.example.zaas.pocketbanker.models.network.FundTransfer;
import com.example.zaas.pocketbanker.models.network.LoanAccountSummary;
import com.example.zaas.pocketbanker.models.network.LoanDetails;
import com.example.zaas.pocketbanker.models.network.LoanEMIDetails;
import com.example.zaas.pocketbanker.models.network.LoanTransactionDetails;
import com.example.zaas.pocketbanker.models.network.RegisteredPayees;
import com.example.zaas.pocketbanker.models.network.RequestCode;
import com.example.zaas.pocketbanker.models.network.Transactions;
import com.example.zaas.pocketbanker.models.network.WalletBalanceBody;
import com.example.zaas.pocketbanker.models.network.WalletBalanceResponse;
import com.example.zaas.pocketbanker.models.network.WalletCreation;
import com.example.zaas.pocketbanker.models.network.WalletCreditDebitBody;
import com.example.zaas.pocketbanker.models.network.WalletCreditDebitResponse;
import com.example.zaas.pocketbanker.models.network.WalletDetails;
import com.example.zaas.pocketbanker.models.network.WalletStatementBody;
import com.example.zaas.pocketbanker.models.network.WalletStatementResponse;
import com.example.zaas.pocketbanker.utils.Constants;
import com.example.zaas.pocketbanker.utils.SecurityUtils;
import com.google.gson.Gson;

/**
 * Created by zaraahmed on 3/18/16.
 */
public class NetworkHelper
{

    private static final String LOG_TAG = NetworkHelper.class.getSimpleName();
    private static final String DEFAULT_ENDPOINT = "https://retailbanking.mybluemix.net";
    private static final String ALPHA_ENDPOINT = "http://alphaiciapi2.mybluemix.net";
    private static final String TOKEN = "f8dc8109cc34";
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
            setupRetrofitParamsForRequest(null, null);
            /*
             * AuthToken token = methods
             * .getToken("corporate_banking/mybank/authenticate_client?client_id=akhilcherian@gmail.com&password=ICIC7202"
             * );
             * 
             * if (token != null) { Log.i(LOG_TAG, "auth token : " + token.getToken());
             * 
             * }
             */
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

            Response balanceEnquiryResponse = methods.getBalanceEnquiry(href);

            RequestCode requestCode = null;
            BalanceEnquiry balanceEnquiry = null;
            Gson gson = new Gson();

            if (balanceEnquiryResponse != null) {
                InputStream responseStream = balanceEnquiryResponse.getBody().in();
                if (responseStream != null) {
                    String responseString = IOUtils.toString(responseStream);
                    if (!TextUtils.isEmpty(responseString) && responseString.startsWith("[")
                            && responseString.endsWith("]")) {
                        responseString = responseString.substring(1, responseString.length() - 1);
                        String[] responseStringArray = responseString.split("\\},");
                        if (responseStringArray != null) {
                            // failure
                            if (responseStringArray.length == 1) {

                                requestCode = gson.fromJson(responseStringArray[0], RequestCode.class);

                            }
                            // success
                            else if (responseStringArray.length == 2) {

                                requestCode = gson.fromJson(responseStringArray[0] + "}", RequestCode.class);
                                balanceEnquiry = gson.fromJson(responseStringArray[1], BalanceEnquiry.class);

                            }
                        }
                    }
                }
            }

            if (requestCode != null) {
                Log.e(LOG_TAG, "response code : " + requestCode);
            }

            if (balanceEnquiry != null) {
                Log.e(LOG_TAG, "balanceEnquiry : " + balanceEnquiry);
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

            Response accountSummaryResponse = methods.getAccountSummary(href);

            RequestCode requestCode = null;
            List<AccountSummary> accountSummaryList = new ArrayList<>();
            Gson gson = new Gson();

            if (accountSummaryResponse != null) {
                InputStream responseStream = accountSummaryResponse.getBody().in();
                if (responseStream != null) {
                    String responseString = IOUtils.toString(responseStream);
                    if (!TextUtils.isEmpty(responseString) && responseString.startsWith("[")
                            && responseString.endsWith("]")) {
                        responseString = responseString.substring(1, responseString.length() - 1);
                        String[] responseStringArray = responseString.split("\\},");
                        if (responseStringArray != null) {
                            // failure
                            if (responseStringArray.length == 1) {

                                requestCode = gson.fromJson(responseStringArray[0], RequestCode.class);

                            }

                            else if (responseStringArray.length > 1) {

                                for (int i = 1; i < responseStringArray.length; i++) {
                                    AccountSummary accountSummary = null;
                                    if (i != (responseStringArray.length - 1)) {
                                        accountSummary = gson.fromJson(responseStringArray[i] + "}",
                                                AccountSummary.class);
                                    }
                                    else {
                                        accountSummary = gson.fromJson(responseStringArray[i], AccountSummary.class);
                                    }

                                    if (accountSummary != null) {
                                        accountSummaryList.add(accountSummary);
                                    }
                                }

                                requestCode = gson.fromJson(responseStringArray[0] + "}", RequestCode.class);

                            }
                        }
                    }
                }
            }

            if (requestCode != null) {
                Log.e(LOG_TAG, "response code : " + requestCode);
            }

            if (accountSummaryList != null) {
                Log.e(LOG_TAG, "no of bank accounts locations" + accountSummaryList.size());
                for (AccountSummary accountSummary : accountSummaryList) {
                    Log.e(LOG_TAG, " accountSummary : " + accountSummary);
                    Account.insertOrUpdateAccount(Account.getAccount(accountSummary));
                }
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

            Response transactionsResponse = methods.getTransactions(href);

            RequestCode requestCode = null;
            List<Transactions> transactions = new ArrayList<>();
            Gson gson = new Gson();

            if (transactionsResponse != null) {
                InputStream responseStream = transactionsResponse.getBody().in();
                if (responseStream != null) {
                    String responseString = IOUtils.toString(responseStream);
                    if (!TextUtils.isEmpty(responseString) && responseString.startsWith("[")
                            && responseString.endsWith("]")) {
                        responseString = responseString.substring(1, responseString.length() - 1);
                        String[] responseStringArray = responseString.split("\\},");
                        if (responseStringArray != null) {
                            // failure
                            if (responseStringArray.length == 1) {

                                requestCode = gson.fromJson(responseStringArray[0], RequestCode.class);

                            }
                            // success
                            else if (responseStringArray.length > 1) {

                                for (int i = 1; i < responseStringArray.length; i++) {
                                    Transactions transaction = null;
                                    if (i != (responseStringArray.length - 1)) {
                                        transaction = gson.fromJson(responseStringArray[i] + "}", Transactions.class);
                                    }
                                    else {
                                        transaction = gson.fromJson(responseStringArray[i], Transactions.class);
                                    }

                                    if (transaction != null) {
                                        transactions.add(transaction);
                                    }
                                }

                                requestCode = gson.fromJson(responseStringArray[0] + "}", RequestCode.class);

                            }
                        }
                    }
                }
            }

            if (requestCode != null) {
                Log.e(LOG_TAG, "response code : " + requestCode);
            }

            if (transactions != null) {
                Log.e(LOG_TAG, "transactions" + transactions.size());
                List<Transaction> receivedTransactions = new ArrayList<>();
                for (Transactions transaction : transactions) {
                    Log.e(LOG_TAG, " branch atm location : " + transaction);
                    DateFormat transactionDF = new SimpleDateFormat(Transaction.TRANSACTION_DATE_FORMAT);
                    Transaction dbTransaction = new Transaction(transaction.getAccountno(),
                            transaction.getTransactionamount(), transaction.getClosingbalance(),
                            Transaction.Type.getEnumFromNetworkType(transaction.getCreditdebitflag()),
                            transaction.getRemark(), transactionDF.parse(transaction.getTransactiondate()).getTime());
                    receivedTransactions.add(dbTransaction);
                }
                PocketBankerDBHelper.getInstance().insertUpdateAndDeleteDbModelTable(receivedTransactions);
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

            Response transactionsResponse = methods.getTransactions(href);

            RequestCode requestCode = null;
            List<Transactions> transactions = new ArrayList<>();
            Gson gson = new Gson();

            if (transactionsResponse != null) {
                InputStream responseStream = transactionsResponse.getBody().in();
                if (responseStream != null) {
                    String responseString = IOUtils.toString(responseStream);
                    if (!TextUtils.isEmpty(responseString) && responseString.startsWith("[")
                            && responseString.endsWith("]")) {
                        responseString = responseString.substring(1, responseString.length() - 1);
                        String[] responseStringArray = responseString.split("\\},");
                        if (responseStringArray != null) {
                            // failure
                            if (responseStringArray.length == 1) {

                                requestCode = gson.fromJson(responseStringArray[0], RequestCode.class);

                            }
                            // success
                            else if (responseStringArray.length > 1) {

                                for (int i = 1; i < responseStringArray.length; i++) {
                                    Transactions transaction = null;
                                    if (i != (responseStringArray.length - 1)) {
                                        transaction = gson.fromJson(responseStringArray[i] + "}", Transactions.class);
                                    }
                                    else {
                                        transaction = gson.fromJson(responseStringArray[i], Transactions.class);
                                    }

                                    if (transaction != null) {
                                        transactions.add(transaction);
                                    }
                                }

                                requestCode = gson.fromJson(responseStringArray[0] + "}", RequestCode.class);

                            }
                        }
                    }
                }
            }

            if (requestCode != null) {
                Log.e(LOG_TAG, "response code : " + requestCode);
            }

            if (transactions != null) {
                Log.e(LOG_TAG, "transactions" + transactions.size());
                List<Transaction> receivedTransactions = new ArrayList<>();
                for (Transactions transaction : transactions) {
                    Log.e(LOG_TAG, " branch atm location : " + transaction);
                    DateFormat transactionDF = new SimpleDateFormat(Transaction.TRANSACTION_DATE_FORMAT);
                    Transaction dbTransaction = new Transaction(transaction.getAccountno(),
                            transaction.getTransactionamount(), transaction.getClosingbalance(),
                            Transaction.Type.getEnumFromNetworkType(transaction.getCreditdebitflag()),
                            transaction.getRemark(), transactionDF.parse(transaction.getTransactiondate()).getTime());
                    receivedTransactions.add(dbTransaction);
                }
                PocketBankerDBHelper.getInstance().insertUpdateAndDeleteDbModelTable(receivedTransactions);
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

            Response behaviourResponse = methods.getBehaviorScore(href);

            RequestCode requestCode = null;
            BehaviorScore behaviorScore = null;
            Gson gson = new Gson();

            if (behaviourResponse != null) {
                InputStream responseStream = behaviourResponse.getBody().in();
                if (responseStream != null) {
                    String responseString = IOUtils.toString(responseStream);
                    if (!TextUtils.isEmpty(responseString) && responseString.startsWith("[")
                            && responseString.endsWith("]")) {
                        responseString = responseString.substring(1, responseString.length() - 1);
                        String[] responseStringArray = responseString.split("\\},");
                        if (responseStringArray != null) {
                            // failure
                            if (responseStringArray.length == 1) {

                                requestCode = gson.fromJson(responseStringArray[0], RequestCode.class);

                            }
                            // success
                            else if (responseStringArray.length == 2) {

                                requestCode = gson.fromJson(responseStringArray[0] + "}", RequestCode.class);
                                behaviorScore = gson.fromJson(responseStringArray[1], BehaviorScore.class);

                            }
                        }
                    }
                }
            }

            if (requestCode != null) {
                Log.e(LOG_TAG, "response code : " + requestCode);
            }

            if (behaviorScore != null) {
                Log.e(LOG_TAG, "behaviorScore : " + behaviorScore);
            }

        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching bevahiour score", e);
        }

    }

    public void fetchRegisteredPayees(String customerId)
    {
        try {

            Log.i(LOG_TAG, "Fetching payees for : " + customerId);
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "banking/icicibank/listpayee" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&custid=" + customerId;
            Log.i(LOG_TAG, "Fetching registered payees for href : " + href);

            Response registeredPayeeResponse = methods.getRegisteredPayees(href);

            RequestCode requestCode = null;
            List<RegisteredPayees> registeredPayeesList = new ArrayList<>();
            Gson gson = new Gson();

            if (registeredPayeeResponse != null) {
                InputStream responseStream = registeredPayeeResponse.getBody().in();
                if (responseStream != null) {
                    String responseString = IOUtils.toString(responseStream);
                    if (!TextUtils.isEmpty(responseString) && responseString.startsWith("[")
                            && responseString.endsWith("]")) {
                        responseString = responseString.substring(1, responseString.length() - 1);
                        String[] responseStringArray = responseString.split("\\},");
                        if (responseStringArray != null) {
                            // failure
                            if (responseStringArray.length == 1) {

                                requestCode = gson.fromJson(responseStringArray[0], RequestCode.class);

                            }
                            // success
                            else if (responseStringArray.length > 1) {

                                for (int i = 1; i < responseStringArray.length; i++) {
                                    RegisteredPayees registeredPayee = null;
                                    if (i != (responseStringArray.length - 1)) {
                                        registeredPayee = gson.fromJson(responseStringArray[i] + "}",
                                                RegisteredPayees.class);
                                    }
                                    else {
                                        registeredPayee = gson.fromJson(responseStringArray[i], RegisteredPayees.class);
                                    }

                                    if (registeredPayee != null) {
                                        registeredPayeesList.add(registeredPayee);
                                    }
                                }

                                requestCode = gson.fromJson(responseStringArray[0] + "}", RequestCode.class);

                            }
                        }
                    }
                }
            }

            if (requestCode != null) {
                Log.e(LOG_TAG, "response code : " + requestCode);
            }

            if (registeredPayeesList != null) {
                Log.e(LOG_TAG, "No of registered payees : " + registeredPayeesList.size());
                List<Payee> receivedPayees = new ArrayList<>();
                for (RegisteredPayees regPayee : registeredPayeesList) {
                    Log.e(LOG_TAG, "\n registered payee : : " + regPayee);
                    Payee payee = new Payee(regPayee);
                    receivedPayees.add(payee);
                }
                PocketBankerDBHelper.getInstance().insertUpdateAndDeleteDbModelTable(receivedPayees);
            }

        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching registeredPayees", e);
        }

    }

    public void getBranchAtmLocations()
    {
        List<BranchAtm> branchAtmList = new ArrayList<>();
        getBranchAtmLocations("ATM", branchAtmList);
        getBranchAtmLocations("Branch", branchAtmList);
        PocketBankerDBHelper.getInstance().insertUpdateAndDeleteDbModelTable(branchAtmList);
    }

    public void getBranchAtmLocations(String type, List<BranchAtm> branchAtmList)
    {
        try {

            Log.i(LOG_TAG, "Fetching branch/atm locations for : " + type);
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            // String href = "banking/icicibank/BranchAtmLocator" + "?" + "client_id=" + clientId + "&token=" + token
            // + "&locate=" + type + "&lat=" + latitude + "&long=" + longitude;
            String href = "banking/icicibank/BranchAtmLocator" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&locate=" + type;

            Response branchAtmLocationsResponse = methods.getBranchAtmLocations(href);

            RequestCode requestCode = null;
            List<BranchAtmLocations> branchAtmLocations = new ArrayList<>();
            Gson gson = new Gson();

            if (branchAtmLocationsResponse != null) {
                InputStream responseStream = branchAtmLocationsResponse.getBody().in();
                if (responseStream != null) {
                    String responseString = IOUtils.toString(responseStream);
                    if (!TextUtils.isEmpty(responseString) && responseString.startsWith("[")
                            && responseString.endsWith("]")) {
                        responseString = responseString.substring(1, responseString.length() - 1);
                        String[] responseStringArray = responseString.split("\\},");
                        if (responseStringArray != null) {
                            // failure
                            if (responseStringArray.length == 1) {

                                requestCode = gson.fromJson(responseStringArray[0], RequestCode.class);

                            }

                            // success
                            else if (responseStringArray.length > 1) {

                                for (int i = 1; i < responseStringArray.length; i++) {
                                    BranchAtmLocations branchAtmLocation = null;
                                    if (i != (responseStringArray.length - 1)) {
                                        branchAtmLocation = gson.fromJson(responseStringArray[i] + "}",
                                                BranchAtmLocations.class);
                                    }
                                    else {
                                        branchAtmLocation = gson.fromJson(responseStringArray[i],
                                                BranchAtmLocations.class);
                                    }

                                    if (branchAtmLocation != null) {
                                        branchAtmLocations.add(branchAtmLocation);
                                    }
                                }

                                requestCode = gson.fromJson(responseStringArray[0] + "}", RequestCode.class);

                            }

                        }
                    }
                }
            }

            if (requestCode != null) {
                Log.e(LOG_TAG, "response code : " + requestCode);
            }

            if (branchAtmLocations != null) {
                Log.e(LOG_TAG, "no of branch atm locations" + branchAtmLocations.size());
                for (BranchAtmLocations batmLocations : branchAtmLocations) {
                    BranchAtm branchAtm = new BranchAtm(batmLocations);
                    branchAtmList.add(branchAtm);
                    Log.e(LOG_TAG, " branch atm location : " + batmLocations);
                }
            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching branchAtmLocations", e);
        }

    }

    public void getLoanAccountSummary(String loanAccountNum)
    {
        try {

            Log.i(LOG_TAG, "Fetching loan account details for : " + loanAccountNum);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "rest/Loan/getLoanDetails/" + loanAccountNum + "/" + clientId + "/" + token;
            Log.i(LOG_TAG, "Fetching loan account details for href : " + href);

            LoanAccountSummary loanAccountSummary = methods.getLoanAccountSummary(href);

            if (loanAccountSummary != null) {
                Log.i(LOG_TAG, "data : " + loanAccountSummary);
                for (LoanDetails loanDetails : loanAccountSummary.getLoanDetails()) {
                    LoanAccount.insertOrUpdateLoanAccount(LoanAccount.getLoanAccount(loanDetails));
                }

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
            String href = "rest/Loan/EMIDetails/" + accountNo + "/" + Constants.AGREEMENT_ID + "/" + clientId + "/" + token;
            Log.i(LOG_TAG, "Fetching loan EMI details for href : " + href);

            LoanEMIDetails loanEMIDetails = methods.getLoanEMIDetails(href);

            if (loanEMIDetails != null) {
                Log.i(LOG_TAG, "data : " + loanEMIDetails);

                String[] emisArray = loanEMIDetails.getLastThreeEmis().split(",");
                String[] emiDates = loanEMIDetails.getEmiDates().split(",");

                if(emisArray != null && emiDates != null && emisArray.length > 0 && emiDates.length > 0) {
                    List<LoanEMI> loanEmiList = new ArrayList<>();
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    for(int i = 0 ;i < emisArray.length;i++) {

                        String date = emiDates[i];
                        long dateMillis = df.parse(date).getTime();
                        LoanEMI emi = new LoanEMI(Double.valueOf(emisArray[i]), dateMillis, accountNo);
                        loanEmiList.add(emi);
                    }

                    PocketBankerDBHelper.getInstance().insertUpdateAndDeleteDbModelTable(loanEmiList);
                }
            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while fetching loanEMIDetails", e);
        }

    }

    public String transferFunds(String srcAccount, String destAccount, long amount, String payeeDesc,
            int payeeId, String typeOfTrans)
    {
        FundTransfer fundTransfer = null;

        try {

            Log.i(LOG_TAG, "Transferring funds ");
            setupRetrofitParamsForRequest(null, null);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;

            String pd = "";
            if (!TextUtils.isEmpty(payeeDesc)) {
                pd = URLEncoder.encode(payeeDesc, "UTF-8");
            }
            int pid = -1;
            if (payeeId != -1) {
                pid = payeeId;
            }
            String tot = "";
            if (!TextUtils.isEmpty(typeOfTrans)) {
                tot = URLEncoder.encode(typeOfTrans, "UTF-8");
            }

            // TODO : what we need to do if payeeId is not given by user
            String href = "banking/icicibank/fundTransfer" + "?" + "client_id=" + clientId + "&token=" + token
                    + "&srcAccount=" + srcAccount + "&destAccount=" + destAccount + "&amt=" + amount + "&payeeDesc="
                    + pd + "&payeeId=" + pid + "&type_of_transaction=" + tot;

            Log.i(LOG_TAG, "transferring funds for href : " + href);

            Response fundTransferResponse = methods.makeTransaction(href);

            RequestCode requestCode = null;
            Gson gson = new Gson();

            if (fundTransferResponse != null) {
                InputStream responseStream = fundTransferResponse.getBody().in();
                if (responseStream != null) {
                    String responseString = IOUtils.toString(responseStream);
                    if (!TextUtils.isEmpty(responseString) && responseString.startsWith("[")
                            && responseString.endsWith("]")) {
                        responseString = responseString.substring(1, responseString.length() - 1);
                        String[] responseStringArray = responseString.split("\\},");
                        if (responseStringArray != null) {
                            // failure
                            if (responseStringArray.length == 1) {

                                requestCode = gson.fromJson(responseStringArray[0], RequestCode.class);

                            }
                            // success
                            else if (responseStringArray.length == 2) {

                                requestCode = gson.fromJson(responseStringArray[0] + "}", RequestCode.class);
                                fundTransfer = gson.fromJson(responseStringArray[1], FundTransfer.class);

                            }
                        }
                    }
                }
            }

            if (requestCode != null) {
                Log.e(LOG_TAG, "response code : " + requestCode);
            }

            if (fundTransfer != null) {
                Log.e(LOG_TAG, "fundTransfer : " + fundTransfer);
            }

        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while making transaction", e);
        }

        if (fundTransfer != null) {
            return fundTransfer.getRefNo();
        }
        return null;
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

    public void getCardAccountDetails(String cardNum)
    {
        try {

            Log.i(LOG_TAG, "Fetching card details for : " + cardNum);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String href = "rest/Card/getCardDetails/" + cardNum + "/" + clientId + "/" + token;
            Log.i(LOG_TAG, "Fetching card details for href : " + href);

            CardAccDetailsResponse cardAccDetailsResponse = methods.getCardDetails(href);

            if (cardAccDetailsResponse != null) {
                Log.i(LOG_TAG, "data : " + cardAccDetailsResponse);
                for (CardDetails cardDetails : cardAccDetailsResponse.getCardDetails()) {
                    CardAccount.insertOrUpdateCardAccount(CardAccount.getCardAccount(cardDetails));
                }

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

    public String createWallet(String firstName, String lastName, String emailId, String phNumber, Date dob, String gender)
    {
        String response = Constants.WALLET_CREATION_FAILED;

        try {

            Log.i(LOG_TAG, "Creating wallet with details : " + firstName + " " + lastName);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String merchantId = "mer_123";
            String ipAddress = "10.22.7.74";
            String os = "android";
            String imei = "34567890";
            String randomString = "xyz";

            DateFormat df = new SimpleDateFormat(Constants.WALLET_DOB_FORMAT);
            String dobString = df.format(dob);
            String href = "rest/Wallet/createWallet/" + merchantId + "/create/" + firstName + "/" + lastName + "/" + emailId +"/" +phNumber +"/" +dobString + "/" + gender+ "/" + ipAddress + "/" + os + "/" + imei + "/" + randomString + "/" + clientId + "/" + token;
            Log.i(LOG_TAG, "creating wallet with href : " + href);

            WalletCreation walletCreation = methods.createWallet(href);

            if (walletCreation != null && walletCreation.getWalletDetails() != null && walletCreation.getWalletDetails().length > 0) {
                Log.i(LOG_TAG, "data : " + walletCreation);
                WalletDetails walletDetails = walletCreation.getWalletDetails()[0];
                String authData = "";
                if(walletDetails.getCreationStatus().equals(Constants.WALLET_ALREADY_EXISTS)) {
                   Log.i(LOG_TAG,"Wallet already exists");
                    authData = walletDetails.getAuthData();
                    response = Constants.WALLET_ALREADY_EXISTS;
                }
                else if(walletDetails.getCreationStatus().equals(Constants.WALLET_CREATED_SUCCESSFULLY)) {
                    Log.i(LOG_TAG,"Wallet created");
                    authData = walletDetails.getAuthData();
                    response = Constants.WALLET_CREATED_SUCCESSFULLY;
                }

                if(response.equals(Constants.WALLET_CREATED_SUCCESSFULLY) || response.equals(Constants.WALLET_ALREADY_EXISTS)) {
                    PocketAccount pocketAccount = new PocketAccount(firstName, lastName, emailId, dob.getTime(),  phNumber , merchantId,
                            PocketAccount.Gender.valueOf(gender), authData, "");
                    SecurityUtils.savePocketsAccount(pocketAccount);

                }

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while creating wallet", e);
        }

        return response;
    }

    public double getWalletBalance(String phNumber)
    {
        double balance = 0.0;

        try {

            PocketAccount pocketAccount = SecurityUtils.getPocketsAccount();

            if(pocketAccount == null) {
                return balance;
            }

            Log.i(LOG_TAG, "getting wallet balance for " + phNumber);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String ipAddress = "10.22.7.74";
            String os = "android";
            String imei = "34567890";
            double latitude = 19.11376955;
            double longitude = 73.8500124;
            String deviceId = "7b47c06dsj12243";

            // TODO get from wherever
            String authDataForWallet = pocketAccount.getAuthToken();

            String href = "rest/Wallet/getWalletBalance";
            Log.i(LOG_TAG, "getting wallet balance with href : " + href);

            WalletBalanceBody walletBalanceBody = new WalletBalanceBody(authDataForWallet, latitude, longitude, imei,
                    os, ipAddress, deviceId, clientId, token);

            WalletBalanceResponse walletBalanceResponse = methods.getWalletBalance(href, walletBalanceBody);

            if (walletBalanceResponse != null) {
                Log.i(LOG_TAG, "wallet balance : " + walletBalanceResponse);
                balance = walletBalanceResponse.getAmount();
                pocketAccount.setBalance(balance);
                SecurityUtils.savePocketsAccount(pocketAccount);
            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while getting wallet response", e);
        }

        return balance;
    }

    public boolean creditWalletAmount(String phNumber, double amount, String promoCode, String remarks,
            String subMerchant)
    {
        boolean result = false;
        try {

            Log.i(LOG_TAG, "credit wallet balance  " + phNumber);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String ipAddress = "10.22.7.74";
            String os = "android";
            String imei = "34567890";
            double latitude = 19.11376955;
            double longitude = 73.8500124;
            String deviceId = "7b47c06dsj12243";

            PocketAccount pocketAccount = SecurityUtils.getPocketsAccount();
            String authDataForWallet = pocketAccount.getAuthToken();

            String href = "rest/Wallet/creditWalletAmount";
            Log.i(LOG_TAG, "creditting wallet balance with href : " + href);

            WalletCreditDebitBody walletCreditDebitBody = new WalletCreditDebitBody(authDataForWallet, latitude,
                    longitude, imei, os, ipAddress, deviceId, clientId, token, amount, remarks, subMerchant, 12345,
                    promoCode);

            WalletCreditDebitResponse walletCreditDebitResponse = methods.creditWallet(href, walletCreditDebitBody);

            if (walletCreditDebitResponse != null) {
                Log.i(LOG_TAG, "wallet balance : " + walletCreditDebitResponse);
                if(walletCreditDebitResponse.getErrorCode().equals("200")) {
                    result = true;
                    double newBalance = walletCreditDebitResponse.getAmount();
                    pocketAccount.setBalance(newBalance);
                    SecurityUtils.savePocketsAccount(pocketAccount);
                }
            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while crediting wallet amount", e);
        }

        return result;

    }

    public boolean debitWalletAmount(String phNumber, double amount, String promoCode, String remarks,
                                     String subMerchant)
    {
        boolean result = false;
        try {

            Log.i(LOG_TAG, "debit wallet balance  " + phNumber);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String ipAddress = "10.22.7.74";
            String os = "android";
            String imei = "34567890";
            double latitude = 19.11376955;
            double longitude = 73.8500124;
            String deviceId = "7b47c06dsj12243";

            PocketAccount pocketAccount = SecurityUtils.getPocketsAccount();
            // TODO get from wherever
            String authDataForWallet = pocketAccount.getAuthToken();

            String href = "rest/Wallet/debitWalletAmount";
            Log.i(LOG_TAG, "debit wallet balance with href : " + href);

            WalletCreditDebitBody walletCreditDebitBody = new WalletCreditDebitBody(authDataForWallet, latitude,
                    longitude, imei, os, ipAddress, deviceId, clientId, token, amount, remarks, subMerchant, 12345,
                    promoCode);

            WalletCreditDebitResponse walletCreditDebitResponse = methods.debitWallet(href, walletCreditDebitBody);

            if (walletCreditDebitResponse != null) {
                Log.i(LOG_TAG, "wallet balance : " + walletCreditDebitResponse);
                if(walletCreditDebitResponse.getErrorCode().equals("200")) {
                    Log.i(LOG_TAG, "debit successful" + walletCreditDebitResponse);
                    result = true;
                    double newBalance = walletCreditDebitResponse.getAmount();
                    pocketAccount.setBalance(newBalance);
                    SecurityUtils.savePocketsAccount(pocketAccount);

                }
            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while debiting wallet amount", e);
        }

        return result;
    }

    public void getWalletStatement(String phNumber)
    {

        try {

            Log.i(LOG_TAG, "get wallet statement for " + phNumber);
            setupRetrofitParamsForRequest(null, ALPHA_ENDPOINT);
            String clientId = "akhilcherian@gmail.com";
            String token = TOKEN;
            String ipAddress = "10.22.7.74";
            String os = "android";
            String imei = "34567890";
            double latitude = 19.11376955;
            double longitude = 73.8500124;
            String deviceId = "7b47c06dsj12243";

            PocketAccount pocketAccount = SecurityUtils.getPocketsAccount();

            // TODO get from wherever
            String authDataForWallet = pocketAccount.getAuthToken();

            String href = "rest/Wallet/getWalletStatementDetails";
            Log.i(LOG_TAG, "debit wallet balance with href : " + href);

            WalletStatementBody walletStatementBody = new WalletStatementBody(authDataForWallet, latitude, longitude,
                    imei, os, ipAddress, deviceId, clientId, token);

            Response walletStatementResponse = methods.getWalletStatement(href, walletStatementBody);

            if (walletStatementResponse != null) {

                InputStream responseStream = walletStatementResponse.getBody().in();

                if (responseStream != null) {

                    String responseString = IOUtils.toString(responseStream);

                    if (!TextUtils.isEmpty(responseString) && responseString.startsWith("[")
                            && responseString.endsWith("]")) {
                        responseString = responseString.substring(1, responseString.length() - 1);

                        int status = 500;

                        if (responseString.contains("errorCode")) {
                            int indexOfErrorCode = responseString.indexOf("errorCode");
                            if (indexOfErrorCode != -1) {
                                status = Integer.parseInt(responseString.substring(indexOfErrorCode + 12,
                                        indexOfErrorCode + 15));
                            }
                        }

                        if (status == 200) {
                            Log.i(LOG_TAG, "getting wallet statement succeeded");

                            int lastIndexOfSeparator = responseString.lastIndexOf(",{");
                            String walletStatementString = responseString.substring(0, lastIndexOfSeparator);
                            Log.i(LOG_TAG, "wallet statement string : " + walletStatementString);

                            WalletStatementResponse finalWalletStatementResponse = new Gson().fromJson(walletStatementString,
                                    WalletStatementResponse.class);

                            if (finalWalletStatementResponse != null && finalWalletStatementResponse.getWalletStatement() != null) {
                                Log.i(LOG_TAG, "walletStatement : " + finalWalletStatementResponse);
                                SecurityUtils.saveWalletStatement(Arrays.asList(finalWalletStatementResponse.getWalletStatement()));
                            }
                        }
                        else {
                            Log.i(LOG_TAG, "getting wallet statement failed");
                        }

                    }
                }

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while debiting wallet amount", e);
        }
    }


}
