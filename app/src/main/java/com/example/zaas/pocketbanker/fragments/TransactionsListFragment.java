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
import com.example.zaas.pocketbanker.adapters.TransactionsListFragmentAdapter;
import com.example.zaas.pocketbanker.models.local.TransactionDataUIItem;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class TransactionsListFragment extends Fragment
{

    RecyclerView mTransactionListFragmentRV;

    TransactionsListFragmentAdapter mAdapter;
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

        View rootView = inflater.inflate(R.layout.transaction_list_fragment_layout, container, false);

        mTransactionListFragmentRV = (RecyclerView) rootView.findViewById(R.id.transaction_list_fragment_RV);
        populateArguments();
        getActivity().setTitle("Transactions for " + accountNo);
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

    public class DataLoadTask extends AsyncTask<Void, Void, List<TransactionDataUIItem>>
    {

        @Override
        protected List<TransactionDataUIItem> doInBackground(Void... voids)
        {

            List<TransactionDataUIItem> uiItems = new ArrayList<>();

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
            uiItems.add(transaction4);

            return uiItems;
        }

        @Override
        protected void onPostExecute(List<TransactionDataUIItem> uiItems)
        {
            if (mAdapter == null) {
                mAdapter = new TransactionsListFragmentAdapter(getActivity(), uiItems);
                mTransactionListFragmentRV.setAdapter(mAdapter);
                mTransactionListFragmentRV.setLayoutManager(new LinearLayoutManager(mTransactionListFragmentRV
                        .getContext()));
                // mAccountSummaryDetailFragmentRV.setScrollBarStyle(View.SCROLL_AXIS_VERTICAL);

            }
            else {
                mAdapter.setUiItems(uiItems);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

}
