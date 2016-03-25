package com.example.zaas.pocketbanker.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionCategory;

/**
 * Created by adugar on 3/25/16.
 */
public class TransactionCategoryUtils
{
    private static Map<String, Category> sTransactionCategoryMap;

    public static Category getCategoryForTransaction(Transaction transaction)
    {
        return getCategoryForMerchant(transaction.getMerchantName());
    }

    public static Category getCategoryForMerchant(String merchantName)
    {
        merchantName = merchantName.toLowerCase().replaceAll(" ", "").replaceAll("'", "");
        if (sTransactionCategoryMap != null && TextUtils.isEmpty(merchantName)
                && sTransactionCategoryMap.containsKey(merchantName)) {
            return sTransactionCategoryMap.get(merchantName);
        }
        return Category.UNKNOWN;
    }

    public static List<TransactionCategory> getInitialTransactionCategories()
    {
        List<TransactionCategory> list = new ArrayList<>();

        // Travel merchants
        list.add(new TransactionCategory("yatra", Category.TRAVEL));
        list.add(new TransactionCategory("makemytrip", Category.TRAVEL));
        list.add(new TransactionCategory("akbartravels", Category.TRAVEL));
        list.add(new TransactionCategory("ibibo", Category.TRAVEL));
        list.add(new TransactionCategory("indigo", Category.TRAVEL));
        list.add(new TransactionCategory("jetairways", Category.TRAVEL));
        list.add(new TransactionCategory("spicejet", Category.TRAVEL));
        list.add(new TransactionCategory("airindia", Category.TRAVEL));
        list.add(new TransactionCategory("aircosta", Category.TRAVEL));
        list.add(new TransactionCategory("airasia", Category.TRAVEL));
        list.add(new TransactionCategory("irctc", Category.TRAVEL));
        list.add(new TransactionCategory("redbus", Category.TRAVEL));
        list.add(new TransactionCategory("bookmybus", Category.TRAVEL));
        list.add(new TransactionCategory("uber", Category.TRAVEL));
        list.add(new TransactionCategory("ola", Category.TRAVEL));
        list.add(new TransactionCategory("taxiforsure", Category.TRAVEL));

        // Shopping merchants
        list.add(new TransactionCategory("shoppersstop", Category.SHOPPING));
        list.add(new TransactionCategory("lifestyle", Category.SHOPPING));
        list.add(new TransactionCategory("westside", Category.SHOPPING));
        list.add(new TransactionCategory("puma", Category.SHOPPING));
        list.add(new TransactionCategory("adidas", Category.SHOPPING));
        list.add(new TransactionCategory("nike", Category.SHOPPING));
        list.add(new TransactionCategory("levis", Category.SHOPPING));
        list.add(new TransactionCategory("wrangler", Category.SHOPPING));
        list.add(new TransactionCategory("lee", Category.SHOPPING));
        list.add(new TransactionCategory("unitedcolorsofbenetton", Category.SHOPPING));
        list.add(new TransactionCategory("arrow", Category.SHOPPING));
        list.add(new TransactionCategory("raymond", Category.SHOPPING));
        list.add(new TransactionCategory("peterengland", Category.SHOPPING));
        list.add(new TransactionCategory("vanheusen", Category.SHOPPING));
        list.add(new TransactionCategory("elle", Category.SHOPPING));
        list.add(new TransactionCategory("forevernew", Category.SHOPPING));
        list.add(new TransactionCategory("mango", Category.SHOPPING));

        // Communication merchants
        list.add(new TransactionCategory("airtel", Category.COMMUNICATION));
        list.add(new TransactionCategory("vodafone", Category.COMMUNICATION));
        list.add(new TransactionCategory("idea", Category.COMMUNICATION));
        list.add(new TransactionCategory("reliance", Category.COMMUNICATION));
        list.add(new TransactionCategory("tatadocomo", Category.COMMUNICATION));
        list.add(new TransactionCategory("bsnl", Category.COMMUNICATION));
        list.add(new TransactionCategory("act", Category.COMMUNICATION));

        // Food merchants
        list.add(new TransactionCategory("pizzahut", Category.FOOD));
        list.add(new TransactionCategory("dominos", Category.FOOD));
        list.add(new TransactionCategory("papajohns", Category.FOOD));
        list.add(new TransactionCategory("mcdonalds", Category.FOOD));
        list.add(new TransactionCategory("burgerking", Category.FOOD));
        list.add(new TransactionCategory("tacobell", Category.FOOD));
        list.add(new TransactionCategory("kobesizzlers", Category.FOOD));

        // Health merchants
        list.add(new TransactionCategory("fortis", Category.HEALTH));
        list.add(new TransactionCategory("apollo", Category.HEALTH));

        // Movies merchants
        list.add(new TransactionCategory("pvr", Category.HEALTH));
        list.add(new TransactionCategory("inox", Category.HEALTH));
        list.add(new TransactionCategory("imax", Category.HEALTH));
        list.add(new TransactionCategory("fame", Category.HEALTH));

        return list;
    }

    public static void populateTransactionCategoryMap()
    {
        sTransactionCategoryMap = new HashMap<>();
        List<TransactionCategory> transactionCategoryList = PocketBankerDBHelper.getInstance()
                .getAllTransactionCategories();
        for (TransactionCategory transactionCategory : transactionCategoryList) {
            sTransactionCategoryMap.put(transactionCategory.getMerchantName(), transactionCategory.getCategory());
        }
    }

    public enum Category
    {
        UNKNOWN,
        TRAVEL,
        SHOPPING,
        COMMUNICATION,
        FOOD,
        HEALTH,
        MOVIES
    }
}
