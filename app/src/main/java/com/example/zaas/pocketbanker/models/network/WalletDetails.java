package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/26/16.
 */
public class WalletDetails
{

    private String creationStatus;
    @SerializedName ("auth_data")
    private String authData;

    public String getCreationStatus()
    {
        return creationStatus;
    }

    public void setCreationStatus(String creationStatus)
    {
        this.creationStatus = creationStatus;
    }

    public String getAuthData()
    {
        return authData;
    }

    public void setAuthData(String authData)
    {
        this.authData = authData;
    }

    @Override
    public String toString()
    {
        return "WalletDetails{" + "creationStatus='" + creationStatus + '\'' + ", authData='" + authData + '\'' + '}';
    }
}
