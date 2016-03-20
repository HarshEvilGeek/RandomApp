package com.example.zaas.pocketbanker.utils;

/**
 * Created by zaas on 3/17/16.
 */
public class SecurityUtils {

    private static final long ACCESS_TOKEN_EXPIRY = 60l * 24l * 60l * 60l * 1000l; // 60 days
    private static final long SESSION_EXPIRY = 2 * 60 * 1000; // 2 minutes

    private static String accessToken;
    private static long accessTokenLastSet;
    private static long lastAccessTime;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        SecurityUtils.accessToken = accessToken;
        accessTokenLastSet = System.currentTimeMillis();
    }

    public static boolean isAccessAuthorized() {
        lastAccessTime = System.currentTimeMillis();
        return true;
    }

    public static boolean isLoginDataStored() {
        return false;
    }
}
