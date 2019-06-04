package com.renzpalo.baraka.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.renzpalo.baraka.MyApp;

public class SharedPreferenceUtility {

    private static String PREFERENCE_NAME = "baraka";
    private static SharedPreferenceUtility sharedPreferenceUtility;
    private SharedPreferences sharedPreferences;

    private SharedPreferenceUtility(Context context) {
        PREFERENCE_NAME = PREFERENCE_NAME + context.getPackageName();

        // MODE_PRIVATE only this app can use SharedPreference
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtility getInstance() {
        // If the SharedPreference is NULL
        // Initialize it.

        if (sharedPreferenceUtility == null) {
            sharedPreferenceUtility = new SharedPreferenceUtility(MyApp.getContext());
        }
        return sharedPreferenceUtility;
    }

    // Login response user_id = 1234
    public void saveString(String key, String val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getString(String key, String defaultVal) {
        return sharedPreferences.getString(key, defaultVal);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void saveInt(String key, int val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }




}
