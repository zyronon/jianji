package com.ttentau.jianji.util;

import android.annotation.SuppressLint;


import com.ttentau.jianji.bean.ChartData;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ttentau on 2017/6/13.
 */
@SuppressLint("WrongConstant")
public class DateUtils {
    public static int mYear;
    public static int mMouth;
    public static int mDay;
    public static int mHour;
    public static int mMinute;
    public static int mSercond;
    public static Calendar sInstance = Calendar.getInstance();

    public static String getCurrentDay() {
        sInstance.setTimeInMillis(System.currentTimeMillis());
        mYear = sInstance.get(Calendar.YEAR);
        mMouth = sInstance.get(Calendar.MONTH) + 1;
        mDay = sInstance.get(Calendar.DAY_OF_MONTH);
        mHour = sInstance.get(Calendar.HOUR_OF_DAY);
        mMinute = sInstance.get(Calendar.MINUTE);
        return mYear + "-" + (mMouth < 10 ? "0" + mMouth : mMouth) + "-" + (mDay < 10 ? "0" + mDay : mDay);
//        return mYear + "-" + mMouth + "-" +  mDay;
    }

    public static String getCurrentMinute() {
        sInstance.setTimeInMillis(System.currentTimeMillis());
        mYear = sInstance.get(Calendar.YEAR);
        mMouth = sInstance.get(Calendar.MONTH) + 1;
        mDay = sInstance.get(Calendar.DAY_OF_MONTH);
        mHour = sInstance.get(Calendar.HOUR_OF_DAY);
        mMinute = sInstance.get(Calendar.MINUTE);
        mSercond = sInstance.get(Calendar.SECOND);
        return (mHour < 10 ? "0" + mHour : mHour) + ":" + (mMinute < 10 ? "0" + mMinute : mMinute) + ":" + (mSercond < 10 ? "0" + mSercond : mSercond);
//        return  mHour + ":" + mMinute + ":" + mSercond;
    }

    public static int getDbSaveMinute() {
        sInstance.setTimeInMillis(System.currentTimeMillis());
        mHour = sInstance.get(Calendar.HOUR_OF_DAY);
        mMinute = sInstance.get(Calendar.MINUTE);
        mSercond = sInstance.get(Calendar.SECOND);
        String s = (mHour < 10 ? "0" + mHour : mHour) + "" + (mMinute < 10 ? "0" + mMinute : mMinute) + "" + (mSercond < 10 ? "0" + mSercond : mSercond);
        return Integer.parseInt(s);
    }

    public static int getDbSaveDay() {
        sInstance.setTimeInMillis(System.currentTimeMillis());
        mYear = sInstance.get(Calendar.YEAR);
        mMouth = sInstance.get(Calendar.MONTH) + 1;
        mDay = sInstance.get(Calendar.DAY_OF_MONTH);
        String s = mYear + "" + (mMouth < 10 ? "0" + mMouth : mMouth) + "" + (mDay < 10 ? "0" + mDay : mDay);
        return Integer.parseInt(s);
    }

    public static int getYear() {
        sInstance.setTimeInMillis(System.currentTimeMillis());
        return sInstance.get(Calendar.YEAR);
    }

    public static int getMouth() {
        sInstance.setTimeInMillis(System.currentTimeMillis());
        return sInstance.get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        sInstance.setTimeInMillis(System.currentTimeMillis());
        return sInstance.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour() {
        sInstance.setTimeInMillis(System.currentTimeMillis());
        return sInstance.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        sInstance.setTimeInMillis(System.currentTimeMillis());
        return sInstance.get(Calendar.MINUTE);
    }

    public static String formatDayOrMinuteToDisplay(int year, int mouth, int day, boolean isDay) {
        String temp = "";
        if (isDay) {
            temp = year + "-" + (mouth < 10 ? "0" + mouth : mouth) + "-" + (day < 10 ? "0" + day : day);
        } else {
            temp = year + ":" + (mouth < 10 ? "0" + mouth : mouth) + ":" + (day < 10 ? "0" + day : day);
        }
        return temp;
    }

    public static String formatDayOrMinuteToDisplay(int data, boolean isDay, boolean isHasYear,boolean isHasDay) {
        String temp = data + "";
        if (isDay) {
            if (!isHasYear)
                if (!isHasDay)temp = temp.substring(4, 6);
                else temp = temp.substring(4, 6) + "-" + temp.substring(6, 8);
            else
                temp = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
//            LogUtils.e("whywhy?????????????//"+temp);
        } else {
            if (temp.length() == 1) temp = "00000" + temp;
            if (temp.length() == 2) temp = "0000" + temp;
            if (temp.length() == 3) temp = "000" + temp;
            if (temp.length() == 4) temp = "00" + temp;
            if (temp.length() == 5) temp = "0" + temp;
            temp = temp.substring(0, 2) + ":" + temp.substring(2, 4) + ":" + temp.substring(4, 6);
        }
        return temp;
    }

    public static int formatDayOrMinuteToSava(String values, boolean isDay) {
        if (isDay) {
            String replace = values.replace("-", "");
            int i = Integer.parseInt(replace);
            return i;
        } else {
            String replace = values.replace(":", "");
            int i = Integer.parseInt(replace);
            return i;
        }
    }

    public static int getCurrentMouthMaxDays() {
        int day = sInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getCurrentMouthLastDay(int days) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.DAY_OF_MONTH, days);
        int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    public static int getCurrentMouthFirstDay() {
        int dbSaveDay = getDbSaveDay();
        dbSaveDay = dbSaveDay - dbSaveDay % 100 + 1;
        return dbSaveDay;
    }

    public static ArrayList<ChartData> getListDataCurrentMouth() {
        ArrayList<ChartData> list = new ArrayList<>();
        int dbSaveDay = getDbSaveDay();
        int fristDay = dbSaveDay - dbSaveDay % 100 + 1;
        int lastDay = dbSaveDay - dbSaveDay % 100 + getCurrentMouthMaxDays();
        for (int i = fristDay; i <= lastDay; i++) {
            ChartData data = new ChartData();
            data.date = i;
            list.add(data);
        }
        return list;
    }
    public static int[] getThisMonthFirstAndEnd(){
        int[] result=new int[2];
        int dbSaveDay = getDbSaveDay();
        int fristDay = dbSaveDay - dbSaveDay % 100 + 1;
        int lastDay = dbSaveDay - dbSaveDay % 100 + getCurrentMouthMaxDays();
        result[0]=fristDay;
        result[1]=lastDay;
        return result;
    }

    public static ArrayList<ChartData> getListDataRecentlyThreeMouth() {
        ArrayList<ChartData> list = new ArrayList<>();
        int thisMouth = getMouth();
        int thisYear = getYear();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.MONTH, -2);
        int lastMouth = c.get(Calendar.MONTH) + 1;
        int lastYear = c.get(Calendar.YEAR);
        if (thisMouth - lastMouth < 0) {
            for (int i = lastMouth; i <= 12; i++) {
                ChartData da = new ChartData();
                ChartData da1 = new ChartData();
                da.date = Integer.parseInt(lastYear + "" + (i < 10 ? "0" + i : i) + "01");
                da1.date = Integer.parseInt(lastYear + "" + (i < 10 ? "0" + i : i) + "15");
                list.add(da);
                list.add(da1);
            }
            for (int i = 1; i <= thisMouth+1; i++) {
                ChartData da = new ChartData();
                ChartData da1 = new ChartData();
                da.date = Integer.parseInt(thisYear + "" + (i < 10 ? "0" + i : i) + "01");
                da1.date = Integer.parseInt(thisYear + "" + (i < 10 ? "0" + i : i) + "15");
                list.add(da);
                list.add(da1);
            }
        } else {
            for (int i = lastMouth; i <= thisMouth+1; i++) {
                ChartData da = new ChartData();
                ChartData da1 = new ChartData();
                da.date = Integer.parseInt(thisYear + "" + (i < 10 ? "0" + i : i) + "01");
                list.add(da);
                if (i!=thisMouth+1){
                    da1.date = Integer.parseInt(thisYear + "" + (i < 10 ? "0" + i : i) + "15");
                    list.add(da1);
                }
            }
        }

        return list;
    }

    public static ArrayList<ChartData> getListDataThisYear() {
        ArrayList<ChartData> list = new ArrayList<>();
        int year = getYear();
        for (int i = 1; i <= 12; i++) {
            ChartData d = new ChartData();
            d.date = Integer.parseInt(year +"" + (i < 10 ? "0" + i : i) + "01");
            list.add(d);
        }
        return list;
    }

    public static ArrayList<ChartData> getListDataRecentlySixMouth() {
        ArrayList<ChartData> list = new ArrayList<>();
        int thisMouth = getMouth();
        int thisYear = getYear();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.MONTH, -5);
        int lastMouth = c.get(Calendar.MONTH) + 1;
        int lastYear = c.get(Calendar.YEAR);
        if (thisMouth - lastMouth < 0) {
            for (int i = lastMouth; i <= 12; i++) {
                ChartData da = new ChartData();
                da.date = Integer.parseInt(lastYear + "" + (i < 10 ? "0" + i : i) + "01");
                list.add(da);
            }
            for (int i = 1; i <=thisMouth+1; i++) {
                ChartData da = new ChartData();
                da.date = Integer.parseInt(thisYear + "" + (i < 10 ? "0" + i : i) + "01");
                list.add(da);
            }
        } else {
            for (int i = lastMouth; i <= thisMouth+1; i++) {
                ChartData da = new ChartData();
                da.date = Integer.parseInt(thisYear + "" + (i < 10 ? "0" + i : i) + "01");
                list.add(da);
            }
        }
        return list;
    }

    public static Calendar getPreviousDaysOfNow(int days) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.DAY_OF_MONTH, days);
        return c;
    }

    public static ArrayList<Integer> getListPreviousDaysOfNow(int days) {
        ArrayList<Integer> list = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.DAY_OF_MONTH, days);
        int lastDay = date2int(c);
        if (getDbSaveDay() - lastDay > 70) {
            int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            maxDay = lastDay - lastDay % 100 + maxDay;
            for (int i = lastDay; i <= maxDay; i++) {
                list.add(i);
            }
            for (int i = getCurrentMouthFirstDay(); i <= getDbSaveDay(); i++) {
                list.add(i);
            }
            return list;
        } else {
            for (int i = lastDay; i <= getDbSaveDay(); i++) {
                list.add(i);
            }
            return list;
        }
    }

    public static ArrayList<ChartData> getListDataPreviousDaysOfNow(int days) {
        ArrayList<ChartData> list = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.DAY_OF_MONTH, days);
        int lastDay = date2int(c);
        if (getDbSaveDay() - lastDay > 70) {
            int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            maxDay = lastDay - lastDay % 100 + maxDay;
            for (int i = lastDay; i <= maxDay; i++) {
                ChartData data = new ChartData();
                data.date = i;
                list.add(data);
            }
            for (int i = getCurrentMouthFirstDay(); i <= getDbSaveDay(); i++) {
                ChartData data = new ChartData();
                data.date = i;
                list.add(data);
            }
            return list;
        } else {
            for (int i = lastDay; i <= getDbSaveDay(); i++) {
                ChartData data = new ChartData();
                data.date = i;

                list.add(data);
            }
            return list;
        }
    }

    public static String date2String(Calendar c, String s) {
        int imouth = c.get(Calendar.MONTH) + 1;
        int iday = c.get(Calendar.DAY_OF_MONTH) + 1;
        String mouth = imouth < 10 ? "0" + imouth : imouth + "";
        String day = iday < 10 ? "0" + iday : iday + "";
        String date = s.replace("yyyy", c.get(Calendar.YEAR) + "")
                .replace("MM", mouth)
                .replace("dd", day);
        return date;
    }

    public static int date2int(Calendar c) {
        int imouth = c.get(Calendar.MONTH) + 1;
        int iday = c.get(Calendar.DAY_OF_MONTH) + 1;
        String mouth = imouth < 10 ? "0" + imouth : imouth + "";
        String day = iday < 10 ? "0" + iday : iday + "";
        int date = Integer.parseInt(c.get(Calendar.YEAR) + mouth + day);
        return date;
    }

}
