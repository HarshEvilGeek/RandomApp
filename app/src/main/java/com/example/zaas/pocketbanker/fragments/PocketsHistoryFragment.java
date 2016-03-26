package com.example.zaas.pocketbanker.fragments;

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
import com.example.zaas.pocketbanker.adapters.TransactionsListFragmentAdapter;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionDataUIItem;
import com.example.zaas.pocketbanker.models.network.WalletStatement;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;
import com.example.zaas.pocketbanker.utils.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 3/26/16.
 */
public class PocketsHistoryFragment extends Fragment {

    RecyclerView mTransactionListFragmentRV;

    TransactionsListFragmentAdapter mAdapter;
    SwipeRefreshLayout mTransactionsListSwipeRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pockets_history, container, false);

        mTransactionListFragmentRV = (RecyclerView) rootView.findViewById(R.id.transaction_list_fragment_RV);
        mTransactionsListSwipeRefresh = (SwipeRefreshLayout) rootView
                .findViewById(R.id.transactions_list_swipe_refresh);
        mTransactionsListSwipeRefresh.setColorSchemeColors(Color.BLUE);

        getActivity().setTitle("Pockets - History ");
        loadData(true);
        return rootView;

    }

    private void disablePullToRefresh() {
        if (mTransactionsListSwipeRefresh != null) {
            mTransactionsListSwipeRefresh.setEnabled(false);
        }
    }

    private void enablePullToRefresh() {
        if (mTransactionsListSwipeRefresh != null) {
            mTransactionsListSwipeRefresh.setEnabled(true);
        }
    }

    private void stopProgressInPullToRefresh() {
        if (mTransactionsListSwipeRefresh != null) {
            mTransactionsListSwipeRefresh.setRefreshing(false);
        }
    }

    private void startProgressInPullToRefresh() {
        if (mTransactionsListSwipeRefresh != null) {
            mTransactionsListSwipeRefresh.setRefreshing(true);
        }
    }

    private void fetchTransactions() {
        new NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }

    private void loadData(boolean doNetworkCall) {
        new DataLoadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }

    public class DataLoadTask extends AsyncTask<Void, Void, List<TransactionDataUIItem>> {

        @Override
        protected List<TransactionDataUIItem> doInBackground(Void... voids) {

            List<WalletStatement> walletStatementList = SecurityUtils.getWalletStatement();
            List<TransactionDataUIItem> uiItems = new ArrayList<>();

            if(walletStatementList != null && walletStatementList.size() > 0) {
                for (WalletStatement walletStatement : walletStatementList) {
                    uiItems.add(new TransactionDataUIItem(walletStatement.getAmount(),
                            walletStatement.getRemarks(),
                            walletStatement.getTransactionDate(),
                            walletStatement.getTransactionType().name()));
                }
            }

            // TODO ZARA DELETE THIS
            /**TransactionDataUIItem transaction1 = new TransactionDataUIItem(10000.00,
                    "Transferred to other account", System.currentTimeMillis(), Transaction.Type.Debit.name());
            TransactionDataUIItem transaction2 = new TransactionDataUIItem(5000.00, null,
                    System.currentTimeMillis() + 345670000, Transaction.Type.Credit.name());
            TransactionDataUIItem transaction3 = new TransactionDataUIItem(10000.00, "Refund from Merchant",
                    System.currentTimeMillis(), Transaction.Type.Debit.name());
            TransactionDataUIItem transaction4 = new TransactionDataUIItem(10000.00,
                    "Transferred to other account", System.currentTimeMillis(), Transaction.Type.Debit.name());
            TransactionDataUIItem transaction5 = new TransactionDataUIItem(10000.00, "Paid Loan EMI",
                    System.currentTimeMillis(), Transaction.Type.Credit.name());
            TransactionDataUIItem transaction6 = new TransactionDataUIItem(10000.00, "Added amount",
                    System.currentTimeMillis(), Transaction.Type.Debit.name());
            TransactionDataUIItem transaction7 = new TransactionDataUIItem(10000.00, "Transferred for FD",
                    System.currentTimeMillis(), Transaction.Type.Credit.name());

            uiItems.add(transaction1);
            uiItems.add(transaction2);
            uiItems.add(transaction3);
            uiItems.add(transaction4);
            uiItems.add(transaction5);
            uiItems.add(transaction6);
            uiItems.add(transaction7);
            uiItems.add(transaction7);
            uiItems.add(transaction1);
            uiItems.add(transaction3);
            uiItems.add(transaction4);**/

            return uiItems;
        }

        @Override
        protected void onPostExecute(List<TransactionDataUIItem> uiItems) {
            if (mAdapter == null) {
                mAdapter = new TransactionsListFragmentAdapter(getActivity(), uiItems);
                mTransactionListFragmentRV.setAdapter(mAdapter);
                mTransactionListFragmentRV.setLayoutManager(new LinearLayoutManager(mTransactionListFragmentRV
                        .getContext()));

            } else {
                mAdapter.setUiItems(uiItems);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startProgressInPullToRefresh();
            disablePullToRefresh();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            NetworkHelper networkHelper = new NetworkHelper();

            networkHelper.getWalletStatement(SecurityUtils.getPocketsAccount().getPhoneNumber());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loadData(false);
            stopProgressInPullToRefresh();
            enablePullToRefresh();
        }
    }
}
