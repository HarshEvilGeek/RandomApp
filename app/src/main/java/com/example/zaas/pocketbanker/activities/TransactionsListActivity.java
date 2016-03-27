package com.example.zaas.pocketbanker.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.TransactionsListFragmentAdapter;
import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.models.local.LoanEMI;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionDataUIItem;
import com.example.zaas.pocketbanker.sync.NetworkHelper;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class TransactionsListActivity extends AppCompatActivity {

    RecyclerView mTransactionListFragmentRV;

    TransactionsListFragmentAdapter mAdapter;
    String accountNo;
    // bank acc/loan acc/card
    String headerType;
    TextView fromDateTV;
    TextView toDateTV;
    TextView dateSelectionTextTV;
    ImageView fetchTransactionIV;
    SwipeRefreshLayout mTransactionsListSwipeRefresh;
    private long mFromDateValue = System.currentTimeMillis() - Constants.ONE_MONTH_IN_MILLIS;
    private long mToDateValue = System.currentTimeMillis();

    private static final int FROM_DATE_PICKER_ID = 1000;
    private static final int TO_DATE_PICKER_ID = 1001;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }
        handleExtras(extras);
        mContext = this;
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Acc. # " + accountNo);
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_transaction_list);

        mTransactionListFragmentRV = (RecyclerView) findViewById(R.id.transaction_list_fragment_RV);
        fromDateTV = (TextView) findViewById(R.id.from_date);
        toDateTV = (TextView) findViewById(R.id.to_date);
        dateSelectionTextTV = (TextView) findViewById(R.id.date_selection_text);
        fetchTransactionIV = (ImageView) findViewById(R.id.fetch_transaction_button);
        fetchTransactionIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                fetchTransactions();
            }
        });
        mTransactionsListSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.transactions_list_swipe_refresh);
        mTransactionsListSwipeRefresh.setColorSchemeColors(Color.BLUE);

        // we dont need the pull to refresh option in this fragment
        disablePullToRefresh();
        setDatePickers();
        populateDatePickerHeading();
        loadData();
    }

    private void disablePullToRefresh()
    {
        if (mTransactionsListSwipeRefresh != null) {
            mTransactionsListSwipeRefresh.setEnabled(false);
        }
    }

    private void enablePullToRefresh()
    {
        if (mTransactionsListSwipeRefresh != null) {
            mTransactionsListSwipeRefresh.setEnabled(true);
        }
    }

    private void stopProgressInPullToRefresh()
    {
        if (mTransactionsListSwipeRefresh != null) {
            mTransactionsListSwipeRefresh.setRefreshing(false);
        }
    }

    private void startProgressInPullToRefresh()
    {
        if (mTransactionsListSwipeRefresh != null) {
            mTransactionsListSwipeRefresh.setRefreshing(true);
        }
    }

    private void fetchTransactions()
    {
        Log.e("LOG", "fetching transactions from : " + fromDateTV.getText().toString() + " to : "
                + toDateTV.getText().toString());
        new NetworkTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

    }

    private void populateDatePickerHeading()
    {
        if (Constants.HEADER_TYPE_BANKACCOUNT.equals(headerType) || Constants.HEADER_TYPE_CARD.equals(headerType)) {
            dateSelectionTextTV.setText("Transaction History");
        }
        else if (Constants.HEADER_TYPE_LOANACCOUNT.equals(headerType)) {
            dateSelectionTextTV.setText("EMI History");
        }
    }

    private void setDatePickers()
    {
        fromDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // On button click show datepicker dialog
                createDialog(FROM_DATE_PICKER_ID).show();
            }
        });

        toDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // On button click show datepicker dialog
                createDialog(TO_DATE_PICKER_ID).show();
            }
        });

        updateDates();
    }

    protected Dialog createDialog(int id)
    {
        Time initialDate = new Time();
        switch (id)
        {
        case FROM_DATE_PICKER_ID:
            initialDate.set(mFromDateValue);
            return new DatePickerDialog(mContext, R.style.DatePickerTheme, fromdatePickerListener, initialDate.year, initialDate.month,
                    initialDate.monthDay);
        case TO_DATE_PICKER_ID:
            initialDate.set(mToDateValue);
            return new DatePickerDialog(mContext,R.style.DatePickerTheme, todatePickerListener, initialDate.year, initialDate.month,
                    initialDate.monthDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener fromdatePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
        {
            Time updateDate = new Time();
            updateDate.set(selectedDay, selectedMonth, selectedYear);
            mFromDateValue = updateDate.normalize(true);
            updateDates();
        }
    };

    private DatePickerDialog.OnDateSetListener todatePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
        {
            Time updateDate = new Time();
            updateDate.set(selectedDay, selectedMonth, selectedYear);
            mToDateValue = updateDate.normalize(true);
            updateDates();
        }
    };

    private void updateDates()
    {
        fromDateTV.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(mFromDateValue)));
        toDateTV.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(mToDateValue)));
        Log.e("DATEPICKER", "from date : " + fromDateTV.getText().toString() + " to date : "
                + toDateTV.getText().toString());
    }

    private void handleExtras(Bundle extras)
    {
        if (extras != null) {
            accountNo = extras.getString(Constants.SUMMARY_DETAIL_FRAGMENT_ACCOUNT_NUMBER);
            headerType = extras.getString(Constants.SUMMARY_DETAIL_FRAGMENT_HEADER_TYPE);
        }
    }

    private void loadData()
    {
        new DataLoadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }

    public class DataLoadTask extends AsyncTask<Void, Void, List<TransactionDataUIItem>>
    {

        @Override
        protected List<TransactionDataUIItem> doInBackground(Void... voids)
        {
            List<TransactionDataUIItem> uiItems = new ArrayList<>();

            if (headerType.equals(Constants.HEADER_TYPE_BANKACCOUNT)) {

                List<Transaction> latestTransactions = Transaction.getLatestTransactionsBetween(accountNo,
                        PocketBankerContract.Transactions.TIME + " DESC ", mFromDateValue, mToDateValue);

                if (latestTransactions != null && latestTransactions.size() > 0) {
                    for (Transaction transaction : latestTransactions) {
                        TransactionDataUIItem transactionUiItem = new TransactionDataUIItem(transaction.getAmount(),
                                transaction.getRemark(), transaction.getTime(), transaction.getType().name());
                        uiItems.add(transactionUiItem);
                    }
                }
            }
            else if (headerType.equals(Constants.HEADER_TYPE_LOANACCOUNT)) {
                List<LoanEMI> latestTransactions = LoanEMI.getEmisBetween(accountNo, PocketBankerContract.Emis.EMI_DATE
                        + " DESC ", mFromDateValue, mToDateValue);


                if (latestTransactions != null && latestTransactions.size() > 0) {
                    for (LoanEMI transaction : latestTransactions) {
                        TransactionDataUIItem transactionUiItem = new TransactionDataUIItem(transaction.getEmiAmount(),
                                null, transaction.getEmiDate(), Transaction.Type.Credit.name());
                        uiItems.add(transactionUiItem);
                    }
                }

            }
            else if (headerType.equals(Constants.HEADER_TYPE_CARD)) {

                TransactionDataUIItem transaction1 = new TransactionDataUIItem(10000.00,
                        "Transferred to other account", System.currentTimeMillis(), Transaction.Type.Debit.name());
                TransactionDataUIItem transaction2 = new TransactionDataUIItem(5000.00, null,
                        System.currentTimeMillis() + 345670000, Transaction.Type.Credit.name());
                TransactionDataUIItem transaction3 = new TransactionDataUIItem(10000.00, "Refund from Merchant",
                        System.currentTimeMillis(), Transaction.Type.Debit.name());
                TransactionDataUIItem transaction4 = new TransactionDataUIItem(10000.00,
                        "Transferred to other account", System.currentTimeMillis(), Transaction.Type.Debit.name());
                TransactionDataUIItem transaction5 = new TransactionDataUIItem(10000.00, "Paid Loan EMI",
                        System.currentTimeMillis(), Transaction.Type.Credit.name());
                TransactionDataUIItem transaction6 = new TransactionDataUIItem(10000.00, "Added amount",
                        System.currentTimeMillis(), Transaction.Type.Debit.name());
                TransactionDataUIItem transaction7 = new TransactionDataUIItem(10000.00, "Transferred for FD",
                        System.currentTimeMillis(), Transaction.Type.Credit.name());

                uiItems.add(transaction1);
                uiItems.add(transaction2);
                uiItems.add(transaction3);
                uiItems.add(transaction4);
                uiItems.add(transaction5);
                uiItems.add(transaction6);
                uiItems.add(transaction7);
                uiItems.add(transaction7);
                uiItems.add(transaction1);
                uiItems.add(transaction3);
                uiItems.add(transaction4);

            }

            return uiItems;
        }

        @Override
        protected void onPostExecute(List<TransactionDataUIItem> uiItems)
        {
            if (mAdapter == null) {
                mAdapter = new TransactionsListFragmentAdapter(mContext, uiItems);
                mTransactionListFragmentRV.setAdapter(mAdapter);
                mTransactionListFragmentRV.setLayoutManager(new LinearLayoutManager(mTransactionListFragmentRV
                        .getContext()));

            }
            else {
                mAdapter.setUiItems(uiItems);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            startProgressInPullToRefresh();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            NetworkHelper networkHelper = new NetworkHelper();

            if (Constants.HEADER_TYPE_BANKACCOUNT.equals(headerType)) {

                networkHelper.fetchTransactionHistoryForPeriod(accountNo, new Date(mFromDateValue), new Date(
                        mToDateValue));

            }
            else if (Constants.HEADER_TYPE_LOANACCOUNT.equals(headerType)) {
                networkHelper.getLoanEMIDetails(accountNo);
            }
            else if (Constants.HEADER_TYPE_CARD.equals(headerType)) {
                // no api
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            loadData();
            stopProgressInPullToRefresh();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
