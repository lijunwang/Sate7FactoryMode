package com.sate7.sate7factorymode.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.sate7.sate7factorymode.XLog;

public class SharedPreferencesHelper {
    public static boolean saveResult(Context context, String title, boolean success) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.edit().putBoolean(title, success).commit();
    }

    public static void debug(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        XLog.d("debug SharedPreferencesHelper ... " + sharedPreferences.getAll());
    }

    public static void listenChange(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }
    public static void unListenChange(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public static boolean isSuccess(Context context,String title){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(title,false);
    }
}
