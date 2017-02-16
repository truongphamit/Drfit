package com.drteam.truongpq.drfit.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by truongpq on 16/11/2016.
 */

public class MySharedPreferences {
    private static final String SHARE_PREFERENCES_NAME = "settings";
    private static final String KEY_START_DATE = "date";
    private static final String KEY_NAME = "name";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_AGE = "age";

    public static void setDate(Context context, long ms) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        settings.edit().putLong(KEY_START_DATE, ms).apply();
    }

    public static long getDate(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        return settings.getLong(KEY_START_DATE, System.currentTimeMillis() * 3 / 4);
    }

    public static void setName(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        settings.edit().putString(KEY_NAME, name).apply();
    }

    public static String getName(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        return settings.getString(KEY_NAME, null);
    }

    public static void setHeight(Context context, int height) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        settings.edit().putInt(KEY_HEIGHT, height).apply();
    }

    public static int getHeight(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        return settings.getInt(KEY_HEIGHT, 0);
    }

    public static void setWeight(Context context, int weight) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        settings.edit().putInt(KEY_WEIGHT, weight).apply();
    }

    public static int getWeight(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        return settings.getInt(KEY_WEIGHT, 0);
    }

    public static void setAge(Context context, int age) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        settings.edit().putInt(KEY_AGE, age).apply();
    }

    public static int getAge(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SHARE_PREFERENCES_NAME, 0);
        return settings.getInt(KEY_AGE, 0);
    }
}
