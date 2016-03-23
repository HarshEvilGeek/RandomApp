package com.example.zaas.pocketbanker.fragments;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.example.zaas.pocketbanker.models.local.SummaryUIItem;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaas on 3/17/16.
 */
public class AccountSummaryFragment extends Fragment
{

    ListView mAccountSummaryLV;
    AccountSummaryFragmentAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        testAccountSummary();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_account_summary, container, false);
        mAccountSummaryLV = (ListView) rootView.findViewById(R.id.account_summary_LV);
        getActivity().setTitle("Summary");
        loadData();
        return rootView;
    }

    private void loadData()
    {
        new DataLoadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

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
                //nh.fetchTransactionHistoryForDays("5555666677770949", 5);
                //nh.fetchTransactionHistoryForPeriod("5555666677770949",new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000)), new Date(System.currentTimeMillis()));
                // nh.fetchBehaviorScore("5555666677770949");

                // nh.getRegisteredPayees("88881949");

                try {
                    String testDesc = URLEncoder.encode("test description", "UTF-8");
                    nh.transferFunds("88881949", "5555666677770950", "5555666677770949", 10000, testDesc, 1, "PMR");
                    //nh.getBranchAtmLocations("ATM", 72.9376984, 19.1445007);
                    nh.getLoanAccountSummary("88881949");
                    nh.getLoanEMIDetails("LBMUM11112220949");
                    nh.getLoanTransactionDetails("LBMUM11112220949");
                    // nh.getCardAccountDetails("88881949");
                }catch (Exception e) {

                }
            }
        }).start();
    }

    public class DataLoadTask extends AsyncTask<Void, Void, List<SummaryUIItem>>
    {

        @Override
        protected List<SummaryUIItem> doInBackground(Void... voids)
        {
            SummaryUIItem uiItem1 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER, "Bank Account", -1, null);
            SummaryUIItem uiItem4 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER, "Card", -1, null);
            SummaryUIItem uiItem7 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER, "Loan Account", -1, null);
            SummaryUIItem uiItem2 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0000012243547123", 2345687.00,
                    Constants.HEADER_TYPE_BANKACCOUNT);
            SummaryUIItem uiItem3 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0000012246547123", 100000.00,
                    Constants.HEADER_TYPE_BANKACCOUNT);
            SummaryUIItem uiItem5 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0000012143547234", 50000.00,
                    Constants.HEADER_TYPE_CARD);
            SummaryUIItem uiItem6 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0020012243547456", 60000.00,
                    Constants.HEADER_TYPE_CARD);
            SummaryUIItem uiItem8 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0030012243547678", 789.00,
                    Constants.HEADER_TYPE_LOANACCOUNT);
            SummaryUIItem uiItem9 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0007012243547789", 60975.00,
                    Constants.HEADER_TYPE_LOANACCOUNT);

            List<SummaryUIItem> uiItems = new ArrayList<>();
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
        protected void onPostExecute(List<SummaryUIItem> uiItems)
        {
            if (mAdapter == null) {
                mAdapter = new AccountSummaryFragmentAdapter(getActivity(), -1, uiItems);
                mAccountSummaryLV.setAdapter(mAdapter);
            }
            else {
                mAdapter.setUiItems(uiItems);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
