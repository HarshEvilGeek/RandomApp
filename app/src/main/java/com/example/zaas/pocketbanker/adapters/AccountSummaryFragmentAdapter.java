package com.example.zaas.pocketbanker.adapters;

import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.AccountSummaryDetailActivity;
import com.example.zaas.pocketbanker.models.local.SummaryUIItem;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class AccountSummaryFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<SummaryUIItem> mUiItems;
    private Context mContext;

    public AccountSummaryFragmentAdapter(Context context, List<SummaryUIItem> uiItems)
    {
        mUiItems = uiItems;
        mContext = context;
    }

    public void setUiItems(List<SummaryUIItem> uiItems)
    {
        this.mUiItems = uiItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder vh = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (Constants.SUMMARY_ITEM_TYPE_HEADER == viewType) {

            View rootView = inflater.inflate(R.layout.header_separators, parent, false);
            vh = new AccountSummaryFragmentHeaderViewHolder(rootView);

        }
        else if (Constants.SUMMARY_ITEM_TYPE_ITEM == viewType) {

            View rootView = inflater.inflate(R.layout.summary_transaction_item, parent, false);
            vh = new AccountSummaryFragmentViewHolder(rootView);

        }
        return vh;
    }

    @Override
    public int getItemCount()
    {
        if (mUiItems != null) {
            return mUiItems.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (mUiItems != null) {
            SummaryUIItem uiItem = mUiItems.get(position);
            return uiItem.getItemType();
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        SummaryUIItem uiItem = mUiItems.get(position);

        if (Constants.SUMMARY_ITEM_TYPE_HEADER == uiItem.getItemType()) {

            AccountSummaryFragmentHeaderViewHolder vh = (AccountSummaryFragmentHeaderViewHolder) holder;

            vh.headerTV.setText(uiItem.getTitle() + "S");

        }
        else if (Constants.SUMMARY_ITEM_TYPE_ITEM == uiItem.getItemType()) {

            AccountSummaryFragmentViewHolder vh = (AccountSummaryFragmentViewHolder) holder;

            vh.setHeaderType(uiItem.getHeaderType());

            vh.summaryTitleTV.setText(uiItem.getTitle());

            vh.summaryBalanceTV.setText("₹ " + String.valueOf(uiItem.getBalance()));

        }

    }

    public class AccountSummaryFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView summaryTitleTV;
        private TextView summaryBalanceTV;
        private String headerType;

        public AccountSummaryFragmentViewHolder(View itemView)
        {
            super(itemView);
            summaryTitleTV = (TextView) itemView.findViewById(R.id.summary_title_tv);
            summaryBalanceTV = (TextView) itemView.findViewById(R.id.summary_balance_tv);
            itemView.setOnClickListener(this);
        }

        public void setHeaderType(String headerType)
        {
            this.headerType = headerType;
        }

        @Override
        public void onClick(View view)
        {
            Intent detailIntent = new Intent(mContext, AccountSummaryDetailActivity.class);
            detailIntent.putExtra(Constants.SUMMARY_DETAIL_FRAGMENT_HEADER_TYPE, headerType);
            detailIntent.putExtra(Constants.SUMMARY_DETAIL_FRAGMENT_ACCOUNT_NUMBER, summaryTitleTV.getText().toString());
            mContext.startActivity(detailIntent);
        }
    }

    public class AccountSummaryFragmentHeaderViewHolder extends RecyclerView.ViewHolder
    {

        private TextView headerTV;

        public AccountSummaryFragmentHeaderViewHolder(View itemView)
        {
            super(itemView);
            headerTV = (TextView) itemView.findViewById(R.id.header_tv);

        }
    }
}
