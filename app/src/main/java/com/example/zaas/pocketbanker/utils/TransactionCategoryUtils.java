package com.example.zaas.pocketbanker.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.content.ContentValues;
import android.text.TextUtils;

import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerDBHelper;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionCategory;

/**
 * Created by adugar on 3/25/16.
 */
public class TransactionCategoryUtils
{
    private static final List<String> RANDOM_MERCHANTS = Arrays.asList("yatra", "irctc", "uber", "indigo", "puma",
            "arrow", "elle", "airtel", "idea", "pizzahut", "mcdonals", "fortis", "apollo", "pvr", "inox", "random1",
            "random2", "random3", "random4", "random5");
    private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    private static final String DUMMY_ACCOUNT_NUMBER_1 = "120938029383";
    private static Map<String, Category> sTransactionCategoryMap;

    public static String getRandomMerchant()
    {
        Random r = new Random();
        return RANDOM_MERCHANTS.get(r.nextInt(20));
    }

    public static Category getCategoryForTransaction(Transaction transaction)
    {
        return getCategoryForMerchant(transaction.getMerchantName());
    }

    public static Category getCategoryForMerchant(String merchantName)
    {
        if (TextUtils.isEmpty(merchantName)) {
            return Category.UNKNOWN;
        }

        merchantName = merchantName.toLowerCase().replaceAll(" ", "").replaceAll("'", "");
        if (sTransactionCategoryMap != null && sTransactionCategoryMap.containsKey(merchantName)) {
            return sTransactionCategoryMap.get(merchantName);
        }
        return Category.UNKNOWN;
    }

    public static void updateCategoryForTransaction(Transaction transaction, Category category)
    {
        String merchantName = transaction.getMerchantName();
        if (TextUtils.isEmpty(merchantName) || category == Category.UNKNOWN) {
            return;
        }

        merchantName = merchantName.toLowerCase().replaceAll(" ", "").replaceAll("'", "");
        if (sTransactionCategoryMap != null) {
            sTransactionCategoryMap.put(merchantName, category);
        }

        PocketBankerDBHelper.getInstance().insertTransactionCategory(new TransactionCategory(merchantName, category));

        ContentValues cv = new ContentValues();
        cv.put(PocketBankerContract.Transactions.CATEGORY, category.ordinal());
        PocketBankerDBHelper.getInstance().updateTransaction(transaction.getId(), cv);
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

    public static List<Transaction> getAllTransactions()
    {
        Transaction transaction1 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 1200, 1200000, Transaction.Type.Debit,
                "House Rent", System.currentTimeMillis() - 30 * ONE_DAY_IN_MILLIS, "House Rent",
                TransactionCategoryUtils.Category.UNKNOWN);
        Transaction transaction2 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 800, 1200000, Transaction.Type.Debit,
                "Conveyance", System.currentTimeMillis() - 20 * ONE_DAY_IN_MILLIS, "Uber",
                TransactionCategoryUtils.Category.TRAVEL);
        Transaction transaction3 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 3000, 1200000, Transaction.Type.Debit,
                "Shopping", System.currentTimeMillis() - 15 * ONE_DAY_IN_MILLIS, "Puma",
                TransactionCategoryUtils.Category.SHOPPING);
        Transaction transaction4 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 900, 1200000, Transaction.Type.Debit,
                "Movies", System.currentTimeMillis() - 12 * ONE_DAY_IN_MILLIS, "PVR",
                TransactionCategoryUtils.Category.MOVIES);
        Transaction transaction5 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 2600, 1200000, Transaction.Type.Debit,
                "Dinner", System.currentTimeMillis() - 10 * ONE_DAY_IN_MILLIS, "Pizza Hut",
                TransactionCategoryUtils.Category.FOOD);
        Transaction transaction6 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 1350, 1200000, Transaction.Type.Debit,
                "Lunch", System.currentTimeMillis() - 7 * ONE_DAY_IN_MILLIS, "Taco Bell",
                TransactionCategoryUtils.Category.FOOD);
        Transaction transaction7 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 250, 1200000, Transaction.Type.Credit,
                "Debt Settlement", System.currentTimeMillis() - 5 * ONE_DAY_IN_MILLIS, "Loan",
                TransactionCategoryUtils.Category.UNKNOWN);
        Transaction transaction8 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 450, 1200000, Transaction.Type.Debit,
                "Taxi", System.currentTimeMillis() - 3 * ONE_DAY_IN_MILLIS, "Ola",
                TransactionCategoryUtils.Category.TRAVEL);
        Transaction transaction9 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 1000, 1200000, Transaction.Type.Credit,
                "Doctor", System.currentTimeMillis() - ONE_DAY_IN_MILLIS, "Fortis",
                TransactionCategoryUtils.Category.HEALTH);
        Transaction transaction10 = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 3500, 1200000, Transaction.Type.Debit,
                "Gift", System.currentTimeMillis(), "Archies", TransactionCategoryUtils.Category.UNKNOWN);
        List<Transaction> dummyTransactions = new ArrayList<>();
        dummyTransactions.add(transaction1);
        dummyTransactions.add(transaction2);
        dummyTransactions.add(transaction3);
        dummyTransactions.add(transaction4);
        dummyTransactions.add(transaction5);
        dummyTransactions.add(transaction6);
        dummyTransactions.add(transaction7);
        dummyTransactions.add(transaction8);
        dummyTransactions.add(transaction9);
        dummyTransactions.add(transaction10);

        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(PocketBankerDBHelper.getInstance().getAllTransactions());

        // Comment this line when dummy transactions are no longer required
        allTransactions.addAll(dummyTransactions);

        return allTransactions;
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
