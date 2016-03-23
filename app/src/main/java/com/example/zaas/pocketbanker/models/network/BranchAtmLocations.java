package com.example.zaas.pocketbanker.models.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class BranchAtmLocations
{

    private String pincode;

    @SerializedName ("Type")
    private String type;
    private String flag;
    private String address;
    private String latitude;

    @SerializedName ("IFSC_CODE")
    private String ifscCode;
    private String city;
    private String state;
    private String phoneno;
    private String longitude;
    private String branchname;

    public String getPincode()
    {
        return pincode;
    }

    public void setPincode(String pincode)
    {
        this.pincode = pincode;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getIfscCode()
    {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode)
    {
        this.ifscCode = ifscCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getPhoneno()
    {
        return phoneno;
    }

    public void setPhoneno(String phoneno)
    {
        this.phoneno = phoneno;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getBranchname()
    {
        return branchname;
    }

    public void setBranchname(String branchname)
    {
        this.branchname = branchname;
    }

    @Override
    public String toString()
    {
        return "BranchAtmLocations{" + "pincode='" + pincode + '\'' + ", type='" + type + '\'' + ", flag='" + flag
                + '\'' + ", address='" + address + '\'' + ", latitude='" + latitude + '\'' + ", ifscCode='" + ifscCode
                + '\'' + ", city='" + city + '\'' + ", state='" + state + '\'' + ", phoneno='" + phoneno + '\''
                + ", longitude='" + longitude + '\'' + ", branchname='" + branchname + '\'' + '}';
    }
}
