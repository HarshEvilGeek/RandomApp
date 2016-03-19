package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.zaas.pocketbanker.data.PocketBankerContract;

/**
 * Model for Payee Created by adugar on 3/19/16.
 */
public class Payee
{
    private int id;
    private String payeeId;
    private String name;
    private String accountNo;
    private String shortName;
    private long creationDate;
    private String customerId;

    public Payee()
    {

    }

    public Payee(String payeeId, String name, String accountNo, String shortName, long creationDate, String customerId)
    {
        this.payeeId = payeeId;
        this.name = name;
        this.accountNo = accountNo;
        this.shortName = shortName;
        this.creationDate = creationDate;
        this.customerId = customerId;
    }

    public static Payee loadFromCursor(Cursor cursor)
    {
        Payee payee = new Payee();
        payee.setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.Payees._ID)));
        payee.setPayeeId(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.PAYEE_ID)));
        payee.setName(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.PAYEE_NAME)));
        payee.setAccountNo(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.ACCOUNT_NUMBER)));
        payee.setShortName(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.SHORT_NAME)));
        payee.setCreationDate(cursor.getLong(cursor.getColumnIndex(PocketBankerContract.Payees.CREATION_DATE)));
        payee.setCustomerId(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.CUST_ID)));
        return payee;
    }

    public ContentValues toContentValues()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PocketBankerContract.Payees.PAYEE_ID, payeeId);
        contentValues.put(PocketBankerContract.Payees.PAYEE_NAME, name);
        contentValues.put(PocketBankerContract.Payees.ACCOUNT_NUMBER, accountNo);
        contentValues.put(PocketBankerContract.Payees.SHORT_NAME, shortName);
        contentValues.put(PocketBankerContract.Payees.CUST_ID, customerId);
        contentValues.put(PocketBankerContract.Payees.CREATION_DATE, creationDate);
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

    public String getPayeeId()
    {
        return payeeId;
    }

    public void setPayeeId(String payeeId)
    {
        this.payeeId = payeeId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }

    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public long getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(long creationDate)
    {
        this.creationDate = creationDate;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
}