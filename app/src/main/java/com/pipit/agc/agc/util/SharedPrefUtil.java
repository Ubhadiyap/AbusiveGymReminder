package com.pipit.agc.agc.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Eric on 2/6/2016.
 */
public class SharedPrefUtil {
    public static void putString(Context context, String key, String val){
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public static SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    public static double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    public static void putLong(Context context, String key, long val){
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, val);
        editor.commit();
    }

    public static long getLong(Context context, String key, long defaultlong){
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        return prefs.getLong(key, defaultlong);
    }

    public static void setFirstTime(Context context, boolean firsttime){
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firsttime", firsttime);
        editor.commit();
    }

    public static boolean getIsFirstTime(Context context){
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        return prefs.getBoolean("firsttime", true);
    }

    /**
     * Returns the time of last visit as a string expression.
     * @param context
     * @param sdf
     * @return
     */
    public static String getLastVisitString(Context context, SimpleDateFormat sdf){
        if (sdf==null){
            sdf = new SimpleDateFormat("MMM dd HH:mm");
        }
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        Long timeinms =  prefs.getLong("last_visit_time", -1);
        if (timeinms<0){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeinms);
        String s = sdf.format(cal.getTime());
        return s;
    }

    public static void updateLastVisitTime(Context context, Long time){
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = prefs.edit();
        editor.commit();
        editor.putLong("last_visit_time", time);
        editor.commit();
    }
}