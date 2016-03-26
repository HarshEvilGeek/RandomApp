package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerOpenHelper;
import com.example.zaas.pocketbanker.models.network.BranchAtmLocations;
import com.google.android.gms.maps.model.LatLng;

/**
 * Model for Branch/ATM Created by adugar on 3/19/16.
 */
public class BranchAtm extends DbModel
{
    private static final String TAG = "BranchAtm";

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

    public BranchAtm(BranchAtmLocations branchAtmLocation)
    {
        name = branchAtmLocation.getBranchname();
        address = branchAtmLocation.getAddress();
        city = branchAtmLocation.getCity();
        state = branchAtmLocation.getState();
        ifscCode = branchAtmLocation.getIfscCode();
        flag = branchAtmLocation.getFlag();
        if (Type.ATM.name().equalsIgnoreCase(branchAtmLocation.getType())) {
            type = Type.ATM;
        }
        else {
            type = Type.BRANCH;
        }
        phoneNumber = branchAtmLocation.getPhoneno();
        try {
            double latitude = Double.parseDouble(branchAtmLocation.getLatitude());
            double longitude = Double.parseDouble(branchAtmLocation.getLongitude());
            mapLocation = new LatLng(latitude, longitude);
        }
        catch (Exception e) {
            // Do nothing
        }
    }

    @Override
    public void instantiateFromCursor(Cursor cursor)
    {
        if (cursor != null) {
            setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.BranchAtms._ID)));
            setName(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.BRANCH_NAME)));
            setAddress(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.ADDRESS)));
            setCity(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.CITY)));
            setState(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.STATE)));
            setIfscCode(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.IFSC_CODE)));
            setFlag(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.FLAG)));
            setType(Type.values()[cursor.getInt(cursor.getColumnIndex(PocketBankerContract.BranchAtms.TYPE))]);
            setPhoneNumber(cursor.getString(cursor.getColumnIndex(PocketBankerContract.BranchAtms.PHONE_NUMBER)));
            double latitude = cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.BranchAtms.LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.BranchAtms.LONGITUDE));
            setMapLocation(new LatLng(latitude, longitude));
            return;
        }
        Log.e(TAG, "Null cursor passed to instantiate with");
    }

    @Override
    public String getSelectionString()
    {
        return PocketBankerContract.BranchAtms.ADDRESS + " = ? AND " + PocketBankerContract.BranchAtms.TYPE + " = ?";
    }

    @Override
    public String[] getSelectionValues()
    {
        return new String[] { address, String.valueOf(type.ordinal()) };
    }

    @Override
    public boolean isEqual(DbModel model)
    {
        return model instanceof BranchAtm && (address.equals(((BranchAtm) model).getAddress()))
                && (type == ((BranchAtm) model).getType());
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

    @Override
    public String getTable()
    {
        return PocketBankerOpenHelper.Tables.BRANCH_ATMS;
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
