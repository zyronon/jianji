package com.ttentau.jianji.db;

import android.content.Context;
import android.os.AsyncTask;

import com.ttentau.jianji.util.Constant;
import com.ttentau.jianji.util.LogUtils;
import com.ttentau.jianji.util.UIUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by ttentau on 2017/6/28.
 */

public class DbManager extends AsyncTask<String,Void,Integer>{
    public static String dbPath=Constant.DB_PATH;
    public static File backupDir= new File(Constant.BACKUP_DB_PATH);
    private final Context mContext;

    public DbManager(Context context){
        mContext = context;
    }

    private int fileCopy(File dbFile, File backup) throws IOException {
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
        return 0;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        File srcdbFile = mContext.getDatabasePath(dbPath);
        if (!backupDir.exists()) {
            backupDir.mkdirs();//创建备份文件
            LogUtils.e(backupDir+"");
        }
        File backupDb = new File(backupDir, srcdbFile.getName());
        try {
            backupDb.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (strings[0].equals("ic_backup")){
            try {
                return fileCopy(srcdbFile, backupDb);
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        if (strings[0].equals("restore")){
            try {
               return fileCopy(backupDb,srcdbFile);
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==-1){
            UIUtils.Toast("失败");
        }else {
            UIUtils.Toast("成功");
        }
    }
}
