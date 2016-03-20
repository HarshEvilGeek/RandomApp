package com.example.zaas.pocketbanker.models.local;

/**
 * Created by zaraahmed on 3/20/16.
 */
public class TransactionDataUIItem
{

    private double transactionAmount;

    // remark in case of bank account, vendor details in case of card
    private String transactionRemark;

    private long transactionDate;

    // debit/credit/mode of transfer for loan
    private String transactionType;

    public TransactionDataUIItem(double transactionAmount, String transactionRemark, long transactionDate,
            String transactionType)
    {
        this.transactionAmount = transactionAmount;
        this.transactionRemark = transactionRemark;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }

    public double getTransactionAmount()
    {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount)
    {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionRemark()
    {
        return transactionRemark;
    }

    public void setTransactionRemark(String transactionRemark)
    {
        this.transactionRemark = transactionRemark;
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
