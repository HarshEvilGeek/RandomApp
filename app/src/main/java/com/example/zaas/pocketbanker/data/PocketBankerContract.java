package com.example.zaas.pocketbanker.data;

/**
 * Created by akhil on 3/19/16.
 */
public class PocketBankerContract {

    public static final String CONTENT_AUTHORITY = "com.example.zaas.pocketbanker.provider";

    interface BaseColumns {
        String _ID = "_id";
    }

    interface AccountBaseColumns {
        String ACCOUNT_NUMBER = "account_number";
        String BALANCE = "balance";
        String TIME = "time";
    }

    interface AccountColumns {
        String ACCOUNT_TYPE = "account_type";
    }

    interface TransactionColumns {
        String TRANSACTION_ID = "transaction_id";
        String AMOUNT = "amount";
        String CREDIT_OR_DEBIT = "credit_or_debit";
        String REMARK = "remark";
        String MERCHANT_ID = "merchant_id";
        String MERCHANT_NAME = "merchant_name";
        String CATEGORY = "category";
    }

    interface PayeeColumns {
        String PAYEE_ID = "payee_id";
        String PAYEE_NAME = "name";
        String ACCOUNT_NUMBER = "account_number";
        String CUST_ID = "cust_id";
        String SHORT_NAME = "short_name";
        String CREATION_DATE = "creation_date";
    }

    interface BranchAtmColumns
    {
        String TYPE = "type";
        String FLAG = "flag";
        String ADDRESS = "address";
        String CITY = "city";
        String STATE = "state";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String IFSC_CODE = "ifsc_code";
        String PHONE_NUMBER = "phone_number";
        String BRANCH_NAME = "branch_name";
    }

    interface LoanBaseColumns {
        String LOAN_ACCOUNT_NUMBER = "loan_account_number";
    }

    interface LoanColumns {
        String CUST_NAME = "cust_name";
        String POSITION = "position";
        String OUTSTANDING_PRINCIPAL = "outstanding_principal";
        String TYPE = "type";
        String ROI = "roi";
        String MONTH_DELINQUENCY = "month_delinquency";
        String AMOUNT = "amount";
        String CUST_ID = "cust_id";
        String DATE_OF_LOAN = "date_of_loan";
    }

    interface EmiColumns {
        String EMI_DATE = "emi_date";
        String EMI_AMOUNT = "emi_amount";
    }

    interface LoanTransactionColumns {
        String LAST_PAYMENT_MADE = "last_payment_mode";
        String PAYMENT_MODE = "payment_mode";
    }

    interface CardColumns {
        String TYPE = "type";
        String STATUS = "status";
        String BALANCE = "balance";
        String DATE_OF_ENROLLMENT = "date_of_enrollment";
        String MONTH_DELINQUENCY = "month_delinquency";
        String CARD_ACC_NUMBER = "card_account_number";
        String EXPIRY_DATE = "expiry_date";
        String AVAIL_LIMIT = "avail_limit";
    }

    interface TransactionCategoryColumns
    {
        String MERCHANT_NAME = "merchant_name";
        String CATEGORY = "category";
    }

    interface RecommendationColumns
    {
        String RECOMMENDATION_ID = "recommendation_id";
        String MESSAGE = "message";
        String IMAGE_URL = "image_url";
        String CATEGORY = "category";
        String REASON = "reason";
        String OPEN_URL = "open_url";
    }

    public static class Account implements BaseColumns, AccountBaseColumns, AccountColumns
    {

    }

    public static class Transactions implements BaseColumns, AccountBaseColumns, TransactionColumns
    {

    }

    public static class Payees implements BaseColumns, PayeeColumns
    {

    }

    public static class BranchAtms implements BaseColumns, BranchAtmColumns
    {

    }

    public static class Loans implements BaseColumns, LoanBaseColumns, LoanColumns
    {

    }

    public static class Emis implements BaseColumns, LoanBaseColumns, EmiColumns
    {

    }

    public static class LoanTransactions implements BaseColumns, LoanBaseColumns, LoanTransactionColumns
    {

    }

    public static class CardAccount implements BaseColumns, CardColumns
    {

    }

    public static class TransactionCategories implements BaseColumns, TransactionCategoryColumns
    {

    }

    public static class Recommendations implements BaseColumns, RecommendationColumns
    {

    }
}
