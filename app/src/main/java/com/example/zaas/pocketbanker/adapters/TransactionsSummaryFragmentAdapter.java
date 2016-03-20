package com.example.zaas.pocketbanker.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.TransactionSummaryUIItem;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class TransactionsSummaryFragmentAdapter extends ArrayAdapter
{

    private List<TransactionSummaryUIItem> mUiItems;
    private Context mContext;

    public TransactionsSummaryFragmentAdapter(Context context, int resource, List objects)
    {
        super(context, resource, objects);
        mUiItems = objects;
        mContext = context;
    }

    public void setUiItems(List<TransactionSummaryUIItem> uiItems)
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

        TransactionSummaryUIItem uiItem = mUiItems.get(i);

        if (uiItem != null) {

            LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (Constants.SUMMARY_ITEM_TYPE_HEADER == uiItem.getItemType()) {

                view = inflator.inflate(R.layout.header_separators, viewGroup, false);
                TextView headerTv = (TextView) view.findViewById(R.id.header_tv);
                headerTv.setText(uiItem.getTitle());

            }
            else if (Constants.SUMMARY_ITEM_TYPE_ITEM == uiItem.getItemType()) {

                view = inflator.inflate(R.layout.transaction_summary_item, viewGroup, false);
                TextView accountNoTV = (TextView) view.findViewById(R.id.transaction_summary_title_tv);
                accountNoTV.setText(uiItem.getTitle());

                TextView transactionAmount = (TextView) view.findViewById(R.id.transaction_amount_tv);
                transactionAmount.setText("Rs. " + String.valueOf(uiItem.getTransactionAmount()));

                TextView transactionType = (TextView) view.findViewById(R.id.transaction_type_tv);
                transactionType.setText(uiItem.getTransactionType());

                Date transactionDate = new Date(uiItem.getTransactionDate());
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                String dateString = df.format(transactionDate);

                TextView transactionDateTV = (TextView) view.findViewById(R.id.transaction_date_tv);
                transactionDateTV.setText(dateString);

            }

        }
        return view;
    }
}
