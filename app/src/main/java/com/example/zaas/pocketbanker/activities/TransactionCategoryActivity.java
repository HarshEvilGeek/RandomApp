package com.example.zaas.pocketbanker.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.TransactionCategoryAdapter;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.utils.TransactionCategoryUtils;

/**
 * Created by shseth on 3/26/2016.
 */
public class TransactionCategoryActivity extends AppCompatActivity
{
    private List<Transaction> mTransactions;
    private TransactionCategoryUtils.Category mCategory;
    private long mFromDate;
    private long mToDate;
    private TransactionCategoryAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_category);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mCategory = (TransactionCategoryUtils.Category) extras.get("Category");
        mFromDate = extras.getLong("FromDate");
        mToDate = extras.getLong("ToDate");

        setFilteredTransactions();

        mAdapter = new TransactionCategoryAdapter(this, mTransactions);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

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

    private void setFilteredTransactions()
    {
        mTransactions = new ArrayList<>();
        List<Transaction> allTransactions = TransactionCategoryUtils.getAllTransactions();
        for (Transaction transaction : allTransactions) {
            if (transaction.getCategory() == mCategory && transaction.getType() == Transaction.Type.Debit
                    && transaction.getTime() >= mFromDate && transaction.getTime() <= mToDate) {
                mTransactions.add(transaction);
            }
        }
    }
}
