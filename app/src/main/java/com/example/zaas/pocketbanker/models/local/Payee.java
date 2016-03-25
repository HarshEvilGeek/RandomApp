package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerOpenHelper;

/**
 * Model for Payee Created by adugar on 3/19/16.
 */
public class Payee extends DbModel
{
    private static final String TAG = "Payee";

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

    @Override
    public void instantiateFromCursor(Cursor cursor) {
        if (cursor != null) {
            setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.Payees._ID)));
            setPayeeId(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.PAYEE_ID)));
            setName(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.PAYEE_NAME)));
            setAccountNo(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.ACCOUNT_NUMBER)));
            setShortName(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.SHORT_NAME)));
            setCreationDate(cursor.getLong(cursor.getColumnIndex(PocketBankerContract.Payees.CREATION_DATE)));
            setCustomerId(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Payees.CUST_ID)));
            return;
        }
        Log.e(TAG, "Null cursor passed to instantatiate with");
    }

    @Override
    public String getSelectionString() {
        return PocketBankerContract.Payees.PAYEE_ID + " = ?";
    }

    @Override
    public String[] getSelectionValues() {
        return new String[] {payeeId};
    }

    @Override
    public boolean isEqual(DbModel model) {
        return model instanceof Payee && payeeId.equals(((Payee) model).getPayeeId());
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

    @Override
    public String getTable() {
        return PocketBankerOpenHelper.Tables.PAYEES;
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