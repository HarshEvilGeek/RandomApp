package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerOpenHelper;
import com.example.zaas.pocketbanker.utils.TransactionCategoryUtils;

/**
 * DB Model for transaction categories
 * 
 * Created by adugar on 3/25/16.
 */
public class TransactionCategory extends DbModel
{
    private int id;
    private String merchantName;
    private TransactionCategoryUtils.Category category;

    public TransactionCategory()
    {
        category = TransactionCategoryUtils.Category.UNKNOWN;
    }

    public TransactionCategory(String merchantName, TransactionCategoryUtils.Category category)
    {
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

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
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
            setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.TransactionCategories._ID)));
            setMerchantName(cursor.getString(cursor
                    .getColumnIndex(PocketBankerContract.TransactionCategories.MERCHANT_NAME)));
            setCategory(TransactionCategoryUtils.Category.values()[cursor.getInt(cursor
                    .getColumnIndex(PocketBankerContract.TransactionCategories.CATEGORY))]);
        }
    }

    @Override
    public String getSelectionString()
    {
        return null;
    }

    @Override
    public String[] getSelectionValues()
    {
        return new String[0];
    }

    @Override
    public boolean isEqual(DbModel model)
    {
        return false;
    }

    @Override
    public ContentValues toContentValues()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PocketBankerContract.TransactionCategories.MERCHANT_NAME, merchantName);
        contentValues.put(PocketBankerContract.TransactionCategories.CATEGORY, category.ordinal());
        return contentValues;
    }

    @Override
    public String getTable()
    {
        return PocketBankerOpenHelper.Tables.TRANSACTION_CATEGORIES;
    }
}
