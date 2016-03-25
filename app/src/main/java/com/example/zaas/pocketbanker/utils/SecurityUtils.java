package com.example.zaas.pocketbanker.utils;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.zaas.pocketbanker.fragments.PinOrFingerprintFragment;

import org.w3c.dom.Text;

/**
 * Created by zaas on 3/17/16.
 */
public class SecurityUtils {

    private static final long ACCESS_TOKEN_EXPIRY = 60l * 24l * 60l * 60l * 1000l; // 60 days
    private static final long SESSION_EXPIRY = 2 * 60 * 1000; // 2 minutes

    private static String accessToken;
    private static long accessTokenLastSet;
    private static long lastAccessTime;

    private static boolean showLogin = false;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        SecurityUtils.accessToken = accessToken;
        accessTokenLastSet = System.currentTimeMillis();
    }

    public static boolean isAccessAuthorized() {
        lastAccessTime = System.currentTimeMillis();
        if (TextUtils.isEmpty(getAccessToken()) && showLogin) {
            return false;
        } else {
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

    public static boolean isValidPin(String pinCode) {
        return true;
    }

    public static boolean isLoginDataStored() {
        return false;
    }
}
