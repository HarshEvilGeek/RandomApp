package com.example.zaas.pocketbanker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by akhil on 3/19/16.
 */
public class PocketBankerOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "PocketBankerOpenHelper";

    private static final String DATABASE_NAME = "pocketbanker.db";
    private static final int DATABASE_VERSION_1 = 1; // Version 1 released April 2016

    public PocketBankerOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tables.CREATE_TABLE_ACCOUNTS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_TRANSACTIONS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_PAYEES_QUERY);
        db.execSQL(Tables.CREATE_TABLE_BRANCH_ATMS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_CARDS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_LOANS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_EMIS_QUERY);
        db.execSQL(Tables.CREATE_TABLE_LOAN_TRANSACTIONS_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteTables() {
        SQLiteDatabase db = getWritableDatabase();
        int accountCount = db.delete(Tables.ACCOUNTS, "1", null);
        int transactionCount = db.delete(Tables.TRANSACTIONS, "1", null);
        int payeeCount = db.delete(Tables.PAYEES, "1", null);
        int branchAtmCount = db.delete(Tables.BRANCH_ATMS, "1", null);
        int cardCount = db.delete(Tables.CARDS, "1", null);
        int loanCount = db.delete(Tables.LOANS, "1", null);
        int emiCount = db.delete(Tables.EMIS, "1", null);
        int loanTransactionCount = db.delete(Tables.LOAN_TRANSACTIONS, "1", null);
        Log.i(TAG, "Deleted " + accountCount + " accounts, " + transactionCount + " transactions, " + payeeCount
                + " payees, " + branchAtmCount + " branches/ATMs, " + cardCount + " cards, " + loanCount
                + " loan details, " + emiCount + " emis and " + loanTransactionCount + " loan transactions.");
    }

    interface Tables {
        String ACCOUNTS = "Accounts";
        String TRANSACTIONS = "Transactions";
        String PAYEES = "Payees";
        String BRANCH_ATMS = "BranchAtms";
        String LOANS = "Loans";
        String EMIS = "Emis";
        String LOAN_TRANSACTIONS = "LoanTransactions";
        String CARDS = "Cards";

        String CREATE_TABLE_ACCOUNTS_QUERY = "CREATE table " + ACCOUNTS + " (" +
                PocketBankerContract.Account._ID + " integer primary key, " +
                PocketBankerContract.Account.ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.Account.ACCOUNT_TYPE + " text, " +
                PocketBankerContract.Account.BALANCE + " real, " +
                PocketBankerContract.Account.TIME + " integer);";

        String CREATE_TABLE_TRANSACTIONS_QUERY = "CREATE table " +
                TRANSACTIONS + " (" +
                PocketBankerContract.Transactions._ID + " integer primary key, " +
                PocketBankerContract.Transactions.ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.Transactions.AMOUNT + " integer, " +
                PocketBankerContract.Transactions.CREDIT_OR_DEBIT + " integer, " +
                PocketBankerContract.Transactions.BALANCE + " real, " +
                PocketBankerContract.Transactions.REMARK + " text, " +
                PocketBankerContract.Transactions.TIME + " integer);";

        String CREATE_TABLE_PAYEES_QUERY = "CREATE table " +
                PAYEES + " (" +
                PocketBankerContract.Payees._ID + " integer primary key, " +
                PocketBankerContract.Payees.PAYEE_ID + " text, " +
                PocketBankerContract.Payees.ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.Payees.PAYEE_NAME + " text, " +
                PocketBankerContract.Payees.CUST_ID + " text, " +
                PocketBankerContract.Payees.SHORT_NAME + " text, " +
 PocketBankerContract.Payees.CREATION_DATE + " integer);";

        String CREATE_TABLE_BRANCH_ATMS_QUERY = "CREATE table " + BRANCH_ATMS + " ("
                + PocketBankerContract.BranchAtms._ID + " integer primary key, " + PocketBankerContract.BranchAtms.TYPE
                + " integer, " + PocketBankerContract.BranchAtms.FLAG + " text, "
                + PocketBankerContract.BranchAtms.ADDRESS + " text, " + PocketBankerContract.BranchAtms.CITY
                + " text, " + PocketBankerContract.BranchAtms.STATE + " text, "
                + PocketBankerContract.BranchAtms.LATITUDE + " double, " + PocketBankerContract.BranchAtms.LONGITUDE
                + " double, " + PocketBankerContract.BranchAtms.IFSC_CODE + " text, "
                + PocketBankerContract.BranchAtms.PHONE_NUMBER + " text, "
                + PocketBankerContract.BranchAtms.BRANCH_NAME + " text);";

        String CREATE_TABLE_LOANS_QUERY = "CREATE table " +
                LOANS + " (" +
                PocketBankerContract.Loans._ID + " integer primary key, " +
                PocketBankerContract.Loans.LOAN_ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.Loans.AMOUNT + " integer, " +
                PocketBankerContract.Loans.CUST_NAME + " text, " +
                PocketBankerContract.Loans.CUST_ID + " text, " +
                PocketBankerContract.Loans.POSITION + " text, " +
                PocketBankerContract.Loans.OUTSTANDING_PRINCIPAL + " integer, " +
                PocketBankerContract.Loans.TYPE + " text, " +
                PocketBankerContract.Loans.ROI + " integer, " +
                PocketBankerContract.Loans.MONTH_DELINQUENCY + " text, " +
                PocketBankerContract.Loans.DATE_OF_LOAN + " integer);";

        String CREATE_TABLE_EMIS_QUERY = "CREATE table " +
                EMIS + " (" +
                PocketBankerContract.Emis._ID + " integer primary key, " +
                PocketBankerContract.Emis.LOAN_ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.Emis.NO_OF_EMI + " integer, " +
                PocketBankerContract.Emis.EMI_DATES + " text, " +
                PocketBankerContract.Emis.EMI_LAST_THREE + " text);";

        String CREATE_TABLE_LOAN_TRANSACTIONS_QUERY = "CREATE table " +
                LOAN_TRANSACTIONS + " (" +
                PocketBankerContract.LoanTransactions._ID + " integer primary key, " +
                PocketBankerContract.LoanTransactions.LOAN_ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.LoanTransactions.LAST_PAYMENT_MADE + " real, " +
                PocketBankerContract.LoanTransactions.PAYMENT_MODE + " text);";

        String CREATE_TABLE_CARDS_QUERY = "CREATE table " + CARDS + " (" +
                PocketBankerContract.CardAccount._ID + " integer primary key, " +
                PocketBankerContract.CardAccount.CARD_ACC_NUMBER + " text, " +
                PocketBankerContract.CardAccount.TYPE + " text, " +
                PocketBankerContract.CardAccount.STATUS + " text, " +
                PocketBankerContract.CardAccount.BALANCE + " integer, " +
                PocketBankerContract.CardAccount.DATE_OF_ENROLLMENT + " integer, " +
                PocketBankerContract.CardAccount.MONTH_DELINQUENCY + " text, " +
                PocketBankerContract.CardAccount.EXPIRY_DATE + " integer, " +
                PocketBankerContract.CardAccount.AVAIL_LIMIT + " integer);";
    }
}
