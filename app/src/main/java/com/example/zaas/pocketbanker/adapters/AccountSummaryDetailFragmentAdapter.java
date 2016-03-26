package com.example.zaas.pocketbanker.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.models.local.AccountSummaryDetailHeaderUIITem;
import com.example.zaas.pocketbanker.models.local.AccountSummaryDetailItem;
import com.example.zaas.pocketbanker.models.local.TransactionDataUIItem;
import com.example.zaas.pocketbanker.models.local.TransactionDetailViewHolder;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class AccountSummaryDetailFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private Context mContext;
    private List<AccountSummaryDetailItem> mUiItems;

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

            final AccountSummaryDetailHeaderUIITem headerUIITem = uiItem.getHeaderUIITem();
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
                viewHolder.delinquencyTitleTV.setVisibility(View.GONE);
                viewHolder.delinquencyViewTV.setVisibility(View.GONE);

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
                viewHolder.delinquencyTitleTV.setVisibility(View.VISIBLE);
                viewHolder.delinquencyViewTV.setVisibility(View.VISIBLE);

                SpannableStringBuilder delinquencyViewBuilder = new SpannableStringBuilder("View");
                delinquencyViewBuilder.setSpan(new UnderlineSpan(), 0, delinquencyViewBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                viewHolder.balanceTV.setText("Outstanding Principle");
                viewHolder.balanceValueTV.setText(String.valueOf(headerUIITem.getAccountDebt()));
                viewHolder.accTypeTV.setText("Type");
                viewHolder.accTypeValueTV.setText(headerUIITem.getAccountType());
                viewHolder.timeTV.setText("Date");
                viewHolder.timeValTV.setText(df.format(new Date(headerUIITem.getTime())));
                viewHolder.custNameTV.setText("Customer Name");
                viewHolder.custNameValueTV.setText(headerUIITem.getAccountHolderName());
                viewHolder.roiTV.setText("ROI %");
                viewHolder.roiValTV.setText(String.valueOf(headerUIITem.getRateOfInterest()));
                viewHolder.delinquencyViewTV.setText(delinquencyViewBuilder);
                viewHolder.delinquencyTitleTV.setText("Delinquency");

                viewHolder.availLtTV.setVisibility(View.GONE);
                viewHolder.availLtValTV.setVisibility(View.GONE);
                viewHolder.statusTV.setVisibility(View.GONE);
                viewHolder.statucValTV.setVisibility(View.GONE);

                viewHolder.delinquencyViewTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDelinquencyAlertDialog(headerUIITem.getDelinquency());
                    }
                });

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
                viewHolder.delinquencyTitleTV.setVisibility(View.GONE);
                viewHolder.delinquencyViewTV.setVisibility(View.GONE);

            }

        }
        else if (Constants.SUMMARY_ITEM_TYPE_ITEM == viewType) {

            TransactionDataUIItem transactionDataUIItem = uiItem.getTransactionDataUIItem();

            TransactionDetailViewHolder viewHolder = (TransactionDetailViewHolder) vh;

            viewHolder.getTransAmountTV().setText(transactionDataUIItem.getTransactionType() + " of ₹"
                    + String.valueOf(transactionDataUIItem.getTransactionAmount()));
            viewHolder.getTransDateTV().setText(df.format(new Date(transactionDataUIItem.getTransactionDate())));

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
        private TextView delinquencyTitleTV;
        private TextView delinquencyViewTV;

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
            delinquencyTitleTV = (TextView) itemView.findViewById(R.id.delinquency_title);
            delinquencyViewTV = (TextView) itemView.findViewById(R.id.delinquency_view);
        }
    }

    private void showDelinquencyAlertDialog(String delinquency)
    {
        Log.e("Adapter", "HERE!!!!!" + delinquency);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.delinquency_layout,null, false);

        TextView janTV = (TextView)rootView.findViewById(R.id.jan);
        TextView febTV = (TextView)rootView.findViewById(R.id.feb);
        TextView marTV = (TextView)rootView.findViewById(R.id.mar);
        TextView aprTV = (TextView)rootView.findViewById(R.id.apr);
        TextView mayTV = (TextView)rootView.findViewById(R.id.may);
        TextView junTV = (TextView)rootView.findViewById(R.id.jun);
        TextView julTV = (TextView)rootView.findViewById(R.id.jul);
        TextView augTV = (TextView)rootView.findViewById(R.id.aug);
        TextView sepTV = (TextView)rootView.findViewById(R.id.sep);
        TextView octTV = (TextView)rootView.findViewById(R.id.oct);
        TextView novTV = (TextView)rootView.findViewById(R.id.nov);
        TextView decTV = (TextView)rootView.findViewById(R.id.dec);

        TextView[] monthTVArray = new TextView[]{febTV,janTV,decTV,novTV,octTV,sepTV,augTV,julTV,junTV,mayTV,aprTV,marTV};

        for(int i = 0;i<monthTVArray.length;i++) {
            if(delinquency.charAt(i) == '0') {
                TextView view = monthTVArray[i];
                view.setBackgroundColor(mContext.getResources().getColor(R.color.delinquency_green));
            }
            else if(delinquency.charAt(i) == '1') {
                TextView view = monthTVArray[i];
                view.setBackgroundColor(mContext.getResources().getColor(R.color.delinquency_red));
            }
        }

        // Set grid view to alertDialog
        builder.setView(rootView);
        builder.setTitle("Delinquency");
        builder.show();
    }
}
