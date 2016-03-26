package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/26/16.
 */
public class WalletBalanceResponse
{

    private double amount;

    @SerializedName ("bank_txn_id")
    private long bankTransactionId;

    private String errorCode;

    @SerializedName ("errorDescripttion")
    private String errorDesc;

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public long getBankTransactionId()
    {
        return bankTransactionId;
    }

    public void setBankTransactionId(long bankTransactionId)
    {
        this.bankTransactionId = bankTransactionId;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorDesc()
    {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc)
    {
        this.errorDesc = errorDesc;
    }

    @Override
    public String toString()
    {
        return "WalletBalanceResponse{" + "amount=" + amount + ", bankTransactionId=" + bankTransactionId
                + ", errorCode='" + errorCode + '\'' + ", errorDesc='" + errorDesc + '\'' + '}';
    }
}
