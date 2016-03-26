package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/26/16.
 */
public class WalletCreditDebitBody
{

    @SerializedName ("id_type")
    private String idType;

    @SerializedName ("id_value")
    private String idValue;

    @SerializedName ("auth_type")
    private String authType;

    @SerializedName ("auth_data")
    private String authData;

    @SerializedName ("txn_id")
    private long transactionId;

    private double amount;

    private String promocode;

    private String remarks;

    @SerializedName ("sub_merchant")
    private String subMerchant;

    private double latitude;

    private double longitude;

    private String imei;

    @SerializedName ("device_id")
    private String deviceId;

    @SerializedName ("ip_address")
    private String ipAddress;

    private String os;

    private String clientID;

    private String authToken;

    public String getIdType()
    {
        return idType;
    }

    public void setIdType(String idType)
    {
        this.idType = idType;
    }

    public String getIdValue()
    {
        return idValue;
    }

    public void setIdValue(String idValue)
    {
        this.idValue = idValue;
    }

    public String getAuthType()
    {
        return authType;
    }

    public void setAuthType(String authType)
    {
        this.authType = authType;
    }

    public String getAuthData()
    {
        return authData;
    }

    public void setAuthData(String authData)
    {
        this.authData = authData;
    }

    public long getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(long transactionId)
    {
        this.transactionId = transactionId;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getPromocode()
    {
        return promocode;
    }

    public void setPromocode(String promocode)
    {
        this.promocode = promocode;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getSubMerchant()
    {
        return subMerchant;
    }

    public void setSubMerchant(String subMerchant)
    {
        this.subMerchant = subMerchant;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getImei()
    {
        return imei;
    }

    public void setImei(String imei)
    {
        this.imei = imei;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getOs()
    {
        return os;
    }

    public void setOs(String os)
    {
        this.os = os;
    }

    public String getClientID()
    {
        return clientID;
    }

    public void setClientID(String clientID)
    {
        this.clientID = clientID;
    }

    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    @Override
    public String toString()
    {
        return "WalletCreditDebitBody{" + "idType='" + idType + '\'' + ", idValue='" + idValue + '\'' + ", authType='"
                + authType + '\'' + ", authData='" + authData + '\'' + ", transactionId=" + transactionId + ", amount="
                + amount + ", promocode='" + promocode + '\'' + ", remarks='" + remarks + '\'' + ", subMerchant='"
                + subMerchant + '\'' + ", latitude=" + latitude + ", longitude=" + longitude + ", imei='" + imei + '\''
                + ", deviceId='" + deviceId + '\'' + ", ipAddress='" + ipAddress + '\'' + ", os='" + os + '\''
                + ", clientID='" + clientID + '\'' + ", authToken='" + authToken + '\'' + '}';
    }
}
