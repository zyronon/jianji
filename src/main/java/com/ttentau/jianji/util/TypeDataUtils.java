package com.ttentau.jianji.util;

import java.util.Arrays;

/**
 * Created by ttentau on 2017/6/15.
 * 这是查询支出和收入的数据类型
 */

public class TypeDataUtils {
    public static String[] getTypeIn(){
        String typein = SpUtils.getType("typein", "");
        LogUtils.e(typein);
        String[] split = typein.split(",");
        return split;
    }
    public static String[] getTypeOut(){
        String typeout = SpUtils.getType("typeout", "");
        String[] split = typeout.split(",");
        return split;
    }
    public static boolean isTypeOut(String targetValue){
        String[] typeOut = getTypeOut();
        return Arrays.asList(typeOut).contains(targetValue);
    }
}
