package com.example.zaas.pocketbanker.models.network;

import com.example.zaas.pocketbanker.models.local.Transaction;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/26/16.
 */
public class WalletStatement
{

    @SerializedName ("bank_txn_id")
    private long bankTransactionId;

    @SerializedName ("txn_id")
    private long transactionId;

    private double amount;

    private String transDate;

    private String transType;

    private String remarks;

    private String walletAcNo;

    public long getBankTransactionId()
    {
        return bankTransactionId;
    }

    public void setBankTransactionId(long bankTransactionId)
    {
        this.bankTransactionId = bankTransactionId;
    }

    public long getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(long transactionId)
    {
        this.transactionId = transactionId;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getTransDate()
    {
        return transDate;
    }

    public long getTransactionDate() {
        // TODO ZARA FIX THIS
        return System.currentTimeMillis();
    }

    public void setTransDate(String transDate)
    {
        this.transDate = transDate;
    }

    public String getTransType()
    {
        return transType;
    }

    public Transaction.Type getTransactionType() {
        return Transaction.Type.getEnumFromNetworkType(transType);
    }

    public void setTransType(String transType)
    {
        this.transType = transType;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getWalletAcNo()
    {
        return walletAcNo;
    }

    public void setWalletAcNo(String walletAcNo)
    {
        this.walletAcNo = walletAcNo;
    }

    @Override
    public String toString()
    {
        return "WalletStatement{" + "bankTransactionId=" + bankTransactionId + ", transactionId=" + transactionId
                + ", amount=" + amount + ", transDate='" + transDate + '\'' + ", transType='" + transType + '\''
                + ", remarks='" + remarks + '\'' + ", walletAcNo='" + walletAcNo + '\'' + '}';
    }
}
