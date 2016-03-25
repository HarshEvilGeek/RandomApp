package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class Transactions
{

    private String transactiondate;

    @SerializedName ("closing_balance")
    private double closingbalance;

    private String accountno;

    @SerializedName ("credit_debit_flag")
    private String creditdebitflag;

    @SerializedName ("transaction_amount")
    private double transactionamount;

    private String remark;

    public String getTransactiondate()
    {
        return transactiondate;
    }

    public void setTransactiondate(String transactiondate)
    {
        this.transactiondate = transactiondate;
    }

    public double getClosingbalance()
    {
        return closingbalance;
    }

    public void setClosingbalance(double closingbalance)
    {
        this.closingbalance = closingbalance;
    }

    public String getAccountno()
    {
        return accountno;
    }

    public void setAccountno(String accountno)
    {
        this.accountno = accountno;
    }

    public String getCreditdebitflag()
    {
        return creditdebitflag;
    }

    public void setCreditdebitflag(String creditdebitflag)
    {
        this.creditdebitflag = creditdebitflag;
    }

    public double getTransactionamount()
    {
        return transactionamount;
    }

    public void setTransactionamount(double transactionamount)
    {
        this.transactionamount = transactionamount;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Override
    public String toString()
    {
        return "Transactions{" + "transactiondate='" + transactiondate + '\'' + ", closingbalance='" + closingbalance
                + '\'' + ", accountno='" + accountno + '\'' + ", creditdebitflag='" + creditdebitflag + '\''
                + ", transactionamount='" + transactionamount + '\'' + ", remark='" + remark + '\'' + '}';
    }
}
