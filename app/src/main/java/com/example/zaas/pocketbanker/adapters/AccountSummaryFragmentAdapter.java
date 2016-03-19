package com.example.zaas.pocketbanker.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.SummaryUIItem;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class AccountSummaryFragmentAdapter extends ArrayAdapter
{

    private List<SummaryUIItem> mUiItems;
    private Context mContext;

    public AccountSummaryFragmentAdapter(Context context, int resource, List objects) {
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

        SummaryUIItem uiItem = mUiItems.get(i);

        if (uiItem != null) {

            LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (Constants.SUMMARY_ITEM_TYPE_HEADER == uiItem.getItemType()) {

                view = inflator.inflate(R.layout.header_separators, viewGroup, false);
                TextView headerTv = (TextView)view.findViewById(R.id.header_tv);
                headerTv.setText(uiItem.getTitle());

            }
            else if (Constants.SUMMARY_ITEM_TYPE_ITEM == uiItem.getItemType()) {

                view = inflator.inflate(R.layout.summary_transaction_item, viewGroup, false);
                TextView accountIdTv = (TextView)view.findViewById(R.id.summary_tv);
                accountIdTv.setText(uiItem.getTitle());
            }

        }
        return view;
    }
}
