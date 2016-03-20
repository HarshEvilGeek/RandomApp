package com.example.zaas.pocketbanker.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.AccountSummaryDetailHeaderUIITem;
import com.example.zaas.pocketbanker.models.local.AccountSummaryDetailItem;
import com.example.zaas.pocketbanker.models.local.TransactionDetailViewHolder;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class AccountSummaryDetailFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    Context mContext;
    List<AccountSummaryDetailItem> mUiItems;

    public AccountSummaryDetailFragmentAdapter(Context context, List<AccountSummaryDetailItem> uiItems)
    {
        mContext = context;
        mUiItems = uiItems;

    }

    public void setUiItems(List<AccountSummaryDetailItem> uiItems)
    {
        mUiItems = uiItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder vh = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (Constants.SUMMARY_ITEM_TYPE_HEADER == viewType) {

            View rootView = inflater.inflate(R.layout.summary_detail_header, parent, false);
            vh = new AccountSummaryDetailFragmentHeaderViewHolder(rootView);

        }
        else if (Constants.SUMMARY_ITEM_TYPE_ITEM == viewType) {

            View rootView = inflater.inflate(R.layout.transaction_detail, parent, false);
            vh = new TransactionDetailViewHolder(rootView);

        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position)
    {
        AccountSummaryDetailItem uiItem = mUiItems.get(position);
        int viewType = getItemViewType(position);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        if (Constants.SUMMARY_ITEM_TYPE_HEADER == viewType) {

            AccountSummaryDetailHeaderUIITem headerUIITem = uiItem.getHeaderUIITem();
            AccountSummaryDetailFragmentHeaderViewHolder viewHolder = (AccountSummaryDetailFragmentHeaderViewHolder) vh;

            viewHolder.accNoTV.setText(headerUIITem.getAccountNo());

            if (Constants.HEADER_TYPE_BANKACCOUNT.equals(headerUIITem.getHeaderType())) {

                viewHolder.balanceTV.setVisibility(View.VISIBLE);
                viewHolder.balanceValueTV.setVisibility(View.VISIBLE);
                viewHolder.accTypeTV.setVisibility(View.VISIBLE);
                viewHolder.accTypeValueTV.setVisibility(View.VISIBLE);
                viewHolder.timeTV.setVisibility(View.VISIBLE);
                viewHolder.timeValTV.setVisibility(View.VISIBLE);

                viewHolder.balanceTV.setText("Balance");
                viewHolder.balanceValueTV.setText(String.valueOf(headerUIITem.getBalance()));
                viewHolder.accTypeTV.setText("Account Type");
                viewHolder.accTypeValueTV.setText(headerUIITem.getAccountType());
                viewHolder.timeTV.setText("Time");
                viewHolder.timeValTV.setText(df.format(new Date(headerUIITem.getTime())));

                viewHolder.custNameTV.setVisibility(View.GONE);
                viewHolder.custNameValueTV.setVisibility(View.GONE);
                viewHolder.availLtTV.setVisibility(View.GONE);
                viewHolder.availLtValTV.setVisibility(View.GONE);
                viewHolder.statusTV.setVisibility(View.GONE);
                viewHolder.statucValTV.setVisibility(View.GONE);
                viewHolder.roiTV.setVisibility(View.GONE);
                viewHolder.roiValTV.setVisibility(View.GONE);

            }
            else if (Constants.HEADER_TYPE_LOANACCOUNT.equals(headerUIITem.getHeaderType())) {

                viewHolder.balanceTV.setVisibility(View.VISIBLE);
                viewHolder.balanceValueTV.setVisibility(View.VISIBLE);
                viewHolder.accTypeTV.setVisibility(View.VISIBLE);
                viewHolder.accTypeValueTV.setVisibility(View.VISIBLE);
                viewHolder.timeTV.setVisibility(View.VISIBLE);
                viewHolder.timeValTV.setVisibility(View.VISIBLE);
                viewHolder.custNameTV.setVisibility(View.VISIBLE);
                viewHolder.custNameValueTV.setVisibility(View.VISIBLE);
                viewHolder.roiTV.setVisibility(View.VISIBLE);
                viewHolder.roiValTV.setVisibility(View.VISIBLE);

                viewHolder.balanceTV.setText("Outstanding Balance");
                viewHolder.balanceValueTV.setText(String.valueOf(headerUIITem.getAccountDebt()));
                viewHolder.accTypeTV.setText("Type");
                viewHolder.accTypeValueTV.setText(headerUIITem.getAccountType());
                viewHolder.timeTV.setText("Date");
                viewHolder.timeValTV.setText(df.format(new Date(headerUIITem.getTime())));
                viewHolder.custNameTV.setText("Customer Name");
                viewHolder.custNameValueTV.setText(headerUIITem.getAccountHolderName());
                viewHolder.roiTV.setText("ROI %");
                viewHolder.roiValTV.setText(String.valueOf(headerUIITem.getRateOfInterest()));

                viewHolder.availLtTV.setVisibility(View.GONE);
                viewHolder.availLtValTV.setVisibility(View.GONE);
                viewHolder.statusTV.setVisibility(View.GONE);
                viewHolder.statucValTV.setVisibility(View.GONE);

            }
            else if (Constants.HEADER_TYPE_CARD.equals(headerUIITem.getHeaderType())) {

                viewHolder.balanceTV.setVisibility(View.VISIBLE);
                viewHolder.balanceValueTV.setVisibility(View.VISIBLE);
                viewHolder.accTypeTV.setVisibility(View.VISIBLE);
                viewHolder.accTypeValueTV.setVisibility(View.VISIBLE);
                viewHolder.timeTV.setVisibility(View.VISIBLE);
                viewHolder.timeValTV.setVisibility(View.VISIBLE);
                viewHolder.availLtTV.setVisibility(View.VISIBLE);
                viewHolder.availLtValTV.setVisibility(View.VISIBLE);
                viewHolder.statusTV.setVisibility(View.VISIBLE);
                viewHolder.statucValTV.setVisibility(View.VISIBLE);

                viewHolder.balanceTV.setText("Debt");
                viewHolder.balanceValueTV.setText(String.valueOf(headerUIITem.getAccountDebt()));
                viewHolder.accTypeTV.setText("Type");
                viewHolder.accTypeValueTV.setText(headerUIITem.getAccountType());
                viewHolder.timeTV.setText("Date of Expiry");
                viewHolder.timeValTV.setText(df.format(new Date(headerUIITem.getTime())));
                viewHolder.availLtTV.setText("Available Limit");
                viewHolder.availLtValTV.setText(String.valueOf(headerUIITem.getAvailableLimit()));
                viewHolder.statusTV.setText("Status");
                viewHolder.statucValTV.setText(headerUIITem.getStatus());

                viewHolder.custNameTV.setVisibility(View.GONE);
                viewHolder.custNameValueTV.setVisibility(View.GONE);
                viewHolder.roiTV.setVisibility(View.GONE);
                viewHolder.roiValTV.setVisibility(View.GONE);

            }

        }
        else if (Constants.SUMMARY_ITEM_TYPE_ITEM == viewType) {

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

    @Override
    public int getItemViewType(int position)
    {
        if (mUiItems != null) {
            AccountSummaryDetailItem uiItem = mUiItems.get(position);
            return uiItem.getItemType();
        }
        return -1;
    }

    public class AccountSummaryDetailFragmentHeaderViewHolder extends RecyclerView.ViewHolder
    {
        private TextView accNoTV;
        private TextView custNameTV;
        private TextView custNameValueTV;
        private TextView balanceTV;
        private TextView balanceValueTV;
        private TextView availLtTV;
        private TextView availLtValTV;
        private TextView accTypeTV;
        private TextView accTypeValueTV;
        private TextView timeTV;
        private TextView timeValTV;
        private TextView roiTV;
        private TextView roiValTV;
        private TextView statusTV;
        private TextView statucValTV;

        public AccountSummaryDetailFragmentHeaderViewHolder(View itemView)
        {
            super(itemView);

            accNoTV = (TextView) itemView.findViewById(R.id.acc_no_tv);
            custNameTV = (TextView) itemView.findViewById(R.id.cust_name_tv);
            custNameValueTV = (TextView) itemView.findViewById(R.id.cust_name_value_tv);
            balanceTV = (TextView) itemView.findViewById(R.id.balance_tv);
            balanceValueTV = (TextView) itemView.findViewById(R.id.balance_value_tv);
            availLtTV = (TextView) itemView.findViewById(R.id.avail_lt_tv);
            availLtValTV = (TextView) itemView.findViewById(R.id.avail_lt_value_tv);
            accTypeTV = (TextView) itemView.findViewById(R.id.acc_type_tv);
            accTypeValueTV = (TextView) itemView.findViewById(R.id.acc_type_value_tv);
            timeTV = (TextView) itemView.findViewById(R.id.time_tv);
            timeValTV = (TextView) itemView.findViewById(R.id.time_value_tv);
            roiTV = (TextView) itemView.findViewById(R.id.roi_tv);
            roiValTV = (TextView) itemView.findViewById(R.id.roi_value_tv);
            statusTV = (TextView) itemView.findViewById(R.id.status_tv);
            statucValTV = (TextView) itemView.findViewById(R.id.status_value_tv);

        }
    }
}
