package com.example.zaas.pocketbanker.fragments;

import java.net.URLEncoder;
import java.util.ArrayList;
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
import com.example.zaas.pocketbanker.adapters.AccountSummaryFragmentAdapter;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.CardAccount;
import com.example.zaas.pocketbanker.models.local.LoanAccount;
import com.example.zaas.pocketbanker.models.local.SummaryUIItem;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaas on 3/17/16.
 */
public class AccountSummaryFragment extends Fragment
{

    RecyclerView mAccountSummaryRV;
    AccountSummaryFragmentAdapter mAdapter;
    SwipeRefreshLayout mAccountSummarySwipeRefresh;

    private static String LOG_TAG = AccountSummaryFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_account_summary, container, false);
        mAccountSummaryRV = (RecyclerView) rootView.findViewById(R.id.account_summary_RV);
        mAccountSummarySwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.account_summary_swipe_refresh);
        mAccountSummarySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                fetchNetworkData();
            }
        });
        mAccountSummarySwipeRefresh.setColorSchemeColors(Color.BLUE);
        getActivity().setTitle("Summary");
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
        if (mAccountSummarySwipeRefresh != null) {
            mAccountSummarySwipeRefresh.setEnabled(false);
        }
    }

    private void enablePullToRefresh()
    {
        if (mAccountSummarySwipeRefresh != null) {
            mAccountSummarySwipeRefresh.setEnabled(true);
        }
    }

    private void stopProgressInPullToRefresh()
    {
        if (mAccountSummarySwipeRefresh != null) {
            mAccountSummarySwipeRefresh.setRefreshing(false);
        }
    }

    private void startProgressInPullToRefresh()
    {
        if (mAccountSummarySwipeRefresh != null) {
            mAccountSummarySwipeRefresh.setRefreshing(true);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    private void testAccountSummary()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                NetworkHelper nh = new NetworkHelper();
                // new NetworkHelper().fetchAccountBalance("5555666677770949");
                // nh.fetchAccountSummary("5555666677770949", "88881949");
                // nh.fetchAccountSummary("5555666677770949", null);
                // nh.fetchAccountSummary(null, "88881949");
                // nh.fetchTransactionHistoryForDays("5555666677770949", 5);
                // nh.fetchTransactionHistoryForPeriod("5555666677770949",new Date(System.currentTimeMillis() - (24 * 60
                // * 60 * 1000)), new Date(System.currentTimeMillis()));
                // nh.fetchBehaviorScore("5555666677770949");

                // nh.getRegisteredPayees("88881949");

                try {
                    String testDesc = URLEncoder.encode("test description", "UTF-8");
                    nh.transferFunds("88881949", "5555666677770950", "5555666677770949", 10000, testDesc, 1, "PMR");
                    // nh.getBranchAtmLocations("ATM", 72.9376984, 19.1445007);
                    nh.getLoanAccountSummary("88881949");
                    nh.getLoanEMIDetails("LBMUM11112220949");
                    nh.getLoanTransactionDetails("LBMUM11112220949");
                    // nh.getCardAccountDetails("88881949");
                }
                catch (Exception e) {

                }
            }
        }).start();
    }

    public class DataLoadTask extends AsyncTask<Void, Void, List<SummaryUIItem>>
    {

        private boolean doNetworkCall;

        public DataLoadTask(boolean doNetworkCall)
        {
            this.doNetworkCall = doNetworkCall;
        }

        @Override
        protected List<SummaryUIItem> doInBackground(Void... voids)
        {
            PocketBankerDBHelper dbHelper = PocketBankerDBHelper.getInstance();
            List<SummaryUIItem> uiItems = new ArrayList<>();
            List<Account> bankAccounts = dbHelper.getAllAccounts();

            if (bankAccounts != null && !bankAccounts.isEmpty()) {
                SummaryUIItem header1 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER, "Bank Account", -1, null);

                uiItems.add(header1);
                for (Account bankAccount : bankAccounts) {
                    SummaryUIItem bankAccountUIItem = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM,
                            bankAccount.getAccountNumber(), bankAccount.getBalance(), Constants.HEADER_TYPE_BANKACCOUNT);
                    uiItems.add(bankAccountUIItem);
                }
            }

            List<LoanAccount> loanAccounts = LoanAccount.getAllLoanAccounts();
            if (loanAccounts != null && !loanAccounts.isEmpty()) {
                SummaryUIItem header2 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER, "Loan Account", -1, null);
                uiItems.add(header2);
                for (LoanAccount loanAccount : loanAccounts) {
                    SummaryUIItem loanAccountUIItem = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM,
                            loanAccount.getLoanAccountNo(), loanAccount.getOutstandingPrinciple(),
                            Constants.HEADER_TYPE_LOANACCOUNT);
                    uiItems.add(loanAccountUIItem);
                }
            }

            List<CardAccount> cardAccounts = CardAccount.getAllCardAccounts();
            if (cardAccounts != null && !cardAccounts.isEmpty()) {
                SummaryUIItem header3 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER, "Card", -1, null);
                uiItems.add(header3);
                for (CardAccount cardAccount : cardAccounts) {
                    SummaryUIItem cardAccountUIItem = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM,
                            cardAccount.getCardAccNumber(), cardAccount.getBalance(), Constants.HEADER_TYPE_CARD);
                    uiItems.add(cardAccountUIItem);
                }
            }

            return uiItems;
        }

        @Override
        protected void onPostExecute(List<SummaryUIItem> uiItems)
        {

            if (mAdapter == null) {
                mAdapter = new AccountSummaryFragmentAdapter(getActivity(), uiItems);
                mAccountSummaryRV.setAdapter(mAdapter);
                mAccountSummaryRV.setLayoutManager(new LinearLayoutManager(mAccountSummaryRV.getContext()));

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

            networkHelper.fetchAccountSummary(Constants.BANK_ACCOUNT_NUMBER, null);

            networkHelper.getLoanAccountSummary(Constants.LOAN_ACC_NUMBER);

            networkHelper.getCardAccountDetails(Constants.CARD_ACCOUNT_NUMBER);

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
