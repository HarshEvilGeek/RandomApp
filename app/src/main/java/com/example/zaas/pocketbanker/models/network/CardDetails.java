package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class CardDetails
{

    private String custId;

    @SerializedName ("month_delinquency")
    private String monthDelinquency;

    @SerializedName ("current_balance")
    private String currentBalance;

    @SerializedName ("expiry_date")
    private String expiryDate;

    @SerializedName ("date_of_enrolemnt")
    private String dateOfEnrollment;

    private String cardType;

    @SerializedName ("avail_lmt")
    private String availLmt;

    private String cardStatus;

    @SerializedName ("card_acc_number")
    private String cardAccNumber;

    public String getCustId()
    {
        return custId;
    }

    public void setCustId(String custId)
    {
        this.custId = custId;
    }

    public String getMonthDelinquency()
    {
        return monthDelinquency;
    }

    public void setMonthDelinquency(String monthDelinquency)
    {
        this.monthDelinquency = monthDelinquency;
    }

    public String getCurrentBalance()
    {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance)
    {
        this.currentBalance = currentBalance;
    }

    public String getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public String getDateOfEnrollment()
    {
        return dateOfEnrollment;
    }

    public void setDateOfEnrollment(String dateOfEnrollment)
    {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public String getAvailLmt()
    {
        return availLmt;
    }

    public void setAvailLmt(String availLmt)
    {
        this.availLmt = availLmt;
    }

    public String getCardStatus()
    {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus)
    {
        this.cardStatus = cardStatus;
    }

    public String getCardAccNumber()
    {
        return cardAccNumber;
    }

    public void setCardAccNumber(String cardAccNumber)
    {
        this.cardAccNumber = cardAccNumber;
    }

    @Override
    public String toString()
    {
        return "CardDetails{" + "custId='" + custId + '\'' + ", monthDelinquency='" + monthDelinquency + '\''
                + ", currentBalance='" + currentBalance + '\'' + ", expiryDate='" + expiryDate + '\''
                + ", dateOfEnrollment='" + dateOfEnrollment + '\'' + ", cardType='" + cardType + '\'' + ", availLmt='"
                + availLmt + '\'' + ", cardStatus='" + cardStatus + '\'' + ", cardAccNumber='" + cardAccNumber + '\''
                + '}';
    }
}
