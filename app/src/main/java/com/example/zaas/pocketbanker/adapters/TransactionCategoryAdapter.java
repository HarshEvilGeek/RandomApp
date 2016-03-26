package com.example.zaas.pocketbanker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.activities.TransactionCategoryActivity;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionDetailViewHolder;
import com.example.zaas.pocketbanker.utils.DateUtils;

import java.util.List;

/**
 * Created by shseth on 3/26/2016.
 */
public class TransactionCategoryAdapter extends RecyclerView.Adapter<TransactionDetailViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Transaction> mTransactions;

    public TransactionCategoryAdapter(Context context, List<Transaction> transactions) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTransactions = transactions;
    }

    public void updateList(List<Transaction> transactions) {
        mTransactions = transactions;
        notifyDataSetChanged();
    }

    @Override
    public TransactionDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.transaction_detail, parent, false);
        return new TransactionDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionDetailViewHolder holder, int position) {
        Transaction transactionToBind = mTransactions.get(position);
        holder.getTransAmountTV().setText("Rs. " + transactionToBind.getAmount());
        holder.getTransDateTV().setText(DateUtils.getDateStringFromMillis(transactionToBind.getTime()));
        //holder.getTransTypeTV().setText(transactionToBind.getType().toString());
        holder.getTransRemarkTV().setText(transactionToBind.getRemark());
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }
}
