package com.example.zaas.pocketbanker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by akhil on 3/19/16.
 */
public class PocketBankerOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pocketbanker.db";
    private static final int DATABASE_VERSION_1 = 1; // Version 1 released April 2016

    public PocketBankerOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    interface Tables {
        String ACCOUNT = "Account";
        String TRANSACTIONS = "Transactions";
        String PAYEES = "Payees";
        String LOANS = "Loans";
        String EMIS = "Emis";
        String LOAN_TRANSACTIONS = "LoanTransactions";

        String CREATE_TABLE_ACCOUNT_QUERY = "CREATE table " +
                ACCOUNT + " (" +
                PocketBankerContract.Account.ID + " integer primary key, " +
                PocketBankerContract.Account.ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.Account.ACCOUNT_TYPE + " text, " +
                PocketBankerContract.Account.BALANCE + " real, " +
                PocketBankerContract.Account.TIME + " integer);";

        String CREATE_TABLE_TRANSACTIONS_QUERY = "CREATE table " +
                TRANSACTIONS + " (" +
                PocketBankerContract.Transactions.ID + " integer primary key, " +
                PocketBankerContract.Transactions.ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.Transactions.AMOUNT + " integer, " +
                PocketBankerContract.Transactions.CREDIT_OR_DEBIT + " integer, " +
                PocketBankerContract.Transactions.BALANCE + " real, " +
                PocketBankerContract.Transactions.REMARK + " text, " +
                PocketBankerContract.Transactions.TIME + " integer);";

        String CREATE_TABLE_PAYEES_QUERY = "CREATE table " +
                PAYEES + " (" +
                PocketBankerContract.Payees.ID + " integer primary key, " +
                PocketBankerContract.Payees.PAYEE_ID + " text, " +
                PocketBankerContract.Payees.ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.Payees.PAYEE_NAME + " text, " +
                PocketBankerContract.Payees.CUST_ID + " text, " +
                PocketBankerContract.Payees.SHORT_NAME + " text, " +
                PocketBankerContract.Payees.ADDED_DATE + " integer);";

        String CREATE_TABLE_LOANS_QUERY = "CREATE table " +
                LOANS + " (" +
                PocketBankerContract.Loans.ID + " integer primary key, " +
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
                PocketBankerContract.Emis.ID + " integer primary key, " +
                PocketBankerContract.Emis.LOAN_ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.Emis.NO_OF_EMI + " integer, " +
                PocketBankerContract.Emis.EMI_DATES + " text, " +
                PocketBankerContract.Emis.EMI_LAST_THREE + " text);";

        String CREATE_TABLE_LOAN_TRANSACTIONS_QUERY = "CREATE table " +
                LOAN_TRANSACTIONS + " (" +
                PocketBankerContract.LoanTransactions.ID + " integer primary key, " +
                PocketBankerContract.LoanTransactions.LOAN_ACCOUNT_NUMBER + " text, " +
                PocketBankerContract.LoanTransactions.LAST_PAYMENT_MADE + " real, " +
                PocketBankerContract.LoanTransactions.PAYMENT_MODE + " text);";

        String CREATE_TABLE_CARD_ACCOUNT_QUERY = "CREATE table " +
                LOAN_TRANSACTIONS + " (" +
                PocketBankerContract.CardAccount.ID + " integer primary key, " +
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
