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
import com.example.zaas.pocketbanker.models.network.LoanDetails;

/**
 * Created by zaraahmed on 3/25/16.
 */
public class LoanAccount extends DbModel
{
    private static final String LOG_TAG = LoanAccount.class.getSimpleName();

    private String loanAccountNo;
    private String customerName;
    private String position;
    private double outstandingPrinciple;
    private String type;
    private double roi;
    private String monthDelinquency;
    private double amount;
    private String custId;
    private String dateOfLoan;

    public LoanAccount()
    {

    }

    public static LoanAccount getLoanAccount(LoanDetails loanDetails)
    {
        return new LoanAccount(loanDetails.getLoanAccNum(), loanDetails.getCustomerName(), loanDetails.getPos(),
                loanDetails.getPrincipleOutstanding(), loanDetails.getTypeOfLoan(), loanDetails.getRoi(),
                loanDetails.getMonthDelinquency(), loanDetails.getLoanAmount(), loanDetails.getCustId(),
                loanDetails.getDateOfLoan());
    }

    public LoanAccount(String loanAccountNo, String customerName, String position, double outstandingPrinciple,
            String type, double roi, String monthDelinquency, double amount, String custId, String dateOfLoan)
    {
        this.loanAccountNo = loanAccountNo;
        this.customerName = customerName;
        this.position = position;
        this.outstandingPrinciple = outstandingPrinciple;
        this.type = type;
        this.roi = roi;
        this.monthDelinquency = monthDelinquency;
        this.amount = amount;
        this.custId = custId;
        this.dateOfLoan = dateOfLoan;
    }

    public String getLoanAccountNo()
    {
        return loanAccountNo;
    }

    public void setLoanAccountNo(String loanAccountNo)
    {
        this.loanAccountNo = loanAccountNo;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getMonthDelinquency()
    {
        return monthDelinquency;
    }

    public void setMonthDelinquency(String monthDelinquency)
    {
        this.monthDelinquency = monthDelinquency;
    }

    public String getCustId()
    {
        return custId;
    }

    public void setCustId(String custId)
    {
        this.custId = custId;
    }

    public String getDateOfLoan()
    {
        return dateOfLoan;
    }

    public void setDateOfLoan(String dateOfLoan)
    {
        this.dateOfLoan = dateOfLoan;
    }

    public double getOutstandingPrinciple()
    {
        return outstandingPrinciple;
    }

    public void setOutstandingPrinciple(double outstandingPrinciple)
    {
        this.outstandingPrinciple = outstandingPrinciple;
    }

    public double getRoi()
    {
        return roi;
    }

    public void setRoi(double roi)
    {
        this.roi = roi;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    @Override
    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(PocketBankerContract.Loans.AMOUNT, amount);
        values.put(PocketBankerContract.Loans.CUST_ID, custId);
        values.put(PocketBankerContract.Loans.CUST_NAME, customerName);
        values.put(PocketBankerContract.Loans.DATE_OF_LOAN, dateOfLoan);
        values.put(PocketBankerContract.Loans.LOAN_ACCOUNT_NUMBER, loanAccountNo);
        values.put(PocketBankerContract.Loans.MONTH_DELINQUENCY, monthDelinquency);
        values.put(PocketBankerContract.Loans.OUTSTANDING_PRINCIPAL, outstandingPrinciple);
        values.put(PocketBankerContract.Loans.POSITION, position);
        values.put(PocketBankerContract.Loans.ROI, roi);
        values.put(PocketBankerContract.Loans.TYPE, type);
        return values;
    }

    @Override
    public String getTable()
    {
        return PocketBankerOpenHelper.Tables.LOANS;
    }

    @Override
    public void instantiateFromCursor(Cursor cursor)
    {
        if (cursor != null) {
            this.dateOfLoan = cursor.getString(cursor.getColumnIndex(PocketBankerContract.Loans.DATE_OF_LOAN));
            this.loanAccountNo = cursor
                    .getString(cursor.getColumnIndex(PocketBankerContract.Loans.LOAN_ACCOUNT_NUMBER));
            this.customerName = cursor.getString(cursor.getColumnIndex(PocketBankerContract.Loans.CUST_NAME));
            this.custId = cursor.getString(cursor.getColumnIndex(PocketBankerContract.Loans.CUST_ID));
            this.amount = cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.Loans.AMOUNT));
            this.roi = cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.Loans.ROI));
            this.outstandingPrinciple = cursor.getDouble(cursor
                    .getColumnIndex(PocketBankerContract.Loans.OUTSTANDING_PRINCIPAL));
            this.position = cursor.getString(cursor.getColumnIndex(PocketBankerContract.Loans.POSITION));
            this.type = cursor.getString(cursor.getColumnIndex(PocketBankerContract.Loans.TYPE));
            this.monthDelinquency = cursor.getString(cursor
                    .getColumnIndex(PocketBankerContract.Loans.MONTH_DELINQUENCY));
        }
    }

    @Override
    public String getSelectionString()
    {
        return PocketBankerContract.Loans.LOAN_ACCOUNT_NUMBER + " = ?";
    }

    @Override
    public String[] getSelectionValues()
    {
        return new String[] { loanAccountNo };
    }

    @Override
    public boolean isEqual(DbModel model)
    {
        return model instanceof LoanAccount && loanAccountNo.equals(((LoanAccount) model).getLoanAccountNo());
    }

    public static List<LoanAccount> getAllLoanAccounts()
    {
        Cursor c = null;
        List<LoanAccount> loanAccountList = new ArrayList<>();
        try {
            c = PocketBankerApplication.getApplication().getApplicationContext().getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_LOANS, null, null, null, null);
            if (c != null) {
                while (c.moveToNext()) {
                    LoanAccount loanAccount = new LoanAccount();
                    loanAccount.instantiateFromCursor(c);
                    loanAccountList.add(loanAccount);
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return loanAccountList;
    }

    public static LoanAccount getLoanAccount(String loanAccountNo)
    {
        LoanAccount loanAccount = null;
        Cursor c = null;
        try {
            c = PocketBankerApplication
                    .getApplication()
                    .getApplicationContext()
                    .getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_LOANS, null,
                            PocketBankerContract.Loans.LOAN_ACCOUNT_NUMBER + " = ? ", new String[] { loanAccountNo },
                            null);
            if (c != null && c.moveToNext()) {
                loanAccount = new LoanAccount();
                loanAccount.instantiateFromCursor(c);
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return loanAccount;
    }

    public static void insertOrUpdateLoanAccount(LoanAccount loanAccount)
    {
        if (loanAccount == null) {
            return;
        }

        LoanAccount existingLoanAccount = getLoanAccount(loanAccount.getLoanAccountNo());
        try {
            if (existingLoanAccount == null) {
                Uri id = PocketBankerApplication.getApplication().getContentResolver()
                        .insert(PocketBankerProvider.CONTENT_URI_LOANS, loanAccount.toContentValues());
                if (id != null) {
                    Log.i(LOG_TAG, "Insertion succeeded for loan account details ");
                }
                else {
                    Log.i(LOG_TAG, "Insertion failed for loan account details ");
                }
            }
            else {

                long id = PocketBankerApplication
                        .getApplication()
                        .getContentResolver()
                        .update(PocketBankerProvider.CONTENT_URI_LOANS, loanAccount.toContentValues(),
                                PocketBankerContract.Loans.LOAN_ACCOUNT_NUMBER + " = ? ",
                                new String[] { existingLoanAccount.getLoanAccountNo() });
                if (id != -1) {
                    Log.i(LOG_TAG, "updation succeeded for loan account details : " + id);
                }
                else {
                    Log.i(LOG_TAG, "updation failed for loan account details ");
                }

            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Exception while inserting loan account details : ", e);
        }
    }
}
