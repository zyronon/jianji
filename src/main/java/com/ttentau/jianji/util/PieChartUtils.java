package com.ttentau.jianji.util;

import android.graphics.Color;

import com.ttentau.jianji.bean.ChartData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.SliceValue;

/**
 * Created by ttentau on 2017/6/23.
 */

public class PieChartUtils {
    private static int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.YELLOW};
    public static List<SliceValue> getValues(int outMoney, ArrayList<ChartData> outList){
        List<SliceValue> values = new ArrayList<>();
        SliceValue sliceValue = null;
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        for (int i = 0; i < outList.size(); ++i) {
            int money = outList.get(i).money;
            money=money<0?0-money:money;
            outMoney=outMoney<0?0-outMoney:outMoney;
            String percentage = numberFormat.format((Float.parseFloat(money+"")/ Float.parseFloat(outMoney+""))* 100);// percentage 百分比
            sliceValue = new SliceValue();
            sliceValue.setValue(Float.parseFloat(percentage));
            int rand = new Random().nextInt(colors.length);
            sliceValue.setColor(colors[rand]);
            sliceValue.setLabel(outList.get(i).type+""+ money+ "￥(" + percentage + "%)");
            values.add(sliceValue);
        }
        return values;
    }
}
