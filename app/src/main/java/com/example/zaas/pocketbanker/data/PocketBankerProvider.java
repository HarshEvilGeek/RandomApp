package com.example.zaas.pocketbanker.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.zaas.pocketbanker.interfaces.IDataChangeListener;

import java.util.ArrayList;

/**
 * Created by akhil on 3/19/16.
 */
public class PocketBankerProvider extends ContentProvider {

    private static final String TAG = "ChatProvider";

    public static final Uri CONTENT_URI_ACCOUNTS = Uri.parse("content://" + PocketBankerContract.CONTENT_AUTHORITY + "/" + PocketBankerOpenHelper.Tables.ACCOUNT);
    public static final Uri CONTENT_URI_TRANSACTIONS = Uri.parse("content://" + PocketBankerContract.CONTENT_AUTHORITY + "/" + PocketBankerOpenHelper.Tables.TRANSACTIONS);
    public static final Uri CONTENT_URI_PAYEES = Uri.parse("content://" + PocketBankerContract.CONTENT_AUTHORITY + "/" + PocketBankerOpenHelper.Tables.PAYEES);
    public static final Uri CONTENT_URI_LOANS = Uri.parse("content://" + PocketBankerContract.CONTENT_AUTHORITY + "/" + PocketBankerOpenHelper.Tables.LOANS);
    public static final Uri CONTENT_URI_EMIS = Uri.parse("content://" + PocketBankerContract.CONTENT_AUTHORITY + "/" + PocketBankerOpenHelper.Tables.EMIS);
    public static final Uri CONTENT_URI_LOAN_TRANSACTIONS = Uri.parse("content://" + PocketBankerContract.CONTENT_AUTHORITY + "/" + PocketBankerOpenHelper.Tables.LOAN_TRANSACTIONS);
    public static final Uri CONTENT_URI_CARD = Uri.parse("content://" + PocketBankerContract.CONTENT_AUTHORITY + "/" + PocketBankerOpenHelper.Tables.CARD);

    private static final int ACCOUNT_ALL_ROWS = 1;
    private static final int TRANSACTIONS_ALL_ROWS = 3;
    private static final int PAYEES_ALL_ROWS = 5;
    private static final int LOANS_ALL_ROWS = 7;
    private static final int EMIS_ALL_ROWS = 9;
    private static final int LOAN_TRANSACTIONS_ALL_ROWS = 11;
    private static final int CARD_ALL_ROWS = 13;

    private static final UriMatcher uriMatcher;

    private static IDataChangeListener dataChangeListener;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PocketBankerContract.CONTENT_AUTHORITY, PocketBankerOpenHelper.Tables.ACCOUNT, ACCOUNT_ALL_ROWS);
        uriMatcher.addURI(PocketBankerContract.CONTENT_AUTHORITY, PocketBankerOpenHelper.Tables.TRANSACTIONS, TRANSACTIONS_ALL_ROWS);
        uriMatcher.addURI(PocketBankerContract.CONTENT_AUTHORITY, PocketBankerOpenHelper.Tables.PAYEES, PAYEES_ALL_ROWS);
        uriMatcher.addURI(PocketBankerContract.CONTENT_AUTHORITY, PocketBankerOpenHelper.Tables.LOANS, LOANS_ALL_ROWS);
        uriMatcher.addURI(PocketBankerContract.CONTENT_AUTHORITY, PocketBankerOpenHelper.Tables.EMIS, EMIS_ALL_ROWS);
        uriMatcher.addURI(PocketBankerContract.CONTENT_AUTHORITY, PocketBankerOpenHelper.Tables.LOAN_TRANSACTIONS, LOAN_TRANSACTIONS_ALL_ROWS);
        uriMatcher.addURI(PocketBankerContract.CONTENT_AUTHORITY, PocketBankerOpenHelper.Tables.ACCOUNT, CARD_ALL_ROWS);
    }

    private PocketBankerOpenHelper pocketBankerOpenHelper;

    @Override
    public boolean onCreate() {
        pocketBankerOpenHelper = new PocketBankerOpenHelper(getContext());
        return true;
    }

    private String getTableName(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ACCOUNT_ALL_ROWS:
                return PocketBankerOpenHelper.Tables.ACCOUNT;
            case TRANSACTIONS_ALL_ROWS:
                return PocketBankerOpenHelper.Tables.TRANSACTIONS;
            case PAYEES_ALL_ROWS:
                return PocketBankerOpenHelper.Tables.PAYEES;
            case LOANS_ALL_ROWS:
                return PocketBankerOpenHelper.Tables.LOANS;
            case EMIS_ALL_ROWS:
                return PocketBankerOpenHelper.Tables.EMIS;
            case LOAN_TRANSACTIONS_ALL_ROWS:
                return PocketBankerOpenHelper.Tables.LOAN_TRANSACTIONS;
            case CARD_ALL_ROWS:
                return PocketBankerOpenHelper.Tables.CARD;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = pocketBankerOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case ACCOUNT_ALL_ROWS:
            case TRANSACTIONS_ALL_ROWS:
            case PAYEES_ALL_ROWS:
            case LOANS_ALL_ROWS:
            case EMIS_ALL_ROWS:
            case LOAN_TRANSACTIONS_ALL_ROWS:
            case CARD_ALL_ROWS:
                qb.setTables(getTableName(uri));
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = pocketBankerOpenHelper.getWritableDatabase();

        long id;
        switch (uriMatcher.match(uri)) {
            case ACCOUNT_ALL_ROWS:
                id = db.insertOrThrow(PocketBankerOpenHelper.Tables.ACCOUNT, null, values);
                break;
            case TRANSACTIONS_ALL_ROWS:
                id = db.insertOrThrow(PocketBankerOpenHelper.Tables.TRANSACTIONS, null, values);
                break;
            case PAYEES_ALL_ROWS:
                id = db.insertOrThrow(PocketBankerOpenHelper.Tables.PAYEES, null, values);
                break;
            case LOANS_ALL_ROWS:
                id = db.insertOrThrow(PocketBankerOpenHelper.Tables.LOANS, null, values);
                break;
            case EMIS_ALL_ROWS:
                id = db.insertOrThrow(PocketBankerOpenHelper.Tables.EMIS, null, values);
                break;
            case LOAN_TRANSACTIONS_ALL_ROWS:
                id = db.insertOrThrow(PocketBankerOpenHelper.Tables.LOAN_TRANSACTIONS, null, values);
                break;
            case CARD_ALL_ROWS:
                id = db.insertOrThrow(PocketBankerOpenHelper.Tables.CARD, null, values);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Uri insertUri = ContentUris.withAppendedId(uri, id);
        notifyListenerIfNecessary(insertUri, values, null);
        return insertUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = pocketBankerOpenHelper.getWritableDatabase();

        int count;
        switch (uriMatcher.match(uri)) {
            case ACCOUNT_ALL_ROWS:
            case TRANSACTIONS_ALL_ROWS:
            case PAYEES_ALL_ROWS:
            case LOANS_ALL_ROWS:
            case EMIS_ALL_ROWS:
            case LOAN_TRANSACTIONS_ALL_ROWS:
            case CARD_ALL_ROWS:
                count = db.delete(getTableName(uri), selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        notifyListenerIfNecessary(uri, null, selection);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = pocketBankerOpenHelper.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case ACCOUNT_ALL_ROWS:
            case TRANSACTIONS_ALL_ROWS:
            case PAYEES_ALL_ROWS:
            case LOANS_ALL_ROWS:
            case EMIS_ALL_ROWS:
            case LOAN_TRANSACTIONS_ALL_ROWS:
            case CARD_ALL_ROWS:
                count = db.update(getTableName(uri), values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        notifyListenerIfNecessary(uri, values, selection);
        return count;
    }

    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = pocketBankerOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numInserted = 0;
        String table;

        switch (uriMatcher.match(uri)) {
            case ACCOUNT_ALL_ROWS:
                table = PocketBankerOpenHelper.Tables.ACCOUNT;
                break;
            case TRANSACTIONS_ALL_ROWS:
                table = PocketBankerOpenHelper.Tables.TRANSACTIONS;
                break;
            case PAYEES_ALL_ROWS:
                table = PocketBankerOpenHelper.Tables.PAYEES;
                break;
            case LOANS_ALL_ROWS:
                table = PocketBankerOpenHelper.Tables.LOANS;
                break;
            case EMIS_ALL_ROWS:
                table = PocketBankerOpenHelper.Tables.EMIS;
                break;
            case LOAN_TRANSACTIONS_ALL_ROWS:
                table = PocketBankerOpenHelper.Tables.LOAN_TRANSACTIONS;
                break;
            case CARD_ALL_ROWS:
                table = PocketBankerOpenHelper.Tables.CARD;
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        SQLiteDatabase db = pocketBankerOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            long time = System.currentTimeMillis();
            for (ContentValues cv : values) {
                long newID = db.insertOrThrow(table, null, cv);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }
            db.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } finally {
            db.endTransaction();
        }
        notifyListenerIfNecessary(uri, null, null);
        return numInserted;
    }

    public static void setDataChangeListener(IDataChangeListener dataChangeListener) {
        Log.d(TAG, "Data change listener set to " + dataChangeListener);
        PocketBankerProvider.dataChangeListener = dataChangeListener;
    }

    // This method can be modified to be more clever about how we notify about data changes
    private void notifyListenerIfNecessary(Uri uri, ContentValues values, String selection) {
        if (dataChangeListener != null) {
            dataChangeListener.onDataChange();
        }
    }
}
