package com.example.zaas.pocketbanker.models.local;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.zaas.pocketbanker.application.PocketBankerApplication;
import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerOpenHelper;
import com.example.zaas.pocketbanker.data.PocketBankerProvider;
import com.example.zaas.pocketbanker.models.network.CardDetails;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/25/16.
 */
public class CardAccount extends DbModel
{

    private static final String LOG_TAG = CardAccount.class.getSimpleName();

    private long id = -1;
    private String type;
    private String status;
    private double balance;
    private String dateOfEnrollment;
    private String monthDelinquency;
    private String cardAccNumber = "3688747898";
    private String expiryDate;
    private double availLimit;

    public static final String CARD_DETAILS_DATE_FORMAT = "dd/MM/yyyy";

    public static CardAccount getCardAccount(CardDetails cardDetails)
    {
        return new CardAccount(cardDetails.getCardType(), cardDetails.getCardStatus(), cardDetails.getCurrentBalance(),
                cardDetails.getDateOfEnrollment(), cardDetails.getMonthDelinquency(), cardDetails.getCustId(),
                cardDetails.getExpiryDate(), cardDetails.getAvailLmt());

    }

    public CardAccount()
    {

    }

    public CardAccount(String type, String status, double balance, String dateOfEnrollment, String monthDelinquency,
            String cardAccNumber, String expiryDate, double availLimit)
    {

        this.type = type;
        this.status = status;
        this.balance = balance;
        this.dateOfEnrollment = dateOfEnrollment;
        this.monthDelinquency = monthDelinquency;
        //TODO hardcoded
        this.cardAccNumber = Constants.CARD_ACCOUNT_NUMBER;
        this.expiryDate = expiryDate;
        this.availLimit = availLimit;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getDateOfEnrollment()
    {
        return dateOfEnrollment;
    }

    public void setDateOfEnrollment(String dateOfEnrollment)
    {
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public String getMonthDelinquency()
    {
        return monthDelinquency;
    }

    public void setMonthDelinquency(String monthDelinquency)
    {
        this.monthDelinquency = monthDelinquency;
    }

    public String getCardAccNumber()
    {
        return cardAccNumber;
    }

    public void setCardAccNumber(String cardAccNumber)
    {
        this.cardAccNumber = cardAccNumber;
    }

    public String getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public double getAvailLimit()
    {
        return availLimit;
    }

    public void setAvailLimit(double availLimit)
    {
        this.availLimit = availLimit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void instantiateFromCursor(Cursor cursor)
    {

        if (cursor != null) {
            this.type = cursor.getString(cursor.getColumnIndex(PocketBankerContract.CardAccount.TYPE));
            this.status = cursor.getString(cursor.getColumnIndex(PocketBankerContract.CardAccount.STATUS));
            this.dateOfEnrollment = cursor.getString(cursor
                    .getColumnIndex(PocketBankerContract.CardAccount.DATE_OF_ENROLLMENT));
            this.monthDelinquency = cursor.getString(cursor
                    .getColumnIndex(PocketBankerContract.CardAccount.MONTH_DELINQUENCY));
            this.balance = cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.CardAccount.BALANCE));
            this.availLimit = cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.CardAccount.AVAIL_LIMIT));
            this.cardAccNumber = cursor.getString(cursor
                    .getColumnIndex(PocketBankerContract.CardAccount.CARD_ACC_NUMBER));
            this.expiryDate = cursor.getString(cursor.getColumnIndex(PocketBankerContract.CardAccount.EXPIRY_DATE));
            this.id = cursor.getLong(cursor.getColumnIndex(PocketBankerContract.CardAccount._ID));
        }

    }

    @Override
    public String getSelectionString()
    {
        return PocketBankerContract.CardAccount.CARD_ACC_NUMBER + " = ?";
    }

    @Override
    public String[] getSelectionValues()
    {
        return new String[] { cardAccNumber };
    }

    @Override
    public boolean isEqual(DbModel model)
    {
        return model instanceof CardAccount && cardAccNumber.equals(((CardAccount) model).getCardAccNumber());
    }

    @Override
    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(PocketBankerContract.CardAccount.TYPE, type);
        values.put(PocketBankerContract.CardAccount.STATUS, status);
        values.put(PocketBankerContract.CardAccount.DATE_OF_ENROLLMENT, dateOfEnrollment);
        values.put(PocketBankerContract.CardAccount.MONTH_DELINQUENCY, monthDelinquency);
        values.put(PocketBankerContract.CardAccount.BALANCE, balance);
        values.put(PocketBankerContract.CardAccount.AVAIL_LIMIT, availLimit);
        values.put(PocketBankerContract.CardAccount.CARD_ACC_NUMBER, cardAccNumber);
        values.put(PocketBankerContract.CardAccount.EXPIRY_DATE, expiryDate);
        return values;
    }

    @Override
    public String getTable()
    {
        return PocketBankerOpenHelper.Tables.CARDS;
    }

    public static List<CardAccount> getAllCardAccounts()
    {
        Cursor c = null;
        List<CardAccount> cardAccountList = new ArrayList<>();
        try {
            c = PocketBankerApplication.getApplication().getApplicationContext().getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_CARDS, null, null, null, null);
            if (c != null) {
                while (c.moveToNext()) {
                    CardAccount cardAccount = new CardAccount();
                    cardAccount.instantiateFromCursor(c);
                    cardAccountList.add(cardAccount);
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return cardAccountList;
    }

    public static CardAccount getCardAccount(String accNumber)
    {
        CardAccount cardAccount = null;
        Cursor c = null;
        try {
            c = PocketBankerApplication
                    .getApplication()
                    .getApplicationContext()
                    .getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_CARDS, null,
                            PocketBankerContract.CardAccount.CARD_ACC_NUMBER + " = ? ", new String[] { accNumber },
                            null);
            if (c != null && c.moveToNext()) {
                cardAccount = new CardAccount();
                cardAccount.instantiateFromCursor(c);
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return cardAccount;
    }

    public static void insertOrUpdateCardAccount(CardAccount cardAccount)
    {
        if (cardAccount == null) {
            return;
        }

        CardAccount existingCardAccount = getCardAccount(cardAccount.getCardAccNumber());
        try {
            if (existingCardAccount == null) {
                Uri id = PocketBankerApplication.getApplication().getContentResolver()
                        .insert(PocketBankerProvider.CONTENT_URI_CARDS, cardAccount.toContentValues());
                if (id != null) {
                    Log.i(LOG_TAG, "Insertion succeeded for card account details ");
                }
                else {
                    Log.i(LOG_TAG, "Insertion failed for card account details ");
                }
            }
            else {

                long id = PocketBankerApplication
                        .getApplication()
                        .getContentResolver()
                        .update(PocketBankerProvider.CONTENT_URI_CARDS, cardAccount.toContentValues(),
                                PocketBankerContract.CardAccount.CARD_ACC_NUMBER + " = ? ",
                                new String[] { existingCardAccount.getCardAccNumber() });
                if (id != -1) {
                    Log.i(LOG_TAG, "updation succeeded for card account details : " + id);
                }
                else {
                    Log.i(LOG_TAG, "updation failed for card account details ");
                }

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while inserting card account details : ", e);
        }
    }

}
