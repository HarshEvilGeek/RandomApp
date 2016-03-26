package com.example.zaas.pocketbanker.models.local;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.zaas.pocketbanker.application.PocketBankerApplication;
import com.example.zaas.pocketbanker.data.PocketBankerContract;
import com.example.zaas.pocketbanker.data.PocketBankerOpenHelper;
import com.example.zaas.pocketbanker.data.PocketBankerProvider;

/**
 * Created by zaraahmed on 3/25/16.
 */
public class LoanEMI extends DbModel
{

    private double emiAmount;
    private long emiDate;
    private String loanAccNo;

    public LoanEMI()
    {

    }

    public LoanEMI(double amount, long date, String loanAccNo)
    {
        this.emiAmount = amount;
        this.emiDate = date;
        this.loanAccNo = loanAccNo;
    }

    public double getEmiAmount()
    {
        return emiAmount;
    }

    public void setEmiAmount(double emiAmount)
    {
        this.emiAmount = emiAmount;
    }

    public long getEmiDate()
    {
        return emiDate;
    }

    public void setEmiDate(long emiDate)
    {
        this.emiDate = emiDate;
    }

    public String getLoanAccNo()
    {
        return loanAccNo;
    }

    public void setLoanAccNo(String loanAccNo)
    {
        this.loanAccNo = loanAccNo;
    }

    @Override
    public void instantiateFromCursor(Cursor cursor)
    {
        if (cursor != null) {
            this.emiAmount = cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.Emis.EMI_AMOUNT));
            this.emiDate = cursor.getLong(cursor.getColumnIndex(PocketBankerContract.Emis.EMI_DATE));
            this.loanAccNo = cursor.getString(cursor.getColumnIndex(PocketBankerContract.Emis.LOAN_ACCOUNT_NUMBER));
        }

    }

    @Override
    public String getSelectionString()
    {
        return PocketBankerContract.Emis.EMI_DATE + " = ? ";
    }

    @Override
    public String[] getSelectionValues()
    {
        return new String[] { String.valueOf(emiDate) };
    }

    @Override
    public boolean isEqual(DbModel model)
    {
        return (model instanceof LoanEMI && loanAccNo.equals(((LoanEMI) model).getLoanAccNo())
                && (emiAmount == ((LoanEMI) model).getEmiAmount()) && (emiDate == ((LoanEMI) model).getEmiDate()));
    }

    @Override
    public ContentValues toContentValues()
    {
        ContentValues values = new ContentValues();
        values.put(PocketBankerContract.Emis.LOAN_ACCOUNT_NUMBER, loanAccNo);
        values.put(PocketBankerContract.Emis.EMI_AMOUNT, emiAmount);
        values.put(PocketBankerContract.Emis.EMI_DATE, emiDate);
        return values;
    }

    @Override
    public String getTable()
    {
        return PocketBankerOpenHelper.Tables.EMIS;
    }

    public static List<LoanEMI> getLatestNEmis(int number, String accNumber, String sortOrder)
    {
        List<LoanEMI> loanEmis = new ArrayList<>();
        String where = PocketBankerContract.Emis.LOAN_ACCOUNT_NUMBER + " = ? ";
        Cursor c = null;
        try {
            c = PocketBankerApplication.getApplication().getApplicationContext().getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_EMIS, null, where, new String[] { accNumber }, sortOrder);
            if (c != null) {
                while (c.moveToNext() && number != 0) {
                    LoanEMI loanEmi = new LoanEMI();
                    loanEmi.instantiateFromCursor(c);
                    loanEmis.add(loanEmi);
                    number--;
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return loanEmis;
    }

    public static List<LoanEMI> getEmisBetween(String accNumber, String sortOrder, long startTime, long endTime)
    {
        List<LoanEMI> loanEmis = new ArrayList<>();
        String where = PocketBankerContract.Emis.LOAN_ACCOUNT_NUMBER + " = ? AND " + PocketBankerContract.Emis.EMI_DATE
                + " >= " + startTime + " AND " + PocketBankerContract.Emis.EMI_DATE + " <= " + endTime;
        Cursor c = null;
        try {
            c = PocketBankerApplication.getApplication().getApplicationContext().getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_EMIS, null, where, new String[] { accNumber }, sortOrder);
            if (c != null) {
                while (c.moveToNext()) {
                    LoanEMI loanEmi = new LoanEMI();
                    loanEmi.instantiateFromCursor(c);
                    loanEmis.add(loanEmi);
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return loanEmis;
    }
}
