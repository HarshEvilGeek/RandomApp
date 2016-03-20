package com.example.zaas.pocketbanker.models.local;

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
    private TextView transTypeTV;
    private TextView transRemarkTV;

    public TransactionDetailViewHolder(View itemView)
    {

        super(itemView);
        transAmountTV = (TextView) itemView.findViewById(R.id.amount_tv);
        transDateTV = (TextView) itemView.findViewById(R.id.date_of_transaction_tv);
        transTypeTV = (TextView) itemView.findViewById(R.id.type_of_trans_tv);
        transRemarkTV = (TextView) itemView.findViewById(R.id.trans_remark_tv);

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

    public TextView getTransTypeTV()
    {
        return transTypeTV;
    }

    public void setTransTypeTV(TextView transTypeTV)
    {
        this.transTypeTV = transTypeTV;
    }

    public TextView getTransRemarkTV()
    {
        return transRemarkTV;
    }

    public void setTransRemarkTV(TextView transRemarkTV)
    {
        this.transRemarkTV = transRemarkTV;
    }
}
