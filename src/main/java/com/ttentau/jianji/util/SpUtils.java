package com.ttentau.jianji.util;

import android.content.SharedPreferences;

/**
 * Created by ttentau on 2017/6/15.
 */

public class SpUtils {
    public static void setLock(String key ,String value){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("isLock", 0);
        sp.edit().putString(key,value).commit();
    }
    public static String getLock(String key ,String defvalue){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("isLock", 0);
        String result = sp.getString(key, defvalue);
        return result;
    }
    public static void putType(String key,String value){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("defulttype", 0);
        sp.edit().putString(key,value).commit();
    }
    public static String getType(String key,String defvalue){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("defulttype", 0);
        String result = sp.getString(key, defvalue);
        return result;
    }
    public static void putRemainder(String key,float value){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("remainder", 0);
        sp.edit().putFloat(key,value).commit();
    }
    public static float getRemainder(String key,float defvalue){
        SharedPreferences sp = UIUtils.getContext().getSharedPreferences("remainder", 0);
        float result = sp.getFloat(key, defvalue);
        return result;
    }
}
