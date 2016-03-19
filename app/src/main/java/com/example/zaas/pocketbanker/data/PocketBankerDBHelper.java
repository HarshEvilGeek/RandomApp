package com.example.zaas.pocketbanker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.zaas.pocketbanker.models.local.AccountModel;

import java.util.ArrayList;
import java.util.List;

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

    private ContentValues getValuesForAccount(AccountModel accountModel) {
        ContentValues values = new ContentValues();
        values.put(PocketBankerContract.Account._ID, accountModel.getId());
        values.put(PocketBankerContract.Account.ACCOUNT_NUMBER, accountModel.getAccountNumber());
        values.put(PocketBankerContract.Account.ACCOUNT_TYPE, accountModel.getType());
        values.put(PocketBankerContract.Account.BALANCE, accountModel.getBalance());
        values.put(PocketBankerContract.Account.TIME, accountModel.getLastUpdateTime());
        return values;
    }

    private AccountModel convertCursorToAccountModel(Cursor cursor) {
        AccountModel accountModel = new AccountModel();
        accountModel.setId(cursor.getInt(cursor.getColumnIndex(PocketBankerContract.Account._ID)));
        accountModel.setAccountNumber(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Account.ACCOUNT_NUMBER)));
        accountModel.setType(cursor.getString(cursor.getColumnIndex(PocketBankerContract.Account.ACCOUNT_TYPE)));
        accountModel.setBalance(cursor.getDouble(cursor.getColumnIndex(PocketBankerContract.Account.BALANCE)));
        accountModel.setLastUpdateTime(cursor.getLong(cursor.getColumnIndex(PocketBankerContract.Account.TIME)));
        return accountModel;
    }

    private List<AccountModel> getAllAccounts(Context context) {
        Cursor c = null;
        List<AccountModel> accountModelList = new ArrayList<>();
        try {
            c = context.getContentResolver().query(PocketBankerProvider.CONTENT_URI_ACCOUNTS, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    accountModelList.add(convertCursorToAccountModel(c));
                } while (c.moveToNext());
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return accountModelList;
    }

    private void insertOrUpdateAccountsTable(Context context, List<AccountModel> newAccountModelList) {
        List<AccountModel> accountModelsToDelete = new ArrayList<>();
        List<AccountModel> accountModelsToUpdate = new ArrayList<>();
        List<AccountModel> accountModelsToAdd = new ArrayList<>();

        List<AccountModel> currentAccountModelList = getAllAccounts(context);

        for (AccountModel localModel : currentAccountModelList) {
            boolean found = false;
            for (AccountModel newModel : newAccountModelList) {
            }
        }

    }


}
