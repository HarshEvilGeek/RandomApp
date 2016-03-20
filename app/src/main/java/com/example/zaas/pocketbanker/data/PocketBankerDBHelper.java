package com.example.zaas.pocketbanker.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.example.zaas.pocketbanker.models.local.Payee;

/**
 * Created by akhil on 3/19/16.
 */
public class PocketBankerDBHelper
{

    private static PocketBankerDBHelper instance;

    private PocketBankerDBHelper()
    {
    }

    public static PocketBankerDBHelper getInstance()
    {
        if (instance == null) {
            instance = new PocketBankerDBHelper();
        }
        return instance;
    }

    public List<Account> getAllAccounts(Context context)
    {
        Cursor c = null;
        List<Account> accountList = new ArrayList<>();
        try {
            c = context.getContentResolver().query(PocketBankerProvider.CONTENT_URI_ACCOUNTS, null, null, null, null);
            if (c != null) {
                while (c.moveToNext()) {
                    accountList.add(Account.loadFromCursor(c));
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return accountList;
    }

    public List<Payee> getAllPayees(Context context)
    {
        Cursor c = null;
        List<Payee> payeeList = new ArrayList<>();
        try {
            c = context.getContentResolver().query(PocketBankerProvider.CONTENT_URI_PAYEES, null, null, null, null);
            if (c != null) {
                while (c.moveToNext()) {
                    payeeList.add(Payee.loadFromCursor(c));
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return payeeList;
    }

    public List<BranchAtm> getAllBranchAtms(Context context)
    {
        Cursor c = null;
        List<BranchAtm> branchAtmList = new ArrayList<>();
        try {
            c = context.getContentResolver()
                    .query(PocketBankerProvider.CONTENT_URI_BRANCH_ATMS, null, null, null, null);
            if (c != null) {
                while (c.moveToNext()) {
                    branchAtmList.add(BranchAtm.loadFromCursor(c));
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return branchAtmList;
    }

    private void insertOrUpdateAccountsTable(Context context, List<Account> newAccountList)
    {
        List<Account> accountModelsToDelete = new ArrayList<>();
        List<Account> accountModelsToUpdate = new ArrayList<>();
        List<Account> accountModelsToAdd = new ArrayList<>();

        List<Account> currentAccountList = getAllAccounts(context);

        for (Account localModel : currentAccountList) {
            boolean found = false;
            for (Account newModel : newAccountList) {
                if (newModel.getAccountNumber().equals(localModel.getAccountNumber())) {
                    accountModelsToUpdate.add(newModel);
                    found = true;
                }
            }
            if (!found) {
                accountModelsToDelete.add(localModel);
            }

        }

        for (Account newModel : newAccountList) {
            boolean found = false;
            for (Account localModel : currentAccountList) {
                if (newModel.getAccountNumber().equals(localModel.getAccountNumber())) {
                    found = true;
                }
            }
            if (!found) {
                accountModelsToAdd.add(newModel);
            }
        }

    }

}
