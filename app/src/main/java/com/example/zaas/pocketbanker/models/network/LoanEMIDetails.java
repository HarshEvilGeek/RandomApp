package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class LoanEMIDetails
{

    @SerializedName ("no_of_EMIs")
    String noOfEmis;

    @SerializedName ("emi_Dates")
    String emiDates;

    @SerializedName ("last_three_EMIs")
    String lastThreeEmis;

    int code;

    String message;

    @SerializedName ("bank_txn_id")
    String bankTransId;

    String statement;

    public String getNoOfEmis()
    {
        return noOfEmis;
    }

    public void setNoOfEmis(String noOfEmis)
    {
        this.noOfEmis = noOfEmis;
    }

    public String getEmiDates()
    {
        return emiDates;
    }

    public void setEmiDates(String emiDates)
    {
        this.emiDates = emiDates;
    }

    public String getLastThreeEmis()
    {
        return lastThreeEmis;
    }

    public void setLastThreeEmis(String lastThreeEmis)
    {
        this.lastThreeEmis = lastThreeEmis;
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
        return "LoanEMIDetails{" + "noOfEmis='" + noOfEmis + '\'' + ", emiDates='" + emiDates + '\''
                + ", lastThreeEmis='" + lastThreeEmis + '\'' + ", code=" + code + ", message='" + message + '\''
                + ", bankTransId='" + bankTransId + '\'' + ", statement='" + statement + '\'' + '}';
    }
}
