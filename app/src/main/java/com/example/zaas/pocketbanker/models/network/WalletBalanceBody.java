package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/26/16.
 */
public class WalletBalanceBody
{

    @SerializedName ("id_type")
    private String idType;

    @SerializedName ("id_value")
    private String idValue;

    @SerializedName ("auth_type")
    private String authType;

    @SerializedName ("auth_data")
    private String authData;

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

    public WalletBalanceBody(String walletAuthData, double lat, double longitude, String imei, String os, String ipAddress, String deviceId, String clientId, String authToken)
    {
        this.idType = "TOKEN";
        this.idValue = walletAuthData;
        this.authType = "TOKEN";
        this.authData = walletAuthData;
        this.latitude = lat;
        this.longitude = longitude;
        this.imei = imei;
        this.deviceId = deviceId;
        this.os = os;
        this.ipAddress = ipAddress;
        this.clientID = clientId;
        this.authToken = authToken;

    }

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
        return "WalletBalanceBody{" + "idType='" + idType + '\'' + ", idValue='" + idValue + '\'' + ", authType='"
                + authType + '\'' + ", authData='" + authData + '\'' + ", latitude=" + latitude + ", longitude="
                + longitude + ", imei='" + imei + '\'' + ", deviceId='" + deviceId + '\'' + ", ipAddress='" + ipAddress
                + '\'' + ", os='" + os + '\'' + ", clientID='" + clientID + '\'' + ", authToken='" + authToken + '\''
                + '}';
    }
}
