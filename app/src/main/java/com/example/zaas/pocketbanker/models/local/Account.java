package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.zaas.pocketbanker.application.PocketBankerApplication;
import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerOpenHelper;
import com.example.zaas.pocketbanker.data.PocketBankerProvider;
import com.example.zaas.pocketbanker.models.network.AccountSummary;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by akhil on 3/19/16.
 */
public class Account extends DbModel
{
    private static final String LOG_TAG = Account.class.getSimpleName();

    private int id;
    private String accountNumber;
    private double balance;
    private String type;
    private long lastUpdateTime;

    public Account()
    {

    }

    public static Account getAccount(AccountSummary accountSummary)
    {
        return new Account(accountSummary.getAccountno(), accountSummary.getBalance(), accountSummary.getAccounttype(),
                System.currentTimeMillis());
    }

    public Account(String accountNumber, double balance, String type, long lastUpdateTime)
    {
        //TODO : hardcoded
        this.accountNumber = Constants.BANK_ACCOUNT_NUMBER;
        this.balance = balance;
        this.type = type;
        this.lastUpdateTime = lastUpdateTime;

    }

    @Override
    public void instantiateFromCursor(Cursor cursor)
    {
        if (cursor != null) {
            setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.Account._ID)));
            setAccountNumber(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Account.ACCOUNT_NUMBER)));
            setType(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Account.ACCOUNT_TYPE)));
            setBalance(cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.Account.BALANCE)));
            setLastUpdateTime(cursor.getLong(cursor.getColumnIndex(PocketBankerContract.Account.TIME)));
        }
    }

    @Override
    public String getSelectionString()
    {
        return PocketBankerContract.Account.ACCOUNT_NUMBER + " = ?";
    }

    @Override
    public String[] getSelectionValues()
    {
        return new String[] { accountNumber };
    }

    @Override
    public boolean isEqual(DbModel model)
    {
        return model instanceof Account && accountNumber.equals(((Account) model).getAccountNumber());
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

    @Override
    public String getTable()
    {
        return PocketBankerOpenHelper.Tables.ACCOUNTS;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public long getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static Account getAccount(String accountNo)
    {
        Account account = null;
        Cursor c = null;
        try {
            c = PocketBankerApplication
                    .getApplication()
                    .getApplicationContext()
                    .getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_ACCOUNTS, null,
                            PocketBankerContract.Account.ACCOUNT_NUMBER + " = ? ", new String[] { accountNo }, null);
            if (c != null && c.moveToNext()) {
                account = new Account();
                account.instantiateFromCursor(c);
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return account;
    }

    public static void insertOrUpdateAccount(Account account)
    {
        if (account == null) {
            return;
        }

        Account existingAccount = getAccount(account.getAccountNumber());
        try {
            if (existingAccount == null) {
                Uri id = PocketBankerApplication.getApplication().getContentResolver()
                        .insert(PocketBankerProvider.CONTENT_URI_ACCOUNTS, account.toContentValues());
                if (id != null) {
                    Log.i(LOG_TAG, "Insertion succeeded for account details ");
                }
                else {
                    Log.i(LOG_TAG, "Insertion failed for account details ");
                }
            }
            else {

                long id = PocketBankerApplication
                        .getApplication()
                        .getContentResolver()
                        .update(PocketBankerProvider.CONTENT_URI_ACCOUNTS, account.toContentValues(),
                                PocketBankerContract.Account.ACCOUNT_NUMBER + " = ? ",
                                new String[] { existingAccount.getAccountNumber() });
                if (id != -1) {
                    Log.i(LOG_TAG, "updation succeeded for account details : " + id);
                }
                else {
                    Log.i(LOG_TAG, "updation failed for account details ");
                }

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while inserting account details : ", e);
        }
    }
}
