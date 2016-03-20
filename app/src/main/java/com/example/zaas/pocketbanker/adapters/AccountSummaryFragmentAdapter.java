package com.example.zaas.pocketbanker.adapters;

import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.fragments.AccountSummaryDetailFragment;
import com.example.zaas.pocketbanker.models.local.SummaryUIItem;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class AccountSummaryFragmentAdapter extends ArrayAdapter
{

    private List<SummaryUIItem> mUiItems;
    private Context mContext;

    public AccountSummaryFragmentAdapter(Context context, int resource, List objects)
    {
        super(context, resource, objects);
        mUiItems = objects;
        mContext = context;
    }

    public void setUiItems(List<SummaryUIItem> uiItems)
    {
        this.mUiItems = uiItems;
    }

    @Override
    public int getCount()
    {

        if (mUiItems != null) {
            return mUiItems.size();
        }
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {

        View view = null;

        final SummaryUIItem uiItem = mUiItems.get(i);

        if (uiItem != null) {

            LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (Constants.SUMMARY_ITEM_TYPE_HEADER == uiItem.getItemType()) {

                view = inflator.inflate(R.layout.header_separators, viewGroup, false);
                TextView headerTv = (TextView) view.findViewById(R.id.header_tv);
                headerTv.setText(uiItem.getTitle());

            }
            else if (Constants.SUMMARY_ITEM_TYPE_ITEM == uiItem.getItemType()) {

                view = inflator.inflate(R.layout.summary_transaction_item, viewGroup, false);
                TextView accountNoTV = (TextView) view.findViewById(R.id.summary_title_tv);
                accountNoTV.setText(uiItem.getTitle());

                TextView accountBalanceTV = (TextView) view.findViewById(R.id.summary_balance_tv);
                accountBalanceTV.setText("Rs. " + String.valueOf(uiItem.getBalance()));

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        AccountSummaryDetailFragment fragment = new AccountSummaryDetailFragment();
                        Bundle args = new Bundle();
                        args.putString(Constants.SUMMARY_DETAIL_FRAGMENT_HEADER_TYPE, uiItem.getHeaderType());
                        args.putString(Constants.SUMMARY_DETAIL_FRAGMENT_ACCOUNT_NUMBER, uiItem.getTitle());
                        fragment.setArguments(args);
                        FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    }
                });
            }

        }
        return view;
    }
}
