package com.example.zaas.pocketbanker.application;

import android.app.Application;

import com.example.zaas.pocketbanker.utils.TransactionCategoryUtils;

/**
 * Main application class
 * 
 * Created by adugar on 3/25/16.
 */
public class PocketBankerApplication extends Application
{
    private static PocketBankerApplication sInstance;

    public static PocketBankerApplication getApplication()
    {
        return sInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        sInstance = this;

        TransactionCategoryUtils.populateTransactionCategoryMap();
    }
}
