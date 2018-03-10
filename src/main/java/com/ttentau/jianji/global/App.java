package com.ttentau.jianji.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.ttentau.jianji.db.DbHelper;
import com.ttentau.jianji.util.Constant;
import com.ttentau.jianji.util.LogUtils;
import com.ttentau.jianji.util.SpUtils;
import com.ttentau.jianji.util.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


/**
 * 自定义application, 进行全局初始化
 *
 * @author Kevin
 * @date 2015-10-27
 */
public class App extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
        //init();
        initData();

    }

    private void init() {
        LogUtils.init(context);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                //在获取到了未捕获的异常后,处理的方法
                ex.printStackTrace();
//                Log.i(tag, "捕获到了一个程序的异常");
                LogUtils.e("捕获到了一个程序的异常"+ex.toString());

                //将捕获的异常存储到sd卡中
                String path = Constant.ERROR_LOG_PATH;
                File file = new File(path);
                try {
                    PrintWriter printWriter = new PrintWriter(file);
                    ex.printStackTrace(printWriter);
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //上传公司的服务器
                //结束应用
                System.exit(0);
            }
        });
    }

    private void initData() {
        new DbHelper(context).getWritableDatabase();
        String typesin = SpUtils.getType("typein", "");
        String typeout = SpUtils.getType("typeout", "");
        if (UIUtils.isEmpty(typesin)) {
            SpUtils.putType("typein",
                    "工资收入,生活费用,他人还款,借款，" +
                            "兼职收入,奖金收入,利息收入,其他收入");
        }
//        if (!typeout.startsWith("早晚午餐")){
//            SpUtils.putType("typeout","");
//        }
//        typeout = SpUtils.getType("typeout", "");
        if (UIUtils.isEmpty(typeout)) {
            SpUtils.putType("typeout",
                    "早晚午餐,零食酒水,出行交通,借给他人,休闲娱乐," +
                            "服饰用品,学习书刊,美容美发," +
                            "交流通讯,求医买药," +
                            "数码百货,其他支出");
        }
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
