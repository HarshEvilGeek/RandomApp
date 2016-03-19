package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.google.android.gms.maps.model.LatLng;

/**
 * Model for Branch/ATM Created by adugar on 3/19/16.
 */
public class BranchAtm
{
    private int id;
    private String name;
    private String flag;
    private String address;
    private String city;
    private String state;
    private String ifscCode;
    private String phoneNumber;
    private Type type;
    private LatLng mapLocation;

    public BranchAtm()
    {
        this.type = Type.BRANCH;
    }

    public BranchAtm(String name, String address, String city, Type type, LatLng mapLocation)
    {
        this.name = name;
        this.address = address;
        this.city = city;
        this.type = type;
        this.mapLocation = mapLocation;
    }

    public static BranchAtm loadFromCursor(Cursor cursor)
    {
        BranchAtm branchAtm = new BranchAtm();
        branchAtm.setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.BranchAtms._ID)));
        branchAtm.setName(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.BRANCH_NAME)));
        branchAtm.setAddress(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.ADDRESS)));
        branchAtm.setCity(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.CITY)));
        branchAtm.setState(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.STATE)));
        branchAtm.setIfscCode(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.IFSC_CODE)));
        branchAtm.setFlag(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.FLAG)));
        branchAtm.setType(Type.values()[cursor.getInt(cursor.getColumnIndex(PocketBankerContract.BranchAtms.TYPE))]);
        branchAtm.setPhoneNumber(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.PHONE_NUMBER)));
        double latitude = cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.BranchAtms.LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.BranchAtms.LONGITUDE));
        branchAtm.setMapLocation(new LatLng(latitude, longitude));
        return branchAtm;
    }

    public ContentValues toContentValues()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PocketBankerContract.BranchAtms.BRANCH_NAME, name);
        contentValues.put(PocketBankerContract.BranchAtms.ADDRESS, address);
        contentValues.put(PocketBankerContract.BranchAtms.CITY, city);
        contentValues.put(PocketBankerContract.BranchAtms.STATE, state);
        contentValues.put(PocketBankerContract.BranchAtms.LATITUDE, mapLocation.latitude);
        contentValues.put(PocketBankerContract.BranchAtms.LONGITUDE, mapLocation.longitude);
        contentValues.put(PocketBankerContract.BranchAtms.IFSC_CODE, ifscCode);
        contentValues.put(PocketBankerContract.BranchAtms.FLAG, flag);
        contentValues.put(PocketBankerContract.BranchAtms.TYPE, type.ordinal());
        contentValues.put(PocketBankerContract.BranchAtms.PHONE_NUMBER, phoneNumber);
        return contentValues;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag)
    {
        this.flag = flag;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getIfscCode()
    {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode)
    {
        this.ifscCode = ifscCode;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public LatLng getMapLocation()
    {
        return mapLocation;
    }

    public void setMapLocation(LatLng mapLocation)
    {
        this.mapLocation = mapLocation;
    }

    public enum Type
    {
        BRANCH,
        ATM
    }
}
