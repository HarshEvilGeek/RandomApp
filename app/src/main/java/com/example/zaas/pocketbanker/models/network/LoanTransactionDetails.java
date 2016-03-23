package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class LoanTransactionDetails
{

    @SerializedName ("last_payments_made")
    double lastPaymentMade;

    @SerializedName ("payment_mode")
    String paymentMode;

    private String errorCode;

    private String errorDescripttion;

    @SerializedName ("bank_txn_id")
    String bankTransId;

    String statement;

    public double getLastPaymentMade()
    {
        return lastPaymentMade;
    }

    public void setLastPaymentMade(double lastPaymentMade)
    {
        this.lastPaymentMade = lastPaymentMade;
    }

    public String getPaymentMode()
    {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode)
    {
        this.paymentMode = paymentMode;
    }

    public String getBankTransId()
    {
        return bankTransId;
    }

    public void setBankTransId(String bankTransId)
    {
        this.bankTransId = bankTransId;
    }

    public String getStatement()
    {
        return statement;
    }

    public void setStatement(String statement)
    {
        this.statement = statement;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorDescripttion()
    {
        return errorDescripttion;
    }

    public void setErrorDescripttion(String errorDescripttion)
    {
        this.errorDescripttion = errorDescripttion;
    }

    @Override
    public String toString()
    {
        return "LoanTransactionDetails{" + "lastPaymentMade=" + lastPaymentMade + ", paymentMode='" + paymentMode
                + '\'' + ", errorCode='" + errorCode + '\'' + ", errorDescripttion='" + errorDescripttion + '\''
                + ", bankTransId='" + bankTransId + '\'' + ", statement='" + statement + '\'' + '}';
    }
}
