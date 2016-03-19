package com.example.zaas.pocketbanker.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.Payee;

/**
 * Recycler view adapter for payee list Created by adugar on 3/19/16.
 */
public class PayeeAdapter extends RecyclerView.Adapter<PayeeAdapter.PayeeViewHolder>
{
    private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Payee> mPayeeList;
    private OnClickListener mOnClickListener;

    public PayeeAdapter(Context context)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPayeeList = createDummyPayeeList();
    }

    public PayeeAdapter(Context context, List<Payee> payeeList)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPayeeList = payeeList;
    }

    public void setOnClickListener(OnClickListener onClickListener)
    {
        mOnClickListener = onClickListener;
    }

    @Override
    public PayeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.payee_item, parent, false);
        return new PayeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PayeeViewHolder holder, int position)
    {
        Payee payee = mPayeeList.get(position);
        holder.name.setText(payee.getName());
        holder.shortName.setText(payee.getShortName());
    }

    @Override
    public int getItemCount()
    {
        return mPayeeList.size();
    }

    private List<Payee> createDummyPayeeList()
    {
        List<Payee> payeeList = new ArrayList<>();
        payeeList.add(new Payee("1", "Akhil Verghese", "5100075", "Stud", System.currentTimeMillis() - 10
                * ONE_DAY_IN_MILLIS, "AKH075"));
        payeeList.add(new Payee("1", "Akshay Dugar", "510110", "Agz", System.currentTimeMillis() - 7
                * ONE_DAY_IN_MILLIS, "AKI110"));
        payeeList.add(new Payee("1", "Shayoni Seth", "510245", "Shy", System.currentTimeMillis() - 5
                * ONE_DAY_IN_MILLIS, "SHY245"));
        payeeList.add(new Payee("1", "Zara Ahmed", "510540", "Zar", System.currentTimeMillis() - 2 * ONE_DAY_IN_MILLIS,
                "ZAR540"));
        return payeeList;
    }

    public interface OnClickListener
    {
        void onClick(Payee payee);
    }

    public class PayeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final TextView name;
        private final TextView shortName;

        public PayeeViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            shortName = (TextView) itemView.findViewById(R.id.short_name);
        }

        @Override
        public void onClick(View v)
        {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(mPayeeList.get(getLayoutPosition()));
            }
        }
    }
}
