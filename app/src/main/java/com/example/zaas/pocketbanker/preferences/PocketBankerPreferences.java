package com.example.zaas.pocketbanker.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.zaas.pocketbanker.application.PocketBankerApplication;

/**
 * Created by adugar on 3/27/16.
 */
public class PocketBankerPreferences
{
    public static final String NAME = "Name";
    private static final String PREFERENCES = "PocketBankerPrefs";

    public static String get(String key)
    {
        SharedPreferences prefs = PocketBankerApplication.getApplication().getSharedPreferences(PREFERENCES,
                Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void put(String key, String value)
    {
        SharedPreferences prefs = PocketBankerApplication.getApplication().getSharedPreferences(PREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
