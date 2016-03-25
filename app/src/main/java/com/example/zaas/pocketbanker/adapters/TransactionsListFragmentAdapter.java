package com.example.zaas.pocketbanker.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.AccountSummaryDetailItem;
import com.example.zaas.pocketbanker.models.local.TransactionDataUIItem;
import com.example.zaas.pocketbanker.models.local.TransactionDetailViewHolder;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class TransactionsListFragmentAdapter extends RecyclerView.Adapter<TransactionDetailViewHolder>
{

    private List<TransactionDataUIItem> mUiItems;
    private Context mContext;

    public TransactionsListFragmentAdapter(Context context, List<TransactionDataUIItem> uiItems)
    {
        this.mContext = context;
        mUiItems = uiItems;
    }

    public void setUiItems(List<TransactionDataUIItem> uiItems)
    {
        mUiItems = uiItems;
    }

    @Override
    public TransactionDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_detail, parent, false);
        TransactionDetailViewHolder vh = new TransactionDetailViewHolder(rootView);

        return vh;
    }

    @Override
    public void onBindViewHolder(TransactionDetailViewHolder viewHolder, int position)
    {

        TransactionDataUIItem transactionDataUIItem = mUiItems.get(position);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        viewHolder.getTransAmountTV().setText(String.valueOf(transactionDataUIItem.getTransactionAmount()));
        viewHolder.getTransDateTV().setText(df.format(new Date(transactionDataUIItem.getTransactionDate())));
        viewHolder.getTransTypeTV().setText(transactionDataUIItem.getTransactionType());

        if (!TextUtils.isEmpty(transactionDataUIItem.getTransactionRemark())) {
            viewHolder.getTransRemarkTV().setVisibility(View.VISIBLE);
            viewHolder.getTransRemarkTitleTV().setVisibility(View.VISIBLE);
            viewHolder.getTransRemarkTV().setText(transactionDataUIItem.getTransactionRemark());
        }
        else {
            viewHolder.getTransRemarkTV().setVisibility(View.GONE);
            viewHolder.getTransRemarkTitleTV().setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount()
    {
        if (mUiItems != null) {
            return mUiItems.size();
        }
        return 0;
    }
}
