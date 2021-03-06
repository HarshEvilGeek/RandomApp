package com.example.zaas.pocketbanker.data;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.example.zaas.pocketbanker.models.local.Payee;
import com.example.zaas.pocketbanker.models.local.Recommendation;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionCategory;
import com.example.zaas.pocketbanker.utils.TransactionCategoryUtils;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by akhil on 3/19/16.
 */
public class PocketBankerOpenHelper extends SQLiteOpenHelper
{

    private static final String TAG = "PocketBankerOpenHelper";

    private static final String DATABASE_NAME = "pocketbanker.db";
    private static final int DATABASE_VERSION_1 = 1; // Version 1 released April 2016
    private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    private static final String DUMMY_ACCOUNT_NUMBER_1 = "120938029383";
    private static final String DUMMY_ACCOUNT_NUMBER_2 = "512398120938";
    private static final String DUMMY_ACCOUNT_NUMBER_3 = "904850982322";

    public PocketBankerOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Tables.CREATE_TABLE_ACCOUNTS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_TRANSACTIONS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_PAYEES_QUERY);
        db.execSQL(Tables.CREATE_TABLE_BRANCH_ATMS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_CARDS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_LOANS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_EMIS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_LOAN_TRANSACTIONS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_TRANSACTION_CATEGORIES_QUERY);
        db.execSQL(Tables.CREATE_TABLE_RECOMMENDATIONS_QUERY);

        insertInitialTransactionCategories(db);
        insertRecommendations(db);

        // Only for testing
        insertDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    private void insertInitialTransactionCategories(SQLiteDatabase db)
    {
        List<TransactionCategory> transactionCategoryList = TransactionCategoryUtils.getInitialTransactionCategories();
        for (TransactionCategory transactionCategory : transactionCategoryList) {
            db.insert(Tables.TRANSACTION_CATEGORIES, null, transactionCategory.toContentValues());
        }
    }

    private void insertRecommendations(SQLiteDatabase db)
    {
        Recommendation recommendation = new Recommendation("1",
                "Zoom off with 20% discount on Self Drive cars on zoomcar.com",
                "http://www.mpower2015.com/images/pastSponsers/currentSponsors/zoomcar.jpg",
                "http://www.icicibank.com/offers/zoom-car-offer.page", TransactionCategoryUtils.Category.TRAVEL,
                "Offer for ICICI Bank Credit and Debit Card");
        db.insert(Tables.RECOMMENDATIONS, null, recommendation.toContentValues());
        recommendation = new Recommendation("2", "Get 15% discount at TGI Fridays",
                "http://thewebhunter.se/wp-content/uploads/2013/03/t-g-i-f.jpg",
                "http://www.icicibank.com/offers/culinary-treats-tgif-offer.page",
                TransactionCategoryUtils.Category.FOOD, "Culinary treats from ICICI Bank Cards");
        db.insert(Tables.RECOMMENDATIONS, null, recommendation.toContentValues());
        recommendation = new Recommendation(
                "3",
                "Use Internet Banking and get \u20B9100 off on Flipkart",
                "https://pbs.twimg.com/profile_images/378800000043109980/af33c8d83e946c83563135cecbab46f6_400x400.jpeg",
                "http://www.icicibank.com/offers/flipkart-discount-on-mobile-banking-offer.page",
                TransactionCategoryUtils.Category.SHOPPING, "Shopping deals especially for you");
        db.insert(Tables.RECOMMENDATIONS, null, recommendation.toContentValues());
        recommendation = new Recommendation("4", "Book a holiday and get \u20B95000 off per person",
                "https://sotcholidaysofindia.files.wordpress.com/2013/02/cropped-sotc.jpg",
                "http://www.icicibank.com/offers/sotc-holiday-offer.page", TransactionCategoryUtils.Category.SHOPPING,
                "Internet banking offer");
        db.insert(Tables.RECOMMENDATIONS, null, recommendation.toContentValues());
        recommendation = new Recommendation("5", "Get Pockets card and enjoy 15% off at CCD",
                "http://2.bp.blogspot.com/-rlA9r-JE42Y/VoKBM1h8SRI/AAAAAAAAAMg/fob5IZozCmQ/s1600/pocket.jpg",
                "http://www.icicibank.com/offers/pockets-ccd-offer.page", TransactionCategoryUtils.Category.SHOPPING,
                "Expires on 31-12-2016");
        db.insert(Tables.RECOMMENDATIONS, null, recommendation.toContentValues());
        recommendation = new Recommendation("6",
                "Get up to \u20B91000 cashback on domestic flight bookings at MakeMyTrip",
                "http://www.giftxoxo.com/image/data/Gift%20Voucher/Vouchers_big/E-vouchers/makemytrip.jpg",
                "http://www.icicibank.com/offers/makemytrip-domestic-flight-offer.page",
                TransactionCategoryUtils.Category.TRAVEL, "Travel deals especially for you");
        db.insert(Tables.RECOMMENDATIONS, null, recommendation.toContentValues());
        recommendation = new Recommendation("6", "Get \u20B9100 cashback at IRCTC",
                "http://allupdates.in/wp-content/uploads/2015/11/irctc-login.jpg",
                "http://www.icicibank.com/offers/irctc-100-cashback.page", TransactionCategoryUtils.Category.TRAVEL,
                "Travel deals especially for you");
        db.insert(Tables.RECOMMENDATIONS, null, recommendation.toContentValues());
        recommendation = new Recommendation("7", "Get 30% off at bigbasket",
                "http://www.indiaretailing.com/wp-content/uploads/2016/03/bigbasket.jpg",
                "http://www.icicibank.com/offers/bigbasket-offer.page", TransactionCategoryUtils.Category.SHOPPING,
                "Use ICICI Bank Internet Banking, Credit or Debit Card");
        db.insert(Tables.RECOMMENDATIONS, null, recommendation.toContentValues());
    }

    private void insertDummyData(SQLiteDatabase db)
    {
        // insertDummyAccountData(db);
        // insertDummyPayeeData(db);
        // insertDummyBranchAtmData(db);
        // insertDummyTransactionData(db);
    }

    private void insertDummyAccountData(SQLiteDatabase db)
    {
        Account account = new Account();
        account.setAccountNumber(DUMMY_ACCOUNT_NUMBER_1);
        account.setBalance(1230500);
        account.setType("Savings");
        account.setLastUpdateTime(System.currentTimeMillis());
        db.insert(Tables.ACCOUNTS, null, account.toContentValues());
        account.setAccountNumber(DUMMY_ACCOUNT_NUMBER_2);
        account.setBalance(3243000);
        account.setType("Current");
        account.setLastUpdateTime(System.currentTimeMillis());
        db.insert(Tables.ACCOUNTS, null, account.toContentValues());
        account.setAccountNumber(DUMMY_ACCOUNT_NUMBER_3);
        account.setBalance(4905832);
        account.setType("Savings");
        account.setLastUpdateTime(System.currentTimeMillis());
        db.insert(Tables.ACCOUNTS, null, account.toContentValues());
    }

    public void insertDummyPayeeData(SQLiteDatabase db)
    {
        Payee payee = new Payee("1", "Akhil Verghese", "5100075", "Stud", System.currentTimeMillis() - 10
                * ONE_DAY_IN_MILLIS, "AKH075");
        db.insert(Tables.PAYEES, null, payee.toContentValues());
        payee = new Payee("1", "Akshay Dugar", "510110", "Agz", System.currentTimeMillis() - 7 * ONE_DAY_IN_MILLIS,
                "AKI110");
        db.insert(Tables.PAYEES, null, payee.toContentValues());
        payee = new Payee("1", "Shayoni Seth", "510245", "Shy", System.currentTimeMillis() - 5 * ONE_DAY_IN_MILLIS,
                "SHY245");
        db.insert(Tables.PAYEES, null, payee.toContentValues());
        payee = new Payee("1", "Zara Ahmed", "510540", "Zar", System.currentTimeMillis() - 2 * ONE_DAY_IN_MILLIS,
                "ZAR540");
        db.insert(Tables.PAYEES, null, payee.toContentValues());
    }

    public void insertDummyBranchAtmData(SQLiteDatabase db)
    {
        BranchAtm branchAtm = new BranchAtm("Richmond Road", "1, Richmond Circle, Richmond Road", "Bangalore",
                BranchAtm.Type.BRANCH, new LatLng(12.939848, 77.5872505));
        db.insert(Tables.BRANCH_ATMS, null, branchAtm.toContentValues());
        branchAtm = new BranchAtm("Cubbonpet", "Cubbonpet Main Road, 199/1, 9th cross", "Bangalore",
                BranchAtm.Type.ATM, new LatLng(12.970534, 77.583551));
        db.insert(Tables.BRANCH_ATMS, null, branchAtm.toContentValues());
        branchAtm = new BranchAtm("Ejipura", "100 Ft Road, Ejipura", "Bangalore", BranchAtm.Type.ATM, new LatLng(
                12.938624, 77.631830));
        db.insert(Tables.BRANCH_ATMS, null, branchAtm.toContentValues());
        branchAtm = new BranchAtm("Adugodi", "Near Forum Mall, Adugodi", "Bangalore", BranchAtm.Type.ATM, new LatLng(
                12.939955, 77.614663));
        db.insert(Tables.BRANCH_ATMS, null, branchAtm.toContentValues());
        branchAtm = new BranchAtm("Lal Bagh", "Lal Bagh Road, Raja Ram Mohanroy Extension, Sudhama Nagar", "Bangalore",
                BranchAtm.Type.ATM, new LatLng(12.966250, 77.588004));
        db.insert(Tables.BRANCH_ATMS, null, branchAtm.toContentValues());
        branchAtm = new BranchAtm("Jayanagar", "Jayanagar 9th Block", "Bangalore", BranchAtm.Type.BRANCH, new LatLng(
                12.923837, 77.593396));
        db.insert(Tables.BRANCH_ATMS, null, branchAtm.toContentValues());
        branchAtm = new BranchAtm("HAL", "HAL Airport Rd, ISRO Colony, Domlur", "Bangalore", BranchAtm.Type.BRANCH,
                new LatLng(12.966355, 77.644921));
        db.insert(Tables.BRANCH_ATMS, null, branchAtm.toContentValues());
        branchAtm = new BranchAtm("Chickpet", "OTC Road, Chickpet", "Bangalore", BranchAtm.Type.BRANCH, new LatLng(
                12.977261, 77.574992));
        db.insert(Tables.BRANCH_ATMS, null, branchAtm.toContentValues());
    }

    private void insertDummyTransactionData(SQLiteDatabase db)
    {
        Transaction transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 1200, 1200000, Transaction.Type.Debit,
                "House Rent", System.currentTimeMillis() - 30 * ONE_DAY_IN_MILLIS, "House Rent",
                TransactionCategoryUtils.Category.UNKNOWN);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
        transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 800, 1200000, Transaction.Type.Debit, "Conveyance",
                System.currentTimeMillis() - 20 * ONE_DAY_IN_MILLIS, "Uber", TransactionCategoryUtils.Category.TRAVEL);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
        transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 3000, 1200000, Transaction.Type.Debit, "Shopping",
                System.currentTimeMillis() - 15 * ONE_DAY_IN_MILLIS, "Puma", TransactionCategoryUtils.Category.SHOPPING);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
        transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 900, 1200000, Transaction.Type.Debit, "Movies",
                System.currentTimeMillis() - 12 * ONE_DAY_IN_MILLIS, "PVR", TransactionCategoryUtils.Category.MOVIES);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
        transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 2600, 1200000, Transaction.Type.Debit, "Dinner",
                System.currentTimeMillis() - 10 * ONE_DAY_IN_MILLIS, "Pizza Hut",
                TransactionCategoryUtils.Category.FOOD);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
        transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 1350, 1200000, Transaction.Type.Debit, "Lunch",
                System.currentTimeMillis() - 7 * ONE_DAY_IN_MILLIS, "Taco Bell", TransactionCategoryUtils.Category.FOOD);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
        transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 250, 1200000, Transaction.Type.Credit, "Debt Settlement",
                System.currentTimeMillis() - 5 * ONE_DAY_IN_MILLIS, "Loan", TransactionCategoryUtils.Category.UNKNOWN);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
        transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 450, 1200000, Transaction.Type.Debit, "Taxi",
                System.currentTimeMillis() - 3 * ONE_DAY_IN_MILLIS, "Ola", TransactionCategoryUtils.Category.TRAVEL);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
        transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 1000, 1200000, Transaction.Type.Credit, "Doctor",
                System.currentTimeMillis() - ONE_DAY_IN_MILLIS, "Fortis", TransactionCategoryUtils.Category.HEALTH);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
        transaction = new Transaction(DUMMY_ACCOUNT_NUMBER_1, 3500, 1200000, Transaction.Type.Debit, "Gift",
                System.currentTimeMillis(), "Archies", TransactionCategoryUtils.Category.UNKNOWN);
        db.insert(Tables.TRANSACTIONS, null, transaction.toContentValues());
    }

    public void deleteTables()
    {
        SQLiteDatabase db = getWritableDatabase();
        int accountCount = db.delete(Tables.ACCOUNTS, "1", null);
        int transactionCount = db.delete(Tables.TRANSACTIONS, "1", null);
        int payeeCount = db.delete(Tables.PAYEES, "1", null);
        int branchAtmCount = db.delete(Tables.BRANCH_ATMS, "1", null);
        int cardCount = db.delete(Tables.CARDS, "1", null);
        int loanCount = db.delete(Tables.LOANS, "1", null);
        int emiCount = db.delete(Tables.EMIS, "1", null);
        int loanTransactionCount = db.delete(Tables.LOAN_TRANSACTIONS, "1", null);
        int transactionCategoriesCount = db.delete(Tables.TRANSACTION_CATEGORIES, "1", null);
        Log.i(TAG, "Deleted " + accountCount + " accounts, " + transactionCount + " transactions, " + payeeCount
                + " payees, " + branchAtmCount + " branches/ATMs, " + cardCount + " cards, " + loanCount
                + " loan details, " + emiCount + " emis, " + loanTransactionCount + " loan transactions and "
                + transactionCategoriesCount + " transaction categories.");
    }

    public interface Tables
    {
        String ACCOUNTS = "Accounts";
        String TRANSACTIONS = "Transactions";
        String PAYEES = "Payees";
        String BRANCH_ATMS = "BranchAtms";
        String LOANS = "Loans";
        String EMIS = "Emis";
        String LOAN_TRANSACTIONS = "LoanTransactions";
        String CARDS = "Cards";
        String TRANSACTION_CATEGORIES = "TransactionCategories";
        String RECOMMENDATIONS = "Recommendations";

        String CREATE_TABLE_ACCOUNTS_QUERY = "CREATE table " + ACCOUNTS + " (" + PocketBankerContract.Account._ID
                + " integer primary key autoincrement, " + PocketBankerContract.Account.ACCOUNT_NUMBER + " text, "
                + PocketBankerContract.Account.ACCOUNT_TYPE + " text, " + PocketBankerContract.Account.BALANCE
                + " real, " + PocketBankerContract.Account.TIME + " integer);";

        String CREATE_TABLE_TRANSACTIONS_QUERY = "CREATE table " + TRANSACTIONS + " ("
                + PocketBankerContract.Transactions._ID + " integer primary key autoincrement, "
                + PocketBankerContract.Transactions.TRANSACTION_ID + " text, "
                + PocketBankerContract.Transactions.ACCOUNT_NUMBER + " text, "
                + PocketBankerContract.Transactions.AMOUNT + " real, "
                + PocketBankerContract.Transactions.CREDIT_OR_DEBIT + " integer, "
                + PocketBankerContract.Transactions.BALANCE + " real, " + PocketBankerContract.Transactions.REMARK
                + " text, " + PocketBankerContract.Transactions.TIME + " integer, "
                + PocketBankerContract.Transactions.MERCHANT_ID + " text, "
                + PocketBankerContract.Transactions.MERCHANT_NAME + " text, "
                + PocketBankerContract.Transactions.CATEGORY + " integer);";

        String CREATE_TABLE_PAYEES_QUERY = "CREATE table " + PAYEES + " (" + PocketBankerContract.Payees._ID
                + " integer primary key autoincrement, " + PocketBankerContract.Payees.PAYEE_ID + " text, "
                + PocketBankerContract.Payees.ACCOUNT_NUMBER + " text, " + PocketBankerContract.Payees.PAYEE_NAME
                + " text, " + PocketBankerContract.Payees.CUST_ID + " text, " + PocketBankerContract.Payees.SHORT_NAME
                + " text, " + PocketBankerContract.Payees.CREATION_DATE + " integer);";

        String CREATE_TABLE_BRANCH_ATMS_QUERY = "CREATE table " + BRANCH_ATMS + " ("
                + PocketBankerContract.BranchAtms._ID + " integer primary key autoincrement, "
                + PocketBankerContract.BranchAtms.TYPE + " integer, " + PocketBankerContract.BranchAtms.FLAG
                + " text, " + PocketBankerContract.BranchAtms.ADDRESS + " text, "
                + PocketBankerContract.BranchAtms.CITY + " text, " + PocketBankerContract.BranchAtms.STATE + " text, "
                + PocketBankerContract.BranchAtms.LATITUDE + " double, " + PocketBankerContract.BranchAtms.LONGITUDE
                + " double, " + PocketBankerContract.BranchAtms.IFSC_CODE + " text, "
                + PocketBankerContract.BranchAtms.PHONE_NUMBER + " text, "
                + PocketBankerContract.BranchAtms.BRANCH_NAME + " text);";

        String CREATE_TABLE_LOANS_QUERY = "CREATE table " + LOANS + " (" + PocketBankerContract.Loans._ID
                + " integer primary key autoincrement, " + PocketBankerContract.Loans.LOAN_ACCOUNT_NUMBER + " text, "
                + PocketBankerContract.Loans.AMOUNT + " integer, " + PocketBankerContract.Loans.CUST_NAME + " text, "
                + PocketBankerContract.Loans.CUST_ID + " text, " + PocketBankerContract.Loans.POSITION + " text, "
                + PocketBankerContract.Loans.OUTSTANDING_PRINCIPAL + " integer, " + PocketBankerContract.Loans.TYPE
                + " text, " + PocketBankerContract.Loans.ROI + " integer, "
                + PocketBankerContract.Loans.MONTH_DELINQUENCY + " text, " + PocketBankerContract.Loans.DATE_OF_LOAN
                + " integer);";

        String CREATE_TABLE_EMIS_QUERY = "CREATE table " + EMIS + " (" + PocketBankerContract.Emis._ID
                + " integer primary key autoincrement, " + PocketBankerContract.Emis.LOAN_ACCOUNT_NUMBER + " text, "
                + PocketBankerContract.Emis.EMI_DATE + " real, " + PocketBankerContract.Emis.EMI_AMOUNT + " real);";

        String CREATE_TABLE_LOAN_TRANSACTIONS_QUERY = "CREATE table " + LOAN_TRANSACTIONS + " ("
                + PocketBankerContract.LoanTransactions._ID + " integer primary key autoincrement, "
                + PocketBankerContract.LoanTransactions.LOAN_ACCOUNT_NUMBER + " text, "
                + PocketBankerContract.LoanTransactions.LAST_PAYMENT_MADE + " real, "
                + PocketBankerContract.LoanTransactions.PAYMENT_MODE + " text);";

        String CREATE_TABLE_CARDS_QUERY = "CREATE table " + CARDS + " (" + PocketBankerContract.CardAccount._ID
                + " integer primary key autoincrement, " + PocketBankerContract.CardAccount.CARD_ACC_NUMBER + " text, "
                + PocketBankerContract.CardAccount.TYPE + " text, " + PocketBankerContract.CardAccount.STATUS
                + " text, " + PocketBankerContract.CardAccount.BALANCE + " integer, "
                + PocketBankerContract.CardAccount.DATE_OF_ENROLLMENT + " integer, "
                + PocketBankerContract.CardAccount.MONTH_DELINQUENCY + " text, "
                + PocketBankerContract.CardAccount.EXPIRY_DATE + " integer, "
                + PocketBankerContract.CardAccount.AVAIL_LIMIT + " integer);";

        String CREATE_TABLE_TRANSACTION_CATEGORIES_QUERY = "CREATE table " + TRANSACTION_CATEGORIES + " ("
                + PocketBankerContract.TransactionCategories._ID + " integer primary key autoincrement, "
                + PocketBankerContract.TransactionCategories.MERCHANT_NAME + " text unique, "
                + PocketBankerContract.TransactionCategories.CATEGORY + " integer);";

        String CREATE_TABLE_RECOMMENDATIONS_QUERY = "CREATE table " + RECOMMENDATIONS + " ("
                + PocketBankerContract.Recommendations._ID + " integer primary key autoincrement, "
                + PocketBankerContract.Recommendations.RECOMMENDATION_ID + " text, "
                + PocketBankerContract.Recommendations.MESSAGE + " text, "
                + PocketBankerContract.Recommendations.IMAGE_URL + " text, "
                + PocketBankerContract.Recommendations.OPEN_URL + " text, "
                + PocketBankerContract.Recommendations.REASON + " text, "
                + PocketBankerContract.Recommendations.CATEGORY + " integer);";
    }
}
