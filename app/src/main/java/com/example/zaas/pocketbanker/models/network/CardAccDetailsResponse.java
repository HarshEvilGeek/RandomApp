package com.example.zaas.pocketbanker.models.network;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class CardAccDetailsResponse
{

    private String errorCode;

    @SerializedName ("errorDescripttion")
    private String errorDescription;

    private CardDetails[] cardDetails;

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorDescription()
    {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription)
    {
        this.errorDescription = errorDescription;
    }

    public CardDetails[] getCardDetails()
    {
        return cardDetails;
    }

    public void setCardDetails(CardDetails[] cardDetails)
    {
        this.cardDetails = cardDetails;
    }

    @Override
    public String toString()
    {
        return "CardAccDetailsResponse{" + "errorCode='" + errorCode + '\'' + ", errorDescription='" + errorDescription
                + '\'' + ", cardDetails=" + Arrays.toString(cardDetails) + '}';
    }
}
