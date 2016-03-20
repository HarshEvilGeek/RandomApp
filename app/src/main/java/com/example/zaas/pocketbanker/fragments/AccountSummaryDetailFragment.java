package com.example.zaas.pocketbanker.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.AccountSummaryDetailFragmentAdapter;
import com.example.zaas.pocketbanker.models.local.AccountSummaryDetailHeaderUIITem;
import com.example.zaas.pocketbanker.models.local.AccountSummaryDetailItem;
import com.example.zaas.pocketbanker.models.local.TransactionDataUIItem;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class AccountSummaryDetailFragment extends Fragment
{

    RecyclerView mAccountSummaryDetailFragmentRV;

    AccountSummaryDetailFragmentAdapter mAdapter;
    String accountNo;
    // bank acc/loan acc/card
    String headerType;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.account_summary_detail_fragment, container, false);

        mAccountSummaryDetailFragmentRV = (RecyclerView) rootView.findViewById(R.id.account_summary_detail_fragment_RV);
        getActivity().setTitle("Summary");
        populateArguments();
        loadData();
        return rootView;

    }

    private void populateArguments()
    {
        Bundle args = getArguments();
        if (args != null) {

            accountNo = args.getString(Constants.SUMMARY_DETAIL_FRAGMENT_ACCOUNT_NUMBER);
            headerType = args.getString(Constants.SUMMARY_DETAIL_FRAGMENT_HEADER_TYPE);

        }
    }

    private void loadData()
    {
        new DataLoadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }

    public class DataLoadTask extends AsyncTask<Void, Void, List<AccountSummaryDetailItem>>
    {

        @Override
        protected List<AccountSummaryDetailItem> doInBackground(Void... voids)
        {

            List<AccountSummaryDetailItem> uiItems = new ArrayList<>();

            AccountSummaryDetailHeaderUIITem bankAccountHeader = new AccountSummaryDetailHeaderUIITem();
            bankAccountHeader.setHeaderDataForBankAccount(accountNo, 5000000.00,
                    Constants.ACCOUNT_TYPE_SAVINGS, System.currentTimeMillis(), headerType);

            TransactionDataUIItem transaction1 = new TransactionDataUIItem(10000.00, "transferred for saving",
                    System.currentTimeMillis(), Constants.TRANSACTION_TYPE_DEBIT);
            TransactionDataUIItem transaction2 = new TransactionDataUIItem(5000.00, null,
                    System.currentTimeMillis() + 345670000, Constants.TRANSACTION_TYPE_CREDIT);
            TransactionDataUIItem transaction3 = new TransactionDataUIItem(10000.00, "transferred for blah",
                    System.currentTimeMillis(), Constants.TRANSACTION_TYPE_DEBIT);
            TransactionDataUIItem transaction4 = new TransactionDataUIItem(10000.00, "transferred for xyz",
                    System.currentTimeMillis(), Constants.TRANSACTION_TYPE_DEBIT);
            TransactionDataUIItem transaction5 = new TransactionDataUIItem(10000.00, "transferred for fd",
                    System.currentTimeMillis(), Constants.TRANSACTION_TYPE_CREDIT);
            TransactionDataUIItem transaction6 = new TransactionDataUIItem(10000.00, "transferred for fd",
                    System.currentTimeMillis(), Constants.TRANSACTION_TYPE_DEBIT);
            TransactionDataUIItem transaction7 = new TransactionDataUIItem(10000.00, "transferred for fd",
                    System.currentTimeMillis(), Constants.TRANSACTION_TYPE_CREDIT);

            AccountSummaryDetailItem uiItem1 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_HEADER,
                    bankAccountHeader, null);
            AccountSummaryDetailItem uiItem2 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM, null,
                    transaction1);
            AccountSummaryDetailItem uiItem3 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM, null,
                    transaction2);
            AccountSummaryDetailItem uiItem4 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM, null,
                    transaction3);
            AccountSummaryDetailItem uiItem5 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM, null,
                    transaction4);
            AccountSummaryDetailItem uiItem6 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM, null,
                    transaction5);

            AccountSummaryDetailItem uiItem7 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM, null,
                    transaction6);
            AccountSummaryDetailItem uiItem8 = new AccountSummaryDetailItem(Constants.SUMMARY_ITEM_TYPE_ITEM, null,
                    transaction7);

            uiItems.add(uiItem1);
            uiItems.add(uiItem2);
            uiItems.add(uiItem3);
            uiItems.add(uiItem4);
            uiItems.add(uiItem5);
            uiItems.add(uiItem6);
            uiItems.add(uiItem7);
            uiItems.add(uiItem8);

            return uiItems;
        }

        @Override
        protected void onPostExecute(List<AccountSummaryDetailItem> uiItems)
        {
            if (mAdapter == null) {
                mAdapter = new AccountSummaryDetailFragmentAdapter(getActivity(), uiItems);
                mAccountSummaryDetailFragmentRV.setAdapter(mAdapter);
                mAccountSummaryDetailFragmentRV.setLayoutManager(new LinearLayoutManager(
                        mAccountSummaryDetailFragmentRV.getContext()));
                // mAccountSummaryDetailFragmentRV.setScrollBarStyle(View.SCROLL_AXIS_VERTICAL);

            }
            else {
                mAdapter.setUiItems(uiItems);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
