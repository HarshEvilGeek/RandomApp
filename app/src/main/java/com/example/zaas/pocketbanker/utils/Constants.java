package com.example.zaas.pocketbanker.utils;

/**
 * Created by zaraahmed on 3/19/16.
 */
public interface Constants
{

    int SUMMARY_ITEM_TYPE_HEADER = 0;
    int SUMMARY_ITEM_TYPE_ITEM = 1;
    String TRANSACTION_TYPE_CREDIT = "Cr.";
    String TRANSACTION_TYPE_DEBIT = "Dr.";
    String TRANSACTION_TYPE_CHEQUE = "Cheque";
    String TRANSACTION_TYPE_CASH = "Cash";
    String TRANSACTION_TYPE_ONLINE_TRANSFER = "Online Transfer";
    String ACCOUNT_TYPE_SAVINGS = "Savings";
    String HEADER_TYPE_BANKACCOUNT = "BA";
    String HEADER_TYPE_LOANACCOUNT = "LA";
    String HEADER_TYPE_CARD = "CA";
    String SUMMARY_DETAIL_FRAGMENT_ACCOUNT_NUMBER = "SUMMARY_DETAIL_FRAGMENT_ACCOUNT_NUMBER";
    String SUMMARY_DETAIL_FRAGMENT_HEADER_TYPE = "SUMMARY_DETAIL_FRAGMENT_HEADER_TYPE";
    String CUST_ID = "88881949";
    long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    long ONE_MONTH_IN_MILLIS = 30 * ONE_DAY_IN_MILLIS;
    String WALLET_DOB_FORMAT = "yyyy-MM-dd";
    String WALLET_CREATED_SUCCESSFULLY = "Wallet Created Successfully";
    String WALLET_ALREADY_EXISTS = "Wallet Already Exist";
    String WALLET_CREATION_FAILED = "WALLET_CREATION_FAILED";

 }
