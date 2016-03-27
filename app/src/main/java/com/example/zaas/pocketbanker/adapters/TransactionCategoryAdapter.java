package com.example.zaas.pocketbanker.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionDetailViewHolder;
import com.example.zaas.pocketbanker.utils.DateUtils;
import com.example.zaas.pocketbanker.utils.TransactionCategoryUtils;

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
        final Transaction transactionToBind = mTransactions.get(position);
        holder.getTransAmountTV().setText("Rs. " + transactionToBind.getAmount());
        holder.getTransDateTV().setText(DateUtils.getDateStringFromMillis(transactionToBind.getTime()));
        //holder.getTransTypeTV().setText(transactionToBind.getType().toString());
        holder.getTransRemarkTV().setText(transactionToBind.getRemark());

        if (transactionToBind.getCategory() == TransactionCategoryUtils.Category.UNKNOWN) {
            TransactionCategoryUtils.Category[] categories = TransactionCategoryUtils.Category.values();
            final List<String> categoryStrings = new ArrayList<>();
            for (TransactionCategoryUtils.Category category : categories) {
                categoryStrings.add(category.name());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item,
                    categoryStrings);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            holder.getTransCategorySpinner().setAdapter(dataAdapter);
            holder.getTransCategorySpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    if (position != 0) {
                        // valid selection
                        TransactionCategoryUtils.Category selectedCategory = TransactionCategoryUtils.Category
                                .valueOf(categoryStrings.get(position));
                        transactionToBind.setCategory(selectedCategory);
                        TransactionCategoryUtils.updateCategoryForTransaction(transactionToBind, selectedCategory);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });

            holder.getTransCategorySpinner().setVisibility(View.VISIBLE);
        }
        else {
            holder.getTransCategorySpinner().setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }
}
