package com.example.zaas.pocketbanker.models.local;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.zaas.pocketbanker.application.PocketBankerApplication;
import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerOpenHelper;
import com.example.zaas.pocketbanker.data.PocketBankerProvider;
import com.example.zaas.pocketbanker.utils.Constants;
import com.example.zaas.pocketbanker.utils.TransactionCategoryUtils;

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
    private String merchantId;
    private String merchantName;
    private TransactionCategoryUtils.Category category;

    public static String TRANSACTION_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    public Transaction()
    {
        type = Type.DEBIT;
        category = TransactionCategoryUtils.Category.UNKNOWN;
    }

    public Transaction(String accountNumber, double amount, double balance, Type type, String remark, long time)
    {
        this(accountNumber,amount,balance,type,remark,time,"", TransactionCategoryUtils.Category.UNKNOWN);
    }

    public Transaction(String accountNumber, double amount, double balance, Type type, String remark, long time,
            String merchantName, TransactionCategoryUtils.Category category)
    {
        this.transactionId = Long.toString(time);
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.remark = remark;
        this.time = time;
        this.merchantId = "xyz";
        this.merchantName = merchantName;
        this.category = category;
    }

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
        this.transactionId = Long.toString(time);
    }

    public String getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(String merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
        this.category = TransactionCategoryUtils.getCategoryForMerchant(merchantName);
    }

    public TransactionCategoryUtils.Category getCategory()
    {
        return category;
    }

    public void setCategory(TransactionCategoryUtils.Category category)
    {
        this.category = category;
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
            setMerchantId(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Transactions.MERCHANT_ID)));
            setMerchantName(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Transactions.MERCHANT_NAME)));
            setCategory(TransactionCategoryUtils.Category.values()[cursor.getInt(cursor
                    .getColumnIndex(PocketBankerContract.Transactions.CATEGORY))]);
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
        contentValues.put(PocketBankerContract.Transactions.MERCHANT_ID, merchantId);
        contentValues.put(PocketBankerContract.Transactions.MERCHANT_NAME, merchantName);
        contentValues.put(PocketBankerContract.Transactions.CATEGORY, category.ordinal());
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
        DEBIT;

        public static Type getEnumFromNetworkType(String value)
        {
            if(Constants.TRANSACTION_TYPE_CREDIT.equals(value)) {
                return CREDIT;
            }
            else {
                return DEBIT;
            }
        }
    }

    public static List<Transaction> getLatestNTransactions(int number, String accNumber, String sortOrder)
    {
        List<Transaction> transactions = new ArrayList<>();
        String where = PocketBankerContract.Transactions.ACCOUNT_NUMBER + " = ? " ;
        Cursor c = null;
        try {
            c = PocketBankerApplication
                    .getApplication()
                    .getApplicationContext()
                    .getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_TRANSACTIONS, null, where, new String[] { accNumber }, sortOrder);
            if (c != null) {
                while (c.moveToNext() && number != 0) {
                    Transaction transaction = new Transaction();
                    transaction.instantiateFromCursor(c);
                    transactions.add(transaction);
                    number--;
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return transactions;
    }

    public static List<Transaction> getLatestTransactionsBetween(String accNumber, String sortOrder, long startTime,
            long endTime)
    {
        List<Transaction> transactions = new ArrayList<>();
        String where = PocketBankerContract.Transactions.ACCOUNT_NUMBER + " = ? AND "
                + PocketBankerContract.Transactions.TIME + " >= " + startTime + " AND "
                + PocketBankerContract.Transactions.TIME + " <= " + endTime;
        Cursor c = null;
        try {
            c = PocketBankerApplication
                    .getApplication()
                    .getApplicationContext()
                    .getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_TRANSACTIONS, null, where, new String[] { accNumber },
                            sortOrder);
            if (c != null) {
                while (c.moveToNext()) {
                    Transaction transaction = new Transaction();
                    transaction.instantiateFromCursor(c);
                    transactions.add(transaction);
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return transactions;
    }
}
