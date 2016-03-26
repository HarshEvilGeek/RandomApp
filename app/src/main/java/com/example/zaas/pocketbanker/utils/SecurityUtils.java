package com.example.zaas.pocketbanker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.zaas.pocketbanker.application.PocketBankerApplication;
import com.example.zaas.pocketbanker.fragments.PinOrFingerprintFragment;
import com.example.zaas.pocketbanker.models.local.PocketAccount;
import com.example.zaas.pocketbanker.models.network.WalletStatement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zaas on 3/17/16.
 */
public class SecurityUtils {

    private static final String DEFAULT_SHARED_PREF = "user";

    private static final String POCKET_ACCOUNT_KEY = "POCKET_ACCOUNT_KEY";
    private static final String WALLET_STATEMENT_KEY = "WALLET_STATEMENT_KEY";
    private static final String PIN_CODE_KEY = "PIN_CODE_KEY";
    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";
    private static final String LAST_ACCESS_KEY = "LAST_ACCESS_KEY";

    private static final long ACCESS_TOKEN_EXPIRY = 60l * 24l * 60l * 60l * 1000l; // 60 days
    private static final long SESSION_EXPIRY = 5 * 60 * 1000; // 2 minutes

    private static List<WalletStatement> walletStatementList;
    private static PocketAccount pocketAccount;

    private static SharedPreferences preferences;

    private static String accessToken;
    private static long accessTokenLastSet;
    private static long lastAccessTime;

    private static boolean showLogin = true;

    private static String pinCode;

    private static SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = PocketBankerApplication.getApplication()
                    .getSharedPreferences(DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public static String getAccessToken() {
        if (TextUtils.isEmpty(accessToken)) {
            accessToken = getPreferences().getString(ACCESS_TOKEN_KEY, "");
        }
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        SecurityUtils.accessToken = accessToken;
        getPreferences().edit().putString(ACCESS_TOKEN_KEY, accessToken).apply();
        accessTokenLastSet = System.currentTimeMillis();
    }

    public static long getLastAccess() {
        if (lastAccessTime == 0) {
            lastAccessTime = getPreferences().getLong(LAST_ACCESS_KEY, 0);
        }
        return lastAccessTime;
    }

    public static void setLastAccess(long lastAccess) {
        lastAccessTime = lastAccess;
        getPreferences().edit().putLong(LAST_ACCESS_KEY, lastAccessTime).apply();
    }

    public static boolean isAccessAuthorized() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - getLastAccess() > SESSION_EXPIRY) {
            return false;
        }
        if (TextUtils.isEmpty(getAccessToken()) && showLogin) {
            return false;
        } else {
            setLastAccess(currentTime);
            return true;
        }
    }

    public static String getUserName() {
        return "akhilcherian@gmail.com";
    }

    public static String getPassword(String pinCode) {
        return "ICIC7202";
    }

    public static void saveUserNameAndPassword(String passCode, String userName, String password) {

    }

    public static void savePinCode(String pin) {
        pinCode = pin;
        setLastAccess(System.currentTimeMillis());
        getPreferences().edit().putString(PIN_CODE_KEY, pinCode).apply();
    }

    public static String getPinCode() {
        if (TextUtils.isEmpty(pinCode)) {
            pinCode = getPreferences().getString(PIN_CODE_KEY, "");
        }
        return pinCode;
    }

    public static boolean isValidPin(String pinCode) {
        boolean isValid = pinCode.equals(getPinCode());
        if (isValid) {
            setLastAccess(System.currentTimeMillis());
        }
        return isValid;
    }

    public static boolean isLoginDataStored() {
        return !TextUtils.isEmpty(getPinCode());
    }

    public static PocketAccount getPocketsAccount() {
        if (pocketAccount == null) {
            String jsonString = getPreferences().getString(POCKET_ACCOUNT_KEY, "");
            if (!TextUtils.isEmpty(jsonString)) {
                pocketAccount = new Gson().fromJson(jsonString, PocketAccount.class);
            }
        }
        return pocketAccount;
    }

    public static void savePocketsAccount(PocketAccount pocketAccount) {
        SecurityUtils.pocketAccount = pocketAccount;
        getPreferences().edit().putString(POCKET_ACCOUNT_KEY, new Gson().toJson(pocketAccount)).apply();
    }

    public static List<WalletStatement> getWalletStatement() {
        if (walletStatementList == null) {
            String jsonString = getPreferences().getString(WALLET_STATEMENT_KEY, "");
            if (!TextUtils.isEmpty(jsonString)) {
                Type statementListType = new TypeToken<List<WalletStatement>>() {}.getType();
                walletStatementList = new Gson().fromJson(jsonString, statementListType);
            }
        }
        return walletStatementList;
    }

    public static void saveWalletStatement(List<WalletStatement> walletStatement) {
        walletStatementList = walletStatement;
        getPreferences().edit().putString(WALLET_STATEMENT_KEY, new Gson().toJson(walletStatementList)).apply();
    }
}
