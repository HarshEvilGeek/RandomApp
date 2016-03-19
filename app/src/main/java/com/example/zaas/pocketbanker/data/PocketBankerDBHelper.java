package com.example.zaas.pocketbanker.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.example.zaas.pocketbanker.models.local.Account;

/**
 * Created by akhil on 3/19/16.
 */
public class PocketBankerDBHelper {

    private static PocketBankerDBHelper instance;

    private PocketBankerDBHelper() {
    }

    public static PocketBankerDBHelper getInstance() {
        if (instance == null) {
            instance = new PocketBankerDBHelper();
        }
        return instance;
    }

    private List<Account> getAllAccounts(Context context)
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

    private void insertOrUpdateAccountsTable(Context context, List<Account> newAccountList)
    {
        List<Account> accountModelsToDelete = new ArrayList<>();
        List<Account> accountModelsToUpdate = new ArrayList<>();
        List<Account> accountModelsToAdd = new ArrayList<>();

        List<Account> currentAccountList = getAllAccounts(context);

        for (Account localModel : currentAccountList) {
            boolean found = false;
            for (Account newModel : newAccountList) {
            }
        }

    }

}
