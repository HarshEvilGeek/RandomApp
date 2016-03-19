package com.example.zaas.pocketbanker.models.local;

import com.google.android.gms.maps.model.LatLng;

/**
 * Model for Branch/ATM Created by adugar on 3/19/16.
 */
public class BranchAtm
{
    private String name;
    private String address;
    private String city;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public LatLng getMapLocation() {
        return mapLocation;
    }

    public void setMapLocation(LatLng mapLocation) {
        this.mapLocation = mapLocation;
    }

    public enum Type
    {
        BRANCH,
        ATM
    }
}
