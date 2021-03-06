package com.example.zaas.pocketbanker.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.example.zaas.pocketbanker.application.PocketBankerApplication;
import com.example.zaas.pocketbanker.models.local.Account;
import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.example.zaas.pocketbanker.models.local.DbModel;
import com.example.zaas.pocketbanker.models.local.LoanEMI;
import com.example.zaas.pocketbanker.models.local.Payee;
import com.example.zaas.pocketbanker.models.local.Recommendation;
import com.example.zaas.pocketbanker.models.local.Shop;
import com.example.zaas.pocketbanker.models.local.Transaction;
import com.example.zaas.pocketbanker.models.local.TransactionCategory;

/**
 * Created by akhil on 3/19/16.
 */
public class PocketBankerDBHelper
{
    private static final String TAG = "PocketBankerDBHelper";

    private static PocketBankerDBHelper instance;
    private static Map<String, Uri> tableToUriMap = new HashMap<>();

    static {
        tableToUriMap.put(PocketBankerOpenHelper.Tables.ACCOUNTS, PocketBankerProvider.CONTENT_URI_ACCOUNTS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.TRANSACTIONS, PocketBankerProvider.CONTENT_URI_TRANSACTIONS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.PAYEES, PocketBankerProvider.CONTENT_URI_PAYEES);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.BRANCH_ATMS, PocketBankerProvider.CONTENT_URI_BRANCH_ATMS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.LOANS, PocketBankerProvider.CONTENT_URI_LOANS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.LOAN_TRANSACTIONS,
                PocketBankerProvider.CONTENT_URI_LOAN_TRANSACTIONS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.EMIS, PocketBankerProvider.CONTENT_URI_EMIS);
        tableToUriMap.put(PocketBankerOpenHelper.Tables.CARDS, PocketBankerProvider.CONTENT_URI_CARDS);
    }

    private Context context;

    private PocketBankerDBHelper()
    {
        context = PocketBankerApplication.getApplication().getApplicationContext();
    }

    public static PocketBankerDBHelper getInstance()
    {
        if (instance == null) {
            instance = new PocketBankerDBHelper();
        }
        return instance;
    }

    public List<Account> getAllAccounts()
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

    public List<Payee> getAllPayees()
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

    public List<Recommendation> getAllRecommendations()
    {
        Cursor c = null;
        List<Recommendation> recommendationList = new ArrayList<>();
        try {
            c = context.getContentResolver().query(PocketBankerProvider.CONTENT_URI_RECOMMEDATIONS, null, null, null,
                    null);
            if (c != null) {
                while (c.moveToNext()) {
                    Recommendation recommendation = new Recommendation();
                    recommendation.instantiateFromCursor(c);
                    recommendationList.add(recommendation);
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return recommendationList;
    }

    public List<Shop> getAllShops()
    {
        Cursor c = null;
        List<Shop> shopList = new ArrayList<>();
        Shop shop = new Shop();
        shop.setId(1);
        shop.setName("Recharge your mobile");
        shop.setUrl("http://files.hostgator.co.in/hostgator244459/image/recharge_m_1a.jpg");
        Shop shop2 = new Shop();
        shop2.setId(2);
        shop2.setName("McDonalds");
        shop2.setUrl("http://2.bp.blogspot.com/-4hgRPCvosa0/VCoWtmcTfLI/AAAAAAAABZE/YPap5ES5_o0/s1600/mcd-logo.png");
        Shop shop3 = new Shop();
        shop3.setId(3);
        shop3.setName("Book My Show");
        shop3.setUrl("http://www.afaqs.com/all/news/images/news_story_grfx/2013/12/39336/Bookmyshow-new-logo.jpg");
        Shop shop4 = new Shop();
        shop4.setId(4);
        shop4.setName("Domino's Pizza");
        shop4.setUrl("http://www.techyville.com/wp-content/uploads/2015/01/dominos.png");
        Shop shop5 = new Shop();
        shop5.setId(5);
        shop5.setName("Gucci");
        shop5.setUrl("http://cdn-4.famouslogos.us/images/gucci-logo.jpg");
        Shop shop6 = new Shop();
        shop6.setId(6);
        shop6.setName("Myntra");
        shop6.setUrl("http://techstory.in/wp-content/uploads/2016/02/Myntra-Customer-Care-Contact-Phone-Number.png");
        Shop shop7 = new Shop();
        shop7.setId(7);
        shop7.setName("Amazon");
        shop7.setUrl("http://scottmarshallmusic.com/wp-content/uploads/2015/01/amazon-icon.png");
        Shop shop8 = new Shop();
        shop8.setId(8);
        shop8.setName("Body Shop");
        shop8.setUrl("https://appscommon.blob.core.windows.net/muscat-container/images/shopImages/body_shop_logo.jpg");
        shopList.add(shop);
        shopList.add(shop2);
        shopList.add(shop3);
        shopList.add(shop4);
        shopList.add(shop5);
        shopList.add(shop6);
        shopList.add(shop7);
        shopList.add(shop8);
        return shopList;
    }

    public List<Transaction> getAllTransactions()
    {
        Cursor c = null;
        List<Transaction> transactionList = new ArrayList<>();
        try {
            c = context.getContentResolver().query(PocketBankerProvider.CONTENT_URI_TRANSACTIONS, null, null, null,
                    null);
            if (c != null) {
                while (c.moveToNext()) {
                    Transaction transaction = new Transaction();
                    transaction.instantiateFromCursor(c);
                    transactionList.add(transaction);
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return transactionList;
    }

    public List<LoanEMI> getAllLoanEmis()
    {
        Cursor c = null;
        List<LoanEMI> loanEMIs = new ArrayList<>();
        try {
            c = context.getContentResolver().query(PocketBankerProvider.CONTENT_URI_EMIS, null, null, null,
                    null);
            if (c != null) {
                while (c.moveToNext()) {
                    LoanEMI loanEMI = new LoanEMI();
                    loanEMI.instantiateFromCursor(c);
                    loanEMIs.add(loanEMI);
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return loanEMIs;
    }

    public void updateTransaction(int id, ContentValues contentValues)
    {
        try {
            context.getContentResolver().update(
                    ContentUris.withAppendedId(PocketBankerProvider.CONTENT_URI_TRANSACTIONS, id), contentValues, null,
                    null);
        }
        catch (Exception e) {
            // Do nothing
        }
    }

    public List<TransactionCategory> getAllTransactionCategories()
    {
        Cursor c = null;
        List<TransactionCategory> transactionCategoryList = new ArrayList<>();
        try {
            c = context.getContentResolver().query(PocketBankerProvider.CONTENT_URI_TRANSACTION_CATEGORIES, null, null,
                    null, null);
            if (c != null) {
                while (c.moveToNext()) {
                    TransactionCategory transactionCategory = new TransactionCategory();
                    transactionCategory.instantiateFromCursor(c);
                    transactionCategoryList.add(transactionCategory);
                }
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return transactionCategoryList;
    }

    public void insertTransactionCategory(TransactionCategory transactionCategory)
    {
        try {
            context.getContentResolver().insert(PocketBankerProvider.CONTENT_URI_TRANSACTION_CATEGORIES,
                    transactionCategory.toContentValues());
        }
        catch (Exception e) {
            // Do nothing
        }
    }

    public Payee getPayeeForLocalId(int id)
    {
        Cursor c = null;
        Payee payee = null;
        try {
            c = context.getContentResolver().query(
                    ContentUris.withAppendedId(PocketBankerProvider.CONTENT_URI_PAYEES, id), null, null, null, null);
            if (c != null && c.moveToFirst()) {
                payee = new Payee();
                payee.instantiateFromCursor(c);
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return payee;
    }

    public List<BranchAtm> getAllBranchAtms()
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

    public BranchAtm getBranchAtmForLocalId(int id)
    {
        Cursor c = null;
        BranchAtm branchAtm = null;
        try {
            c = context.getContentResolver().query(
                    ContentUris.withAppendedId(PocketBankerProvider.CONTENT_URI_BRANCH_ATMS, id), null, null, null,
                    null);
            if (c != null && c.moveToNext()) {
                branchAtm = new BranchAtm();
                branchAtm.instantiateFromCursor(c);
            }
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
        return branchAtm;
    }

    public List<? extends DbModel> getAllDbModels(String table)
    {
        switch (table)
        {
        case PocketBankerOpenHelper.Tables.ACCOUNTS:
            return getAllAccounts();
        case PocketBankerOpenHelper.Tables.BRANCH_ATMS:
            return getAllBranchAtms();
        case PocketBankerOpenHelper.Tables.PAYEES:
            return getAllPayees();
        case PocketBankerOpenHelper.Tables.TRANSACTIONS:
            return getAllTransactions();
        case PocketBankerOpenHelper.Tables.EMIS:
            return getAllLoanEmis();
        }
        return null;
    }

    public void insertOrUpdateDbModelTable(List<? extends DbModel> newDbModels)
    {
        if (newDbModels == null || newDbModels.isEmpty()) {
            // TODO delete all entries for passed table
            return;
        }
        
        List<DbModel> modelsToUpdate = new ArrayList<>();
        List<DbModel> modelsToAdd = new ArrayList<>();

        List<? extends DbModel> currentDbModels = getAllDbModels(newDbModels.get(0).getTable());

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

        bulkInsertDbModels(modelsToAdd);
        batchUpdateDbModels(modelsToUpdate);
    }

    public void insertUpdateAndDeleteDbModelTable(List<? extends DbModel> newDbModels)
    {
        if (newDbModels == null || newDbModels.isEmpty()) {
            // TODO delete all entries for passed table
            return;
        }
        List<DbModel> modelsToDelete = new ArrayList<>();
        List<DbModel> modelsToUpdate = new ArrayList<>();
        List<DbModel> modelsToAdd = new ArrayList<>();

        List<? extends DbModel> currentDbModels = getAllDbModels(newDbModels.get(0).getTable());

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

        bulkInsertDbModels(modelsToAdd);
        batchUpdateDbModels(modelsToUpdate);
        batchDeleteDbModels(modelsToDelete);
    }

    private void bulkInsertDbModels(List<DbModel> dbModelsToInsert)
    {
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

    private void batchUpdateDbModels(List<DbModel> dbModelsToUpdate)
    {
        if (dbModelsToUpdate == null || dbModelsToUpdate.isEmpty()) {
            return;
        }
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        Uri contentUri = PocketBankerDBHelper.tableToUriMap.get(dbModelsToUpdate.get(0).getTable());
        for (DbModel data : dbModelsToUpdate) {
            operations
                    .add(ContentProviderOperation.newUpdate(contentUri).withValues(data.toContentValues())
                            .withSelection(data.getSelectionString(), data.getSelectionValues()).withYieldAllowed(true)
                            .build());
        }
        try {
            context.getContentResolver().applyBatch(PocketBankerContract.CONTENT_AUTHORITY, operations);
        }
        catch (RemoteException e) {
            Log.e(TAG, "Failed to update private groups with a batch operation", e);
        }
        catch (OperationApplicationException e) {
            Log.e(TAG, "Failed to update private groups with a batch operation", e);
        }
    }

    private void batchDeleteDbModels(List<DbModel> dbModelsToDelete)
    {
        if (dbModelsToDelete == null || dbModelsToDelete.isEmpty()) {
            return;
        }
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        Uri contentUri = PocketBankerDBHelper.tableToUriMap.get(dbModelsToDelete.get(0).getTable());
        for (DbModel data : dbModelsToDelete) {
            operations
                    .add(ContentProviderOperation.newDelete(contentUri)
                            .withSelection(data.getSelectionString(), data.getSelectionValues()).withYieldAllowed(true)
                            .build());
        }
        try {
            context.getContentResolver().applyBatch(PocketBankerContract.CONTENT_AUTHORITY, operations);
        }
        catch (RemoteException e) {
            Log.e(TAG, "Failed to delete private groups with a batch operation", e);
        }
        catch (OperationApplicationException e) {
            Log.e(TAG, "Failed to delete private groups with a batch operation", e);
        }
    }
}
