package com.ttentau.jianji.bean;

/**
 * Created by ttentau on 2017/6/14.
 */

public class UserNote {
    public double money;
    public int day;
    public int minute;
    public String type;
    public String comment="";
    public String userid;
    public String itemid;
    public int dbtype=0;
    public int from=0;

    @Override
    public String toString() {
        return "UserNote{" +
                "money=" + money +
                ", day=" + day +
                ", minute=" + minute +
                ", ic_type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                ", userid='" + userid + '\'' +
                ", itemid='" + itemid + '\'' +
                ", dbtype=" + dbtype +
                ", from=" + from +
                '}';
    }
}
