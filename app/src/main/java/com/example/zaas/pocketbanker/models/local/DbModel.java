package com.example.zaas.pocketbanker.models.local;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by akhil on 3/20/16.
 */
public abstract class DbModel {
    public abstract void instantiateFromCursor(Cursor cursor);
    public abstract String getUniqueIdentifier();
    public abstract ContentValues toContentValues();
    public abstract String getTable();
}
