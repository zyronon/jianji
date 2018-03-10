package com.ttentau.jianji.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.ttentau.jianji.bean.ChartData;
import com.ttentau.jianji.bean.UserNote;
import com.ttentau.jianji.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by ttent on 2017/2/28.
 */

public class NoteDb {
    private final DbHelper mDb;
    private static NoteDb friendDb = null;

    private NoteDb(Context context) {
        mDb = new DbHelper(context);
    }

    public static NoteDb getInstence(Context context) {
        if (friendDb == null) {
            friendDb = new NoteDb(context);
        }
        return friendDb;
    }

    public ArrayList<UserNote> queryAllData(int dbtype) {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
//		Cursor queryAllData = wdb.queryAllData("userdata", null, "dbtype", new String[dbtype], null, null, null);
        Cursor query = wdb.rawQuery("select * from userdata where dbtype = " + dbtype, null);
        ArrayList<UserNote> notes = new ArrayList<>();
        while (query.moveToNext()) {
            UserNote note = new UserNote();
            note.money = query.getDouble(1);
            note.day = query.getInt(2);
            note.minute = query.getInt(3);
            note.type = query.getString(4);
            note.comment = query.getString(5);
            note.userid = query.getString(6);
            note.itemid = query.getString(7);
            note.dbtype = query.getInt(8);
            note.from = query.getInt(9);
            notes.add(note);
        }
        query.close();
        wdb.close();
        return notes;
    }

    public ArrayList<UserNote> queryAllData() {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor query = wdb.rawQuery("select * from userdata order by day desc ", null);
        ArrayList<UserNote> notes = new ArrayList<>();
        while (query.moveToNext()) {
            UserNote note = new UserNote();
            note.money = query.getDouble(1);
            note.day = query.getInt(2);
            note.minute = query.getInt(3);
            note.type = query.getString(4);
            note.comment = query.getString(5);
            note.userid = query.getString(6);
            note.itemid = query.getString(7);
            note.dbtype = query.getInt(8);
            note.from = query.getInt(9);
            notes.add(note);
        }
        query.close();
        wdb.close();
        return notes;
    }

    /*
    * 查询自定义日期的所有数据
    * */
    public ArrayList<UserNote> queryCustomDateData(int today, int days) {
        SQLiteDatabase wdb = mDb.getReadableDatabase();
        Calendar c = DateUtils.getPreviousDaysOfNow(days);
        int lastDay = DateUtils.date2int(c);
        Cursor query = wdb.rawQuery("select * from userdata where day <= " + today + " and day > "+lastDay + " and money !=0 order by day desc", null);
        ArrayList<UserNote> notes = new ArrayList<>();
        while (query.moveToNext()) {
            UserNote note = new UserNote();
            note.money = query.getDouble(1);
            note.day = query.getInt(2);
            note.minute = query.getInt(3);
            note.type = query.getString(4);
            note.comment = query.getString(5);
            note.userid = query.getString(6);
            note.itemid = query.getString(7);
            note.dbtype = query.getInt(8);
            note.from = query.getInt(9);
            notes.add(note);
        }
        query.close();
        wdb.close();
        return notes;
    }

    public ArrayList<ChartData> queryCustomDateMoney(ArrayList<ChartData> list, int dbtype) {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor query = null;
        for (int i = 0; i < list.size() - 1; i++) {
            query = wdb.rawQuery("select sum(money) as money, " + list.get(i).date + " from userdata where dbtype = " + dbtype + " and day <= " + list.get(i + 1).date + " and day > " + list.get(i).date + " group by day order by day asc", null);
            while (query.moveToNext()) {
                for (ChartData c1 : list) {
                    if (c1.date == query.getInt(1)) {
                        c1.money = query.getInt(0);
                        c1.dbtype = dbtype;
                    }
                }
            }
        }
        return list;
    }

    public ArrayList<ChartData> queryCustomDateAllMoney(ArrayList<ChartData> list) {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor query = null;
        for (int i = 0; i < list.size() - 1; i++) {
            query = wdb.rawQuery("select sum(money) as money, " + list.get(i).date + " from userdata where day <= " + list.get(i + 1).date + " and day > " + list.get(i).date + " group by day order by day asc", null);
            while (query.moveToNext()) {
                for (ChartData c1 : list) {
                    if (c1.date == query.getInt(1)) {
                        c1.money = query.getInt(0);
                    }
                }
            }
        }
        return list;
    }

    public int queryCustomDateAllMoneyToPie(int today, int days,int dbtype) {
        int money=0;
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Calendar c = DateUtils.getPreviousDaysOfNow(days);
        int lastDay = DateUtils.date2int(c);
        Cursor query = wdb.rawQuery("select sum(money) as money from userdata where dbtype = " + dbtype + " and day <= " + today + " and day > " + lastDay, null);
        while (query.moveToNext()) {
            money = query.getInt(0);
        }
        query.close();
        wdb.close();
        return money;
    }
    public ArrayList<ChartData> queryCustomDateTypeMoneyToPie(int today, int days,int dbtype) {
        ArrayList<ChartData> list = new ArrayList<>();
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Calendar c = DateUtils.getPreviousDaysOfNow(days);
        int lastDay = DateUtils.date2int(c);
        Cursor query = wdb.rawQuery("select sum(money) as money,type from userdata where dbtype = " + dbtype + " and day <= " + today + " and day > " + lastDay+" group by type ", null);
        while (query.moveToNext()) {
            ChartData data = new ChartData();
            data.money = query.getInt(0);
            data.type=query.getString(1);
            list.add(data);
        }
        query.close();
        wdb.close();
        return list;
    }

    /*
    * 查询自定义日期的每一天的所有金钱
    * */
    public ArrayList<ChartData> queryCustomDateAllMoney(int today, int days) {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        ArrayList<ChartData> list = DateUtils.getListDataPreviousDaysOfNow(days);
        Calendar c = DateUtils.getPreviousDaysOfNow(days);
        int lastDay = DateUtils.date2int(c);
        Cursor query = wdb.rawQuery("select sum(money) as money,day from userdata where day <= " + today + " and day > " + lastDay + " group by day order by day asc", null);
        while (query.moveToNext()) {
            for (ChartData c1 : list) {
                if (c1.date == query.getInt(1)) {
                    c1.money = query.getInt(0);
                }
            }
        }
        query.close();
        wdb.close();
        return list;
    }

    /*
    * 查询自定义日期的每一天的金钱
    * */
    public ArrayList<ChartData> queryCustomDateMoney(int today, int days, int dbtype) {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        ArrayList<ChartData> list = DateUtils.getListDataPreviousDaysOfNow(days);
        Calendar c = DateUtils.getPreviousDaysOfNow(days);
        int lastDay = DateUtils.date2int(c);
        Cursor query = wdb.rawQuery("select sum(money) as money,day from userdata where dbtype = " + dbtype + " and day <= " + today + " and day > " + lastDay + " group by day order by day asc", null);
        while (query.moveToNext()) {
            for (ChartData c1 : list) {
                if (c1.date == query.getInt(1)) {
                    c1.money = query.getInt(0);
                    c1.dbtype = dbtype;
                }
            }
        }
        query.close();
        wdb.close();
        return list;
    }

    public ArrayList<UserNote> queryTodayData(int today) {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
//		Cursor queryAllData = wdb.query("userdata", null, "dbtype", new String[dbtype], null, null, null);
        Cursor query = wdb.rawQuery("select * from userdata where day = " + today, null);
        ArrayList<UserNote> notes = new ArrayList<>();
        while (query.moveToNext()) {
            UserNote note = new UserNote();
            note.money = query.getDouble(1);
            note.day = query.getInt(2);
            note.minute = query.getInt(3);
            note.type = query.getString(4);
            note.comment = query.getString(5);
            note.userid = query.getString(6);
            note.itemid = query.getString(7);
            note.dbtype = query.getInt(8);
            note.from = query.getInt(9);
            notes.add(note);
        }
        query.close();
        wdb.close();
        return notes;
    }

    public double queryAllMoney(int dbtype) {
        double money = 0;
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor query = wdb.rawQuery("select sum(money) as money from userdata where dbtype = " + dbtype, null);
        while (query.moveToNext()) {
            money = query.getDouble(0);
        }
        wdb.close();
        query.close();
        return money;
    }

    public double queryTodayMoney(int dbtype) {
        int today = DateUtils.getDbSaveDay();
        double money = 0;
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor query = wdb.rawQuery("select sum(money) as money from userdata where dbtype = " + dbtype + " and day = " + today, null);
        while (query.moveToNext()) {
            money = query.getDouble(0);
        }
        wdb.close();
        query.close();
        return money;
    }

    public double queryThisMouthMoney(int dbtype) {
        int[] ints = DateUtils.getThisMonthFirstAndEnd();
        double money = 0;
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor query = wdb.rawQuery("select sum(money) as money from userdata where dbtype = " + dbtype + " and day <= " + ints[1] + " and day >= " + ints[0], null);
        while (query.moveToNext()) {
            money = query.getDouble(0);
        }
        wdb.close();
        query.close();
        return money;
    }

    public long insert(UserNote data) {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("money", data.money);
        values.put("day", data.day);
        values.put("minute", data.minute);
        values.put("type", data.type);
        values.put("comment", data.comment);
        values.put("itemid", data.itemid);
        values.put("userid", data.userid);
        values.put("dbtype", data.dbtype);
        values.put("from1", data.from);
        long result = wdb.insert("userdata", null, values);
        wdb.close();
        return result;
    }
    public void dalete(String rowId) {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
//		int result = wdb.delete("userdata", "itemid" , new String[Integer.parseInt(rowId)]);这种方法不知怎么不行
        wdb.execSQL("delete from userdata where itemid" + "=" + rowId);
        wdb.close();
        //return result>0;
    }

    public boolean daleteall() {
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        int result = wdb.delete("userdata", null, null);
        wdb.close();
        return result > 0;
    }
}
