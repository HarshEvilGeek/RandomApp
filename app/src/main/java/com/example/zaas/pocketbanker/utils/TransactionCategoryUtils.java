package com.example.zaas.pocketbanker.utils;

import com.example.zaas.pocketbanker.models.local.Transaction;

/**
 * Created by adugar on 3/25/16.
 */
public class TransactionCategoryUtils
{
    public static Category getCategoryForMerchant(String merchantName)
    {
        return Category.UNKNOWN;
    }

    public static Category getCategoryForTransaction(Transaction transaction)
    {
        return Category.UNKNOWN;
    }

    public enum Category
    {
        UNKNOWN,
        TRAVEL,
        SHOPPING,
        COMMUNICATION,
        FOOD,
        HEALTH,
        HOME,
        MOVIES
    }
}
