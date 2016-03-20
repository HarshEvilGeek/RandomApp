package com.example.zaas.pocketbanker.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.example.zaas.pocketbanker.models.local.DbModel;
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
                    Account account = new Account();
                    account.instantiateFromCursor(c);
                    accountList.add(account);
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
                    Payee payee = new Payee();
                    payee.instantiateFromCursor(c);
                    payeeList.add(payee);
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
                    BranchAtm branchAtm = new BranchAtm();
                    branchAtm.instantiateFromCursor(c);
                    branchAtmList.add(branchAtm);
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

    public List<? extends DbModel> getAllDbModels(Context context, String table) {
        switch (table) {
            case PocketBankerOpenHelper.Tables.ACCOUNTS:
                return getAllAccounts(context);
            case PocketBankerOpenHelper.Tables.BRANCH_ATMS:
                return getAllBranchAtms(context);
            case PocketBankerOpenHelper.Tables.PAYEES:
                return getAllPayees(context);
        }
        return null;
    }

    private void insertOrUpdateAccountsTable(Context context, List<? extends DbModel> newAccountList)
    {
        List<DbModel> modelsToDelete = new ArrayList<>();
        List<DbModel> modelsToUpdate = new ArrayList<>();
        List<DbModel> modelsToAdd = new ArrayList<>();

        List<? extends DbModel> currentAccountList = getAllAccounts(context);

        for (DbModel localModel : currentAccountList) {
            boolean found = false;
            for (DbModel newModel : newAccountList) {
                if (newModel.getUniqueIdentifier().equals(localModel.getUniqueIdentifier())) {
                    modelsToUpdate.add(newModel);
                    found = true;
                }
            }
            if (!found) {
                modelsToDelete.add(localModel);
            }
        }

        for (DbModel newModel : newAccountList) {
            boolean found = false;
            for (DbModel localModel : currentAccountList) {
                if (newModel.getUniqueIdentifier().equals(localModel.getUniqueIdentifier())) {
                    found = true;
                }
            }
            if (!found) {
                modelsToAdd.add(newModel);
            }
        }

    }
}
