package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.zaas.pocketbanker.data.PocketBankerContract;

/**
 * Created by akhil on 3/19/16.
 */
public class Account
{
    private int id;
    private String accountNumber;
    private double balance;
    private String type;
    private long lastUpdateTime;

    public static Account loadFromCursor(Cursor cursor)
    {
        Account account = new Account();
        account.setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.Account._ID)));
        account.setAccountNumber(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Account.ACCOUNT_NUMBER)));
        account.setType(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Account.ACCOUNT_TYPE)));
        account.setBalance(cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.Account.BALANCE)));
        account.setLastUpdateTime(cursor.getLong(cursor.getColumnIndex(PocketBankerContract.Account.TIME)));
        return account;
    }

    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(PocketBankerContract.Account.ACCOUNT_NUMBER, accountNumber);
        values.put(PocketBankerContract.Account.ACCOUNT_TYPE, type);
        values.put(PocketBankerContract.Account.BALANCE, balance);
        values.put(PocketBankerContract.Account.TIME, lastUpdateTime);
        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
