package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class FundTransfer
{

    @SerializedName ("destination_accountno")
    private String destinationAccountNo;

    @SerializedName ("transaction_date")
    private String transactionDate;

    @SerializedName ("referance_no")
    private String refNo;

    @SerializedName ("transaction_amount")
    private double transAmount;

    @SerializedName ("payee_name")
    private String payeeName;

    @SerializedName ("payee_id")
    private String payeeId;

    private String status;

    public String getDestinationAccountNo()
    {
        return destinationAccountNo;
    }

    public void setDestinationAccountNo(String destinationAccountNo)
    {
        this.destinationAccountNo = destinationAccountNo;
    }

    public String getTransactionDate()
    {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate)
    {
        this.transactionDate = transactionDate;
    }

    public String getRefNo()
    {
        return refNo;
    }

    public void setRefNo(String refNo)
    {
        this.refNo = refNo;
    }

    public double getTransAmount()
    {
        return transAmount;
    }

    public void setTransAmount(double transAmount)
    {
        this.transAmount = transAmount;
    }

    public String getPayeeName()
    {
        return payeeName;
    }

    public void setPayeeName(String payeeName)
    {
        this.payeeName = payeeName;
    }

    public String getPayeeId()
    {
        return payeeId;
    }

    public void setPayeeId(String payeeId)
    {
        this.payeeId = payeeId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "FundTransfer{" + "destinationAccountNo='" + destinationAccountNo + '\'' + ", transactionDate='"
                + transactionDate + '\'' + ", refNo='" + refNo + '\'' + ", transAmount=" + transAmount
                + ", payeeName='" + payeeName + '\'' + ", payeeId='" + payeeId + '\'' + ", status='" + status + '\''
                + '}';
    }
}
