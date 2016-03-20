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
import com.example.zaas.pocketbanker.models.local.SummaryUIItem;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

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
        // testAccountSummary();
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
                new NetworkHelper().fetchAccountBalance("5555666677770949");
            }
        }).start();
    }

    public class DataLoadTask extends AsyncTask<Void, Void, List<SummaryUIItem>>
    {

        @Override
        protected List<SummaryUIItem> doInBackground(Void... voids)
        {
            SummaryUIItem uiItem1 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER, "Bank Account", -1);
            SummaryUIItem uiItem4 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER, "Card", -1);
            SummaryUIItem uiItem7 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_HEADER, "Loan Account", -1);
            SummaryUIItem uiItem2 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0000012243547123", 2345687.00);
            SummaryUIItem uiItem3 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0000012246547123", 100000.00);
            SummaryUIItem uiItem5 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0000012143547234", 50000.00);
            SummaryUIItem uiItem6 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0020012243547456", 60000.00);
            SummaryUIItem uiItem8 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0030012243547678", 789.00);
            SummaryUIItem uiItem9 = new SummaryUIItem(Constants.SUMMARY_ITEM_TYPE_ITEM, "0007012243547789", 60975.00);

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
            } else {
                mAdapter.setUiItems(uiItems);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
