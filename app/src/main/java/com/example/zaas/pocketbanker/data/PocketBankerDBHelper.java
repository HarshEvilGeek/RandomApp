package com.example.zaas.pocketbanker.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.example.zaas.pocketbanker.models.local.DbModel;
import com.example.zaas.pocketbanker.models.local.Payee;

/**
 * Created by akhil on 3/19/16.
 */
public class PocketBankerDBHelper
{
    private static final String TAG = "PocketBankerDBHelper";

    private static PocketBankerDBHelper instance;

    private static Map<String, Uri> tableToUriMap  = new HashMap<>();

    static {
        tableToUriMap.put(PocketBankerOpenHelper.Tables.ACCOUNTS, PocketBankerProvider.CONTENT_URI_ACCOUNTS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.TRANSACTIONS, PocketBankerProvider.CONTENT_URI_TRANSACTIONS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.PAYEES, PocketBankerProvider.CONTENT_URI_PAYEES);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.BRANCH_ATMS, PocketBankerProvider.CONTENT_URI_BRANCH_ATMS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.LOANS, PocketBankerProvider.CONTENT_URI_LOANS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.LOAN_TRANSACTIONS, PocketBankerProvider.CONTENT_URI_LOAN_TRANSACTIONS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.EMIS, PocketBankerProvider.CONTENT_URI_EMIS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.CARDS, PocketBankerProvider.CONTENT_URI_CARDS);
    }

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

    private void insertOrUpdateDbModelTable(Context context, List<? extends DbModel> newDbModels)
    {
        List<DbModel> modelsToUpdate = new ArrayList<>();
        List<DbModel> modelsToAdd = new ArrayList<>();

        List<? extends DbModel> currentDbModels = getAllAccounts(context);

        for (DbModel newModel : newDbModels) {
            boolean found = false;
            for (DbModel localModel : currentDbModels) {
                if (newModel.isEqual(localModel)) {
                    modelsToUpdate.add(newModel);
                    found = true;
                }
            }
            if (!found) {
                modelsToAdd.add(newModel);
            }
        }

        bulkInsertDbModels(context, modelsToAdd);
        batchUpdateDbModels(context, modelsToUpdate);
    }

    private void insertUpdateAndDeleteDbModelTable(Context context, List<? extends DbModel> newDbModels)
    {
        List<DbModel> modelsToDelete = new ArrayList<>();
        List<DbModel> modelsToUpdate = new ArrayList<>();
        List<DbModel> modelsToAdd = new ArrayList<>();

        List<? extends DbModel> currentDbModels = getAllAccounts(context);

        for (DbModel localModel : currentDbModels) {
            boolean found = false;
            for (DbModel newModel : newDbModels) {
                if (newModel.isEqual(localModel)) {
                    modelsToUpdate.add(newModel);
                    found = true;
                }
            }
            if (!found) {
                modelsToDelete.add(localModel);
            }
        }

        for (DbModel newModel : newDbModels) {
            boolean found = false;
            for (DbModel localModel : currentDbModels) {
                if (newModel.isEqual(localModel)) {
                    found = true;
                }
            }
            if (!found) {
                modelsToAdd.add(newModel);
            }
        }

        bulkInsertDbModels(context, modelsToAdd);
        batchUpdateDbModels(context, modelsToUpdate);
        batchDeleteDbModels(context, modelsToDelete);
    }

    private void bulkInsertDbModels(Context context, List<DbModel> dbModelsToInsert) {
        if (dbModelsToInsert == null || dbModelsToInsert.isEmpty()) {
            return;
        }
        ContentValues[] values = new ContentValues[dbModelsToInsert.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = dbModelsToInsert.get(i).toContentValues();
        }
        Uri contentUri = PocketBankerDBHelper.tableToUriMap.get(dbModelsToInsert.get(0).getTable());
        context.getContentResolver().bulkInsert(contentUri, values);
    }

    private void batchUpdateDbModels(Context context, List<DbModel> dbModelsToUpdate) {
        if (dbModelsToUpdate == null || dbModelsToUpdate.isEmpty()) {
            return;
        }
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        Uri contentUri = PocketBankerDBHelper.tableToUriMap.get(dbModelsToUpdate.get(0).getTable());
        for (DbModel data : dbModelsToUpdate) {
            operations.add(ContentProviderOperation.newUpdate(contentUri)
                    .withValues(data.toContentValues())
                    .withSelection(data.getSelectionString(), data.getSelectionValues())
                    .withYieldAllowed(true)
                    .build());
        }
        try {
            context.getContentResolver().applyBatch(PocketBankerContract.CONTENT_AUTHORITY, operations);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to update private groups with a batch operation", e);
        } catch (OperationApplicationException e) {
            Log.e(TAG, "Failed to update private groups with a batch operation", e);
        }
    }

    private void batchDeleteDbModels(Context context, List<DbModel> dbModelsToDelete) {
        if (dbModelsToDelete == null || dbModelsToDelete.isEmpty()) {
            return;
        }
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        Uri contentUri = PocketBankerDBHelper.tableToUriMap.get(dbModelsToDelete.get(0).getTable());
        for (DbModel data : dbModelsToDelete) {
            operations.add(ContentProviderOperation.newDelete(contentUri)
                    .withSelection(data.getSelectionString(), data.getSelectionValues())
                    .withYieldAllowed(true)
                    .build());
        }
        try {
            context.getContentResolver().applyBatch(PocketBankerContract.CONTENT_AUTHORITY, operations);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to delete private groups with a batch operation", e);
        } catch (OperationApplicationException e) {
            Log.e(TAG, "Failed to delete private groups with a batch operation", e);
        }
    }
}
