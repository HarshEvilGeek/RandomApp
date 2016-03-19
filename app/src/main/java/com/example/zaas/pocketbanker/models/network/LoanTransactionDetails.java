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

    int code;

    String message;

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

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
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

    @Override
    public String toString()
    {
        return "LoanTransactionDetails{" + "lastPaymentMade=" + lastPaymentMade + ", paymentMode='" + paymentMode
                + '\'' + ", code=" + code + ", message='" + message + '\'' + ", bankTransId='" + bankTransId + '\''
                + ", statement='" + statement + '\'' + '}';
    }
}
