package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerOpenHelper;

/**
 * DB model for transactions
 * 
 * Created by adugar on 3/25/16.
 */
public class Transaction extends DbModel
{
    private int id;
    private String transactionId;
    private String accountNumber;
    private double amount;
    private double balance;
    private Type type;
    private String remark;
    private long time;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
        setTransactionId(Long.toString(time));
    }

    @Override
    public void instantiateFromCursor(Cursor cursor)
    {
        if (cursor != null) {
            setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.Transactions._ID)));
            setTransactionId(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Transactions.TRANSACTION_ID)));
            setAccountNumber(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Transactions.ACCOUNT_NUMBER)));
            setAmount(cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.Transactions.AMOUNT)));
            setBalance(cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.Transactions.BALANCE)));
            setType(Type.values()[cursor.getInt(cursor
                    .getColumnIndex(PocketBankerContract.Transactions.CREDIT_OR_DEBIT))]);
            setRemark(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Transactions.REMARK)));
            setTime(cursor.getLong(cursor.getColumnIndex(PocketBankerContract.Transactions.TIME)));
        }
    }

    @Override
    public String getSelectionString()
    {
        return PocketBankerContract.Transactions.TRANSACTION_ID + " = ?";
    }

    @Override
    public String[] getSelectionValues()
    {
        return new String[] { transactionId };
    }

    @Override
    public boolean isEqual(DbModel model)
    {
        return model instanceof Transaction && transactionId.equals(((Transaction) model).getTransactionId());
    }

    @Override
    public ContentValues toContentValues()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PocketBankerContract.Transactions.TRANSACTION_ID, transactionId);
        contentValues.put(PocketBankerContract.Transactions.ACCOUNT_NUMBER, accountNumber);
        contentValues.put(PocketBankerContract.Transactions.AMOUNT, amount);
        contentValues.put(PocketBankerContract.Transactions.BALANCE, balance);
        contentValues.put(PocketBankerContract.Transactions.CREDIT_OR_DEBIT, type.ordinal());
        contentValues.put(PocketBankerContract.Transactions.REMARK, remark);
        contentValues.put(PocketBankerContract.Transactions.TIME, time);
        return contentValues;
    }

    @Override
    public String getTable()
    {
        return PocketBankerOpenHelper.Tables.TRANSACTIONS;
    }

    public enum Type
    {
        CREDIT,
        DEBIT
    }
}
