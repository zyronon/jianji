package com.ttentau.jianji.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by ttentau on 2017/6/29.
 */

public class Constant {
    public static final String PRODUCT_PATH= Environment.getExternalStorageDirectory().getAbsoluteFile()+ File.separator+"jianji";
    public static final String ERROR_LOG_PATH=PRODUCT_PATH+"/error.log";
    public static final String DB_PATH="/data/data/com.ttentau.jianji/databases/Note.db";
    public static final String BACKUP_DB_PATH=PRODUCT_PATH+"/jianjibackup";

}
