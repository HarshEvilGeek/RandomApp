package com.example.zaas.pocketbanker.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.fragments.TransactionsListFragment;
import com.example.zaas.pocketbanker.models.local.TransactionSummaryUIItem;
import com.example.zaas.pocketbanker.utils.Constants;
import com.example.zaas.pocketbanker.utils.DateUtils;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class TransactionsSummaryFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<TransactionSummaryUIItem> mUiItems;
    private Context mContext;

    public TransactionsSummaryFragmentAdapter(Context context, List<TransactionSummaryUIItem> uiItems)
    {
        mUiItems = uiItems;
        mContext = context;
    }

    public void setUiItems(List<TransactionSummaryUIItem> uiItems)
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
            vh = new AccountTransactionsFragmentHeaderViewHolder(rootView);

        }
        else if (Constants.SUMMARY_ITEM_TYPE_ITEM == viewType) {

            View rootView = inflater.inflate(R.layout.transaction_summary_item, parent, false);
            vh = new AccountTransactionsFragmentViewHolder(rootView);

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
            TransactionSummaryUIItem uiItem = mUiItems.get(position);
            return uiItem.getItemType();
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        TransactionSummaryUIItem uiItem = mUiItems.get(position);

        if (Constants.SUMMARY_ITEM_TYPE_HEADER == uiItem.getItemType()) {

            AccountTransactionsFragmentHeaderViewHolder vh = (AccountTransactionsFragmentHeaderViewHolder) holder;

            vh.headerTV.setText(uiItem.getTitle());

        }
        else if (Constants.SUMMARY_ITEM_TYPE_ITEM == uiItem.getItemType()) {

            AccountTransactionsFragmentViewHolder vh = (AccountTransactionsFragmentViewHolder) holder;

            vh.setHeaderType(uiItem.getHeaderType());

            vh.transTitleTV.setText(uiItem.getTitle());

            vh.transAmountTV.setText("Rs. " + String.valueOf(uiItem.getTransactionAmount()));

            vh.transTypeTV.setText(uiItem.getTransactionType());

            String dateString = DateUtils.getDateStringFromMillis(uiItem.getTransactionDate());

            vh.transDateTV.setText(dateString);

        }

    }

    public class AccountTransactionsFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView transTitleTV;
        private TextView transAmountTV;
        private TextView transDateTV;
        private TextView transTypeTV;
        private String headerType;

        public AccountTransactionsFragmentViewHolder(View itemView)
        {
            super(itemView);
            transTitleTV = (TextView) itemView.findViewById(R.id.transaction_summary_title_tv);
            transAmountTV = (TextView) itemView.findViewById(R.id.transaction_amount_tv);
            transDateTV = (TextView) itemView.findViewById(R.id.transaction_date_tv);
            transTypeTV = (TextView) itemView.findViewById(R.id.transaction_type_tv);
            itemView.setOnClickListener(this);
        }

        public void setHeaderType(String headerType)
        {
            this.headerType = headerType;
        }

        @Override
        public void onClick(View view)
        {

            TransactionsListFragment fragment = new TransactionsListFragment();
            Bundle args = new Bundle();
            args.putString(Constants.SUMMARY_DETAIL_FRAGMENT_HEADER_TYPE, headerType);
            args.putString(Constants.SUMMARY_DETAIL_FRAGMENT_ACCOUNT_NUMBER, transTitleTV.getText().toString());
            fragment.setArguments(args);
            FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // ft.addToBackStack(null);
            ft.commit();
        }
    }

    public class AccountTransactionsFragmentHeaderViewHolder extends RecyclerView.ViewHolder
    {

        private TextView headerTV;

        public AccountTransactionsFragmentHeaderViewHolder(View itemView)
        {
            super(itemView);
            headerTV = (TextView) itemView.findViewById(R.id.header_tv);

        }
    }

}
