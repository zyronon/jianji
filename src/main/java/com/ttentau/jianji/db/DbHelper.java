package com.ttentau.jianji.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.ttentau.jianji.util.UIUtils;


/**
 * Created by ttent on 2017/2/10.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String USERDATA="CREATE TABLE `userdata` (\n" +
            "  `id` integer primary key ,\n" +
            "  `money`  real,  \n" +
            "  'day' integer,\n" +
            "  'minute' integer,\n" +
            "  'type' varchar(10), \n" +
            "  `comment` text,\n" +
            "  `userid` varchar(20),\n" +
            "  `itemid` varchar(20)," +
            "  `dbtype` integer" +
            ")";
    private final Context mContext;

    public DbHelper(Context context) {
        super(context,"Note.db", null, 3);
        mContext = context;
    }
    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERDATA);
        db.execSQL("update userdata set money=-(select money from userdata) where dbtype=0;");
        db.execSQL("alter table userdata add from1 int default 0");
        Toast.makeText(mContext,"数据库创建成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion==2){
            db.execSQL("update userdata set money=-(select money from userdata) where dbtype=0;");
        }
        if (newVersion==3){
            db.execSQL("update userdata set money=-(select money from userdata) where dbtype=0;");
            db.execSQL("alter table userdata add from1 int default 0");
        }
        UIUtils.Toast("数据库修改成功");
    }
}
