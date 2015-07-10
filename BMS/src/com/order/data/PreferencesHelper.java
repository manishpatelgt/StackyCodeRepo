package com.order.data;

import android.content.SharedPreferences;

import com.order.application.BMSAapplication;

/**
 * Created by Manish on 3/1/2015.
 */
public class PreferencesHelper {

    private static final String IS_LOGIN_PREFS = "Is_Login";
    private static final String IS_PREFS = "Is_FirstTime";
    private static final String PREF_KEY_USER_ID = "UserId";
    private static final String PREF_KEY_USER_NAME = "UserName";
    private static final String PREF_KEY_USER_ROLL_ID = "userRollId";
    private static final String PREF_KEY_USER_ROLL= "userRolename";

    public static Boolean getBoolean(String key, Boolean defValue) {
        return BMSAapplication.getPreferences().getBoolean(key, defValue);
    }

    public static void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = BMSAapplication.getPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(String key, String defValue) {
        return BMSAapplication.getPreferences().getString(key, defValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = BMSAapplication.getPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defValue) {
        return BMSAapplication.getPreferences().getInt(key, defValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = BMSAapplication.getPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getLoginCheck() {
        return getBoolean(IS_LOGIN_PREFS, false);
    }

    public static void setLoginCheck(boolean tested) {
        putBoolean(IS_LOGIN_PREFS, tested);
    }
    
    public static boolean getFirstTime() {
        return getBoolean(IS_PREFS, true);
    }

    public static void setFirstTime(boolean tested) {
        putBoolean(IS_PREFS, tested);
    }

    public static int getUserID() {
        return getInt(PREF_KEY_USER_ID, 0);
    }

    public static void setUserID(int value) {
        putInt(PREF_KEY_USER_ID, value);
    }

    public static String getUserName() {
        return getString(PREF_KEY_USER_NAME, "");
    }

    public static void setUserName(String value) {
        putString(PREF_KEY_USER_NAME, value);
    }

    public static int getRollId() {
        return getInt(PREF_KEY_USER_ROLL_ID, 0);
    }

    public static void setRollId(int value) {
        putInt(PREF_KEY_USER_ROLL_ID, value);
    }
    
    public static String getUserRole() {
        return getString(PREF_KEY_USER_ROLL, "");
    }

    public static void setUserRole(String value) {
        putString(PREF_KEY_USER_ROLL, value);
    }

}
