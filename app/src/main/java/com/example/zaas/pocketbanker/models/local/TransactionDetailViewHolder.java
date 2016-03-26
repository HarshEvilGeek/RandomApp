package com.example.zaas.pocketbanker.models.local;

import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class TransactionDetailViewHolder extends RecyclerView.ViewHolder
{

    private TextView transAmountTV;
    private TextView transDateTV;
    private TextView transRemarkTV;
    private TextView transRemarkTitleTV;
    private AppCompatSpinner transCategorySpinner;

    public TransactionDetailViewHolder(View itemView)
    {

        super(itemView);
        transAmountTV = (TextView) itemView.findViewById(R.id.amount_tv);
        transDateTV = (TextView) itemView.findViewById(R.id.date_of_transaction_tv);
        transRemarkTV = (TextView) itemView.findViewById(R.id.trans_remark_tv);
        transRemarkTitleTV = (TextView) itemView.findViewById(R.id.trans_remark_heading_tv);
        transCategorySpinner = (AppCompatSpinner) itemView.findViewById(R.id.trans_category_spinner);

    }

    public TextView getTransAmountTV()
    {
        return transAmountTV;
    }

    public void setTransAmountTV(TextView transAmountTV)
    {
        this.transAmountTV = transAmountTV;
    }

    public TextView getTransDateTV()
    {
        return transDateTV;
    }

    public void setTransDateTV(TextView transDateTV)
    {
        this.transDateTV = transDateTV;
    }

    public TextView getTransRemarkTV()
    {
        return transRemarkTV;
    }

    public void setTransRemarkTV(TextView transRemarkTV)
    {
        this.transRemarkTV = transRemarkTV;
    }

    public TextView getTransRemarkTitleTV()
    {
        return transRemarkTitleTV;
    }

    public void setTransRemarkTitleTV(TextView transRemarkTitleTV)
    {
        this.transRemarkTitleTV = transRemarkTitleTV;
    }

    public AppCompatSpinner getTransCategorySpinner() {
        return transCategorySpinner;
    }
}
