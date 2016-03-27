package com.example.zaas.pocketbanker.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.AccountSummaryDetailFragmentAdapter;
import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.AccountSummaryDetailHeaderUIITem;
import com.example.zaas.pocketbanker.models.local.AccountSummaryDetailItem;
import com.example.zaas.pocketbanker.models.local.CardAccount;
import com.example.zaas.pocketbanker.models.local.LoanAccount;
import com.example.zaas.pocketbanker.models.local.LoanEMI;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionDataUIItem;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class AccountSummaryDetailActivity extends AppCompatActivity
{

    RecyclerView mAccountSummaryDetailFragmentRV;

    AccountSummaryDetailFragmentAdapter mAdapter;
    String accountNo;
    // bank acc/loan acc/card
    String headerType;
    SwipeRefreshLayout mAccountSummaryDetailSwipeRefresh;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Details");
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.account_summary_detail_fragment);

        mAccountSummaryDetailFragmentRV = (RecyclerView) findViewById(R.id.account_summary_detail_fragment_RV);
        mAccountSummaryDetailSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.account_summary_detail_swipe_refresh);
        mAccountSummaryDetailSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNetworkData();
            }
        });
        mAccountSummaryDetailSwipeRefresh.setColorSchemeColors(Color.BLUE);

        setTitle("Summary");
        Bundle extras = getIntent().getExtras();
        handleExtras(extras);
        loadData(true);
    }

    private void handleExtras(Bundle extras) {

        accountNo = extras.getString(Constants.SUMMARY_DETAIL_FRAGMENT_ACCOUNT_NUMBER);
        headerType = extras.getString(Constants.SUMMARY_DETAIL_FRAGMENT_HEADER_TYPE);
    }

    private void fetchNetworkData()
    {
        new NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

    }

    private void disablePullToRefresh()
    {
        if (mAccountSummaryDetailSwipeRefresh != null) {
            mAccountSummaryDetailSwipeRefresh.setEnabled(false);
        }
    }

    private void enablePullToRefresh()
    {
        if (mAccountSummaryDetailSwipeRefresh != null) {
            mAccountSummaryDetailSwipeRefresh.setEnabled(true);
        }
    }

    private void stopProgressInPullToRefresh()
    {
        if (mAccountSummaryDetailSwipeRefresh != null) {
            mAccountSummaryDetailSwipeRefresh.setRefreshing(false);
        }
    }

    private void startProgressInPullToRefresh()
    {
        if (mAccountSummaryDetailSwipeRefresh != null) {
            mAccountSummaryDetailSwipeRefresh.setRefreshing(true);
        }
    }

    private void loadData(boolean doNetworkCallAfter)
    {
        new DataLoadTask(doNetworkCallAfter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

    }

    public class DataLoadTask extends AsyncTask<Void, Void, List<AccountSummaryDetailItem>>
    {

        private boolean doNetworkCall;

        public DataLoadTask(boolean doNetworkCall)
        {
            this.doNetworkCall = doNetworkCall;
        }

        @Override
        protected List<AccountSummaryDetailItem> doInBackground(Void... voids)
        {
            List<AccountSummaryDetailItem> uiItems = new ArrayList<>();

            try {

                AccountSummaryDetailHeaderUIITem accountHeader = new AccountSummaryDetailHeaderUIITem();

                if (headerType.equals(Constants.HEADER_TYPE_BANKACCOUNT)) {
                    Account bankAccount = Account.getAccount(accountNo);
                    accountHeader.setHeaderDataForBankAccount(accountNo, bankAccount.getBalance(),
                            bankAccount.getType(), bankAccount.getLastUpdateTime(), headerType);

                    AccountSummaryDetailItem headerItem = new AccountSummaryDetailItem(
                            Constants.SUMMARY_ITEM_TYPE_HEADER, accountHeader, null);

                    uiItems.add(headerItem);

                    List<Transaction> accountTransactions = Transaction.getLatestNTransactions(5, accountNo,
                            PocketBankerContract.Transactions.TIME + " DESC ");
                    if (accountTransactions != null && accountTransactions.size() > 0) {
                        for (Transaction transaction : accountTransactions) {
                            TransactionDataUIItem transactionUiItem = new TransactionDataUIItem(
                                    transaction.getAmount(), transaction.getRemark(), transaction.getTime(),
                                    transaction.getType().name());
                            AccountSummaryDetailItem transactionItem = new AccountSummaryDetailItem(
                                    Constants.SUMMARY_ITEM_TYPE_ITEM, null, transactionUiItem);
                            uiItems.add(transactionItem);

                        }
                    }

                }
                else if (headerType.equals(Constants.HEADER_TYPE_LOANACCOUNT)) {
                    LoanAccount loanAccount = LoanAccount.getLoanAccount(accountNo);
                    DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                    accountHeader.setHeaderDataForLoanAccount(accountNo, loanAccount.getCustomerName(),
                            loanAccount.getOutstandingPrinciple(), loanAccount.getType(),
                            df.parse(loanAccount.getDateOfLoan()).getTime(), loanAccount.getRoi(), headerType, loanAccount.getMonthDelinquency());

                    AccountSummaryDetailItem headerItem = new AccountSummaryDetailItem(
                            Constants.SUMMARY_ITEM_TYPE_HEADER, accountHeader, null);

                    uiItems.add(headerItem);

                    List<LoanEMI> loanEmis = LoanEMI.getLatestNEmis(5, accountNo, PocketBankerContract.Emis.EMI_DATE
                            + " DESC ");
                    if (loanEmis != null && loanEmis.size() > 0) {
                        for (LoanEMI loanEmi : loanEmis) {
                            TransactionDataUIItem transactionUiItem = new TransactionDataUIItem(loanEmi.getEmiAmount(),
                                    "Paid EMI", loanEmi.getEmiDate(), Transaction.Type.Credit.name());
                            AccountSummaryDetailItem transactionItem = new AccountSummaryDetailItem(
                                    Constants.SUMMARY_ITEM_TYPE_ITEM, null, transactionUiItem);
                            uiItems.add(transactionItem);

                        }
                    }

                }
               else if (headerType.equals(Constants.HEADER_TYPE_CARD)) {

                    CardAccount cardAccount = CardAccount.getCardAccount(accountNo);

                    DateFormat df = new SimpleDateFormat(CardAccount.CARD_DETAILS_DATE_FORMAT);
                    accountHeader.setHeaderDataForCard(accountNo, cardAccount.getAvailLimit(), cardAccount.getBalance(), df.parse(cardAccount.getExpiryDate()).getTime(), cardAccount.getType(), cardAccount.getStatus(), headerType);

                    AccountSummaryDetailItem headerItem = new AccountSummaryDetailItem(
                            Constants.SUMMARY_ITEM_TYPE_HEADER, accountHeader, null);

                    uiItems.add(headerItem);

                    TransactionDataUIItem transaction1 = new TransactionDataUIItem(500.00, "Paid phone bill",
                            System.currentTimeMillis(), Transaction.Type.Debit.name());
                    TransactionDataUIItem transaction2 = new TransactionDataUIItem(1245.00, "Groceries purchase",
                            System.currentTimeMillis() - Constants.ONE_DAY_IN_MILLIS, Transaction.Type.Debit.name());
                    TransactionDataUIItem transaction3 = new TransactionDataUIItem(10000.00, null,
                            System.currentTimeMillis() - Constants.ONE_DAY_IN_MILLIS, Transaction.Type.Credit.name());
                    TransactionDataUIItem transaction4 = new TransactionDataUIItem(200.00, null,
                            System.currentTimeMillis() - (2 * Constants.ONE_DAY_IN_MILLIS),
                            Transaction.Type.Debit.name());
                    TransactionDataUIItem transaction5 = new TransactionDataUIItem(5000.00, "Refund from merchant",
                            System.currentTimeMillis() - (3 * Constants.ONE_DAY_IN_MILLIS),
                            Transaction.Type.Credit.name());

                    AccountSummaryDetailItem uiItem2 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM,
                            null, transaction1);
                    AccountSummaryDetailItem uiItem3 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM,
                            null, transaction2);
                    AccountSummaryDetailItem uiItem4 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM,
                            null, transaction3);
                    AccountSummaryDetailItem uiItem5 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM,
                            null, transaction4);
                    AccountSummaryDetailItem uiItem6 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM,
                            null, transaction5);

                    uiItems.add(uiItem2);
                    uiItems.add(uiItem3);
                    uiItems.add(uiItem4);
                    uiItems.add(uiItem5);
                    uiItems.add(uiItem6);

                }
            }
            catch (Exception e) {
                Log.e("abc", "Exception : ", e);
            }

            return uiItems;
        }

        @Override
        protected void onPostExecute(List<AccountSummaryDetailItem> uiItems)
        {
            if (mAdapter == null) {
                mAdapter = new AccountSummaryDetailFragmentAdapter(mContext, uiItems);
                mAccountSummaryDetailFragmentRV.setAdapter(mAdapter);
                mAccountSummaryDetailFragmentRV.setLayoutManager(new LinearLayoutManager(
                        mAccountSummaryDetailFragmentRV.getContext()));

            }
            else {
                mAdapter.setUiItems(uiItems);
                mAdapter.notifyDataSetChanged();
            }

            if (doNetworkCall) {
                fetchNetworkData();
            }
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            startProgressInPullToRefresh();
            disablePullToRefresh();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            NetworkHelper networkHelper = new NetworkHelper();
            if (headerType.equals(Constants.HEADER_TYPE_BANKACCOUNT)) {
                networkHelper.fetchTransactionHistoryForPeriod(accountNo, new Date(
                        (System.currentTimeMillis() - Constants.ONE_MONTH_IN_MILLIS)),
                        new Date(System.currentTimeMillis()));
            }
            else if (headerType.equals(Constants.HEADER_TYPE_LOANACCOUNT)) {
                networkHelper.getLoanEMIDetails(accountNo);
            }
            else if (headerType.equals(Constants.HEADER_TYPE_CARD)) {
                // No api
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            loadData(false);
            stopProgressInPullToRefresh();
            enablePullToRefresh();
        }
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
