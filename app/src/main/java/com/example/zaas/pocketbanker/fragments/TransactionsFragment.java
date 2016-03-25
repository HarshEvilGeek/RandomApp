package com.example.zaas.pocketbanker.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.AccountSummaryFragmentAdapter;
import com.example.zaas.pocketbanker.adapters.TransactionsSummaryFragmentAdapter;
import com.example.zaas.pocketbanker.models.local.SummaryUIItem;
import com.example.zaas.pocketbanker.models.local.TransactionSummaryUIItem;
import com.example.zaas.pocketbanker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaas on 3/17/16.
 */
public class TransactionsFragment extends Fragment {

    ListView mTransactionsLV;
    TransactionsSummaryFragmentAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transactions, container,false);
        mTransactionsLV = (ListView) rootView.findViewById(R.id.account_transactions_LV);
        getActivity().setTitle("Transactions");
        loadData();
        return rootView;
    }

    private void loadData()
    {
        new DataLoadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

    }

    public class DataLoadTask extends AsyncTask<Void, Void, List<TransactionSummaryUIItem>>
    {

        @Override
        protected List<TransactionSummaryUIItem> doInBackground(Void... voids)
        {
            TransactionSummaryUIItem uiItem1 = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER,"Bank Account",-1, -1, null, null);
            TransactionSummaryUIItem uiItem2 = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM,"0000012243547123",10000.00, System.currentTimeMillis(), Constants.TRANSACTION_TYPE_CREDIT, Constants.HEADER_TYPE_BANKACCOUNT);
            TransactionSummaryUIItem uiItem3 = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM,"0000012246547123",5000.00, (System.currentTimeMillis() + 10000000), Constants.TRANSACTION_TYPE_DEBIT, Constants.HEADER_TYPE_BANKACCOUNT);
            TransactionSummaryUIItem uiItem4 = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER,"Card",-1, -1, null, null);
            TransactionSummaryUIItem uiItem5 = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM,"0000012143547234",356.98, System.currentTimeMillis(), Constants.TRANSACTION_TYPE_DEBIT, Constants.HEADER_TYPE_CARD);
            TransactionSummaryUIItem uiItem6 = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM,"0020012243547456",2000.00, System.currentTimeMillis(), Constants.TRANSACTION_TYPE_DEBIT, Constants.HEADER_TYPE_CARD);
            TransactionSummaryUIItem uiItem7 = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER,"Loan Account",-1, -1, null, null);
            TransactionSummaryUIItem uiItem8 = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM,"0030012243547678",10000.00, System.currentTimeMillis(), Constants.TRANSACTION_TYPE_CREDIT, Constants.HEADER_TYPE_LOANACCOUNT);
            TransactionSummaryUIItem uiItem9 = new TransactionSummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM,"0007012243547789",10000.00, System.currentTimeMillis() + 10000000, Constants.TRANSACTION_TYPE_CREDIT, Constants.HEADER_TYPE_LOANACCOUNT);

            List<TransactionSummaryUIItem> uiItems = new ArrayList<>();
            uiItems.add(uiItem1);
            uiItems.add(uiItem2);
            uiItems.add(uiItem3);
            uiItems.add(uiItem4);
            uiItems.add(uiItem5);
            uiItems.add(uiItem6);
            uiItems.add(uiItem7);
            uiItems.add(uiItem8);
            uiItems.add(uiItem9);

            return uiItems;
        }

        @Override
        protected void onPostExecute(List<TransactionSummaryUIItem> uiItems)
        {
            if (mAdapter == null) {
                mAdapter = new TransactionsSummaryFragmentAdapter(getActivity(), -1, uiItems);
                mTransactionsLV.setAdapter(mAdapter);
            } else {
                mAdapter.setUiItems(uiItems);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

}
