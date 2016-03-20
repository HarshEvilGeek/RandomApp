package com.example.zaas.pocketbanker.models.local;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class TransactionSummaryUIItem
{

    int itemType;
    String title;
    double transactionAmount;
    long transactionDate;
    // credit/debit
    String transactionType;

    public TransactionSummaryUIItem(int itemType, String title, double transactionAmount, long transactionDate,
            String transactionType)
    {
        this.itemType = itemType;
        this.title = title;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;

    }

    public int getItemType()
    {
        return itemType;
    }

    public void setItemType(int itemType)
    {
        this.itemType = itemType;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public double getTransactionAmount()
    {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount)
    {
        this.transactionAmount = transactionAmount;
    }

    public long getTransactionDate()
    {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate)
    {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }
}
