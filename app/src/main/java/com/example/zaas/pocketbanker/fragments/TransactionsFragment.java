package com.example.zaas.pocketbanker.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.TransactionsSummaryFragmentAdapter;
import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.models.local.LoanEMI;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionSummaryUIItem;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaas on 3/17/16.
 */
public class TransactionsFragment extends Fragment
{

    RecyclerView mTransactionsRV;
    TransactionsSummaryFragmentAdapter mAdapter;
    SwipeRefreshLayout mTransactionsSummarySwipeRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_transactions, container, false);
        mTransactionsRV = (RecyclerView) rootView.findViewById(R.id.account_trabsactions_RV);
        mTransactionsSummarySwipeRefresh = (SwipeRefreshLayout) rootView
                .findViewById(R.id.transactions_summary_swipe_refresh);
        mTransactionsSummarySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                fetchNetworkData();
            }
        });
        mTransactionsSummarySwipeRefresh.setColorSchemeColors(Color.BLUE);

        getActivity().setTitle("Transactions");
        loadData(false);
        return rootView;
    }

    private void fetchNetworkData()
    {
        new NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

    }

    private void loadData(boolean doNetworkCallAfter)
    {
        new DataLoadTask(doNetworkCallAfter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

    }

    private void disablePullToRefresh()
    {
        if (mTransactionsSummarySwipeRefresh != null) {
            mTransactionsSummarySwipeRefresh.setEnabled(false);
        }
    }

    private void enablePullToRefresh()
    {
        if (mTransactionsSummarySwipeRefresh != null) {
            mTransactionsSummarySwipeRefresh.setEnabled(true);
        }
    }

    private void stopProgressInPullToRefresh()
    {
        if (mTransactionsSummarySwipeRefresh != null) {
            mTransactionsSummarySwipeRefresh.setRefreshing(false);
        }
    }

    private void startProgressInPullToRefresh()
    {
        if (mTransactionsSummarySwipeRefresh != null) {
            mTransactionsSummarySwipeRefresh.setRefreshing(true);
        }
    }

    public class DataLoadTask extends AsyncTask<Void, Void, List<TransactionSummaryUIItem>>
    {
        private boolean doNetworkCall;

        public DataLoadTask(boolean doNetworkCall)
        {
            this.doNetworkCall = doNetworkCall;
        }

        @Override
        protected List<TransactionSummaryUIItem> doInBackground(Void... voids)
        {
            List<TransactionSummaryUIItem> uiItems = new ArrayList<>();

            // List<Account> bankAccounts = PocketBankerDBHelper.getInstance().getAllAccounts();

            // if (bankAccounts != null && !bankAccounts.isEmpty()) {

            // for (Account bankAccount : bankAccounts) {

            List<Transaction> latestTransactions = Transaction.getLatestNTransactions(1, Constants.BANK_ACCOUNT_NUMBER,
                    PocketBankerContract.Transactions.TIME + " DESC ");

            if (latestTransactions != null && latestTransactions.size() > 0) {
                TransactionSummaryUIItem bankHeaderItem = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER,
                        "Bank Account", -1, -1, null, null);

                uiItems.add(bankHeaderItem);

                Transaction latestTransaction = latestTransactions.get(0);
                TransactionSummaryUIItem bankAccountTransSummary = new TransactionSummaryUIItem(
                        Constants.SUMMARY_ITEM_TYPE_ITEM, Constants.BANK_ACCOUNT_NUMBER, latestTransaction.getAmount(),
                        latestTransaction.getTime(), latestTransaction.getType().name(),
                        Constants.HEADER_TYPE_BANKACCOUNT);
                uiItems.add(bankAccountTransSummary);
            }
            // }
            // }

            // List<LoanAccount> loanAccounts = LoanAccount.getAllLoanAccounts();
            // if (loanAccounts != null && !loanAccounts.isEmpty()) {

            // for (LoanAccount loanAccount : loanAccounts) {

            List<LoanEMI> latestEmis = LoanEMI.getLatestNEmis(1, Constants.LOAN_ACC_NUMBER,
                    PocketBankerContract.Emis.EMI_DATE + " DESC ");

            if (latestEmis != null && latestEmis.size() > 0) {

                TransactionSummaryUIItem loanAccountHeader = new TransactionSummaryUIItem(
                        Constants.SUMMARY_ITEM_TYPE_HEADER, "Loan Account", -1, -1, null, null);
                uiItems.add(loanAccountHeader);

                LoanEMI latestEmi = latestEmis.get(0);
                TransactionSummaryUIItem loanTransactionSummary = new TransactionSummaryUIItem(
                        Constants.SUMMARY_ITEM_TYPE_ITEM, Constants.LOAN_ACC_NUMBER, latestEmi.getEmiAmount(),
                        latestEmi.getEmiDate(), Transaction.Type.Credit.name(), Constants.HEADER_TYPE_LOANACCOUNT);
                uiItems.add(loanTransactionSummary);
            }

            // }
            // }

            // List<CardAccount> cardAccounts = CardAccount.getAllCardAccounts();
            // if (cardAccounts != null && !cardAccounts.isEmpty()) {
            TransactionSummaryUIItem cardHeader = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER,
                    "Card", -1, -1, null, null);
            uiItems.add(cardHeader);

            // for (CardAccount cardAccount : cardAccounts) {
            TransactionSummaryUIItem cardTransactionSummary = new TransactionSummaryUIItem(
                    Constants.SUMMARY_ITEM_TYPE_ITEM, Constants.CARD_ACCOUNT_NUMBER, 100, System.currentTimeMillis(),
                    Transaction.Type.Debit.name(), Constants.HEADER_TYPE_CARD);
            uiItems.add(cardTransactionSummary);
            // }
            // }
            return uiItems;
        }

        @Override
        protected void onPostExecute(List<TransactionSummaryUIItem> uiItems)
        {
            if (mAdapter == null) {
                mAdapter = new TransactionsSummaryFragmentAdapter(getActivity(), uiItems);
                mTransactionsRV.setAdapter(mAdapter);
                mTransactionsRV.setLayoutManager(new LinearLayoutManager(mTransactionsRV.getContext()));

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

            // List<Account> bankAccounts = PocketBankerDBHelper.getInstance().getAllAccounts();
            // if (bankAccounts != null && bankAccounts.size() > 0) {

            // for (Account bankAccount : bankAccounts) {
            // TODO : get from wherever
            networkHelper.fetchTransactionHistoryForPeriod(Constants.BANK_ACCOUNT_NUMBER,
                    new Date((System.currentTimeMillis() - Constants.ONE_MONTH_IN_MILLIS)),
                    new Date(System.currentTimeMillis()));
            // }

            // }

            // List<LoanAccount> loanAccounts = LoanAccount.getAllLoanAccounts();
            // if (loanAccounts != null && loanAccounts.size() > 0) {

            // for (LoanAccount loanAccount : loanAccounts) {
            // TODO : get from wherever
            networkHelper.getLoanEMIDetails(Constants.LOAN_ACC_NUMBER);
            // }

            // }

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

}
