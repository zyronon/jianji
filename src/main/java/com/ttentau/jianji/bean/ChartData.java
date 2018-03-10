package com.ttentau.jianji.bean;

/**
 * Created by ttentau on 2017/6/23.
 */

public class ChartData {
    public int date;
    public int money=0;
    public int dbtype=-1;
    public String type="";

    @Override
    public String toString() {
        return "ChartData{" +
                "date=" + date +
                ", money=" + money +
                ", dbtype=" + dbtype +
                ", ic_type='" + type + '\'' +
                '}';
    }
}
