package com.example.zaas.pocketbanker.models.network;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/26/16.
 */
public class WalletCreation
{
    private String errorCode;
    private String errorDescripttion;
    @SerializedName ("WalletDetails")
    private WalletDetails[] walletDetails;

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

    public WalletDetails[] getWalletDetails()
    {
        return walletDetails;
    }

    public void setWalletDetails(WalletDetails[] walletDetails)
    {
        this.walletDetails = walletDetails;
    }

    @Override
    public String toString()
    {
        return "WalletCreation{" + "errorCode='" + errorCode + '\'' + ", errorDescripttion='" + errorDescripttion
                + '\'' + ", walletDetails=" + Arrays.toString(walletDetails) + '}';
    }
}
