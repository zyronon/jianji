package com.ttentau.jianji.base.vp_view;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ttentau.jianji.R;
import com.ttentau.jianji.adapter.SpinnerAdapter;
import com.ttentau.jianji.base.BasePager;
import com.ttentau.jianji.db.NoteDb;
import com.ttentau.jianji.util.ColumnChartUtils;
import com.ttentau.jianji.util.DateUtils;
import com.ttentau.jianji.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by ttentau on 2017/6/23.
 */

public class ColumnPager extends BasePager {

    @BindView(R.id.column_chart)
    ColumnChartView column_chart;
    @BindView(R.id.gb_out)
    RadioButton mCb_out;
    @BindView(R.id.gb_in)
    RadioButton mCb_in;
    @BindView(R.id.gb_all)
    RadioButton mCb_all;
    @BindView(R.id.rg_all)
    RadioGroup mRg_all;
    @BindView(R.id.sp_select)
    Spinner mSp_select;

    private ColumnChartData mLineData;
    private ArrayList<Column> mOutLine;
    private ArrayList<Column> mInLine;
    private ArrayList<Column> mAllLine;
    private NoteDb mDb;

    public ColumnPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.main_vp2_column);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initData() {
        mRg_all.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.gb_out:
                        mLineData.setColumns(mOutLine);
                        break;
                    case R.id.gb_in:
                        mLineData.setColumns(mInLine);
                        break;
                    case R.id.gb_all:
                        mLineData.setColumns(mAllLine);
                        break;
                }
                column_chart.setColumnChartData(mLineData);
            }
        });
        mDb = NoteDb.getInstence(mActivity);
        mSp_select.setAdapter(new SpinnerAdapter(UIUtils.getContext(), new String[]{
                "最近7天",
                "最近半月",
                "最近一月",
        }));
        /*
        *  "最近三月",
                "最近六月",
                "查询本月",
                "查询本年"*/
        mSp_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        initLine(-7);
                        break;
                    case 1:
                        initLine(-15);
                        break;
                    case 2:
                        initLine(-30);
                        break;
//                    case 3:
//                        initLineBig(DateUtils.getListDataRecentlyThreeMouth(),false,true);
//                        break;
//                    case 4:
//                        initLineBig(DateUtils.getListDataRecentlySixMouth(),false,false);
//                        break;
//                    case 5:
//                        initLineBig(DateUtils.getListDataCurrentMouth(),false,true);
//                        break;
//                    case 6:
//                        initLineBig(DateUtils.getListDataThisYear(),false,true);
//                        break;
                }
                mCb_all.setChecked(false);
                mCb_in.setChecked(false);
                mCb_out.setChecked(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        initLine(-7);
    }
//    /*
//    * 这是查询整月，整年。。。
//    * */
//    private void initLineBig(ArrayList list, boolean isDeleteLastData, boolean isHasDay) {
//        mLines = new ArrayList<Line>();
//        List<PointValue> outP = LineChartUtils.getP(mDb.queryCustomDateMoney(list, 0),isDeleteLastData);
//        List<PointValue> inP = LineChartUtils.getP(mDb.queryCustomDateMoney(list, 1),isDeleteLastData);
//        List<PointValue> allP = LineChartUtils.getP(mDb.queryCustomDateAllMoney(list),isDeleteLastData);
//        List<AxisValue> allX = LineChartUtils.getX(mDb.queryCustomDateAllMoney(list),isDeleteLastData,isHasDay);
//        mInLine = LineChartUtils.getLine(inP, Color.GREEN);
//        mOutLine = LineChartUtils.getLine(outP, Color.RED);
//        mAllLine = LineChartUtils.getLine(allP, Color.BLUE);
//        Axis axisX = LineChartUtils.getAxisX(allX);
//        Axis axisY = LineChartUtils.getAxisY();
//        mLines.floatbutton_ic_add(mOutLine);
//        mLineData = new LineChartData();
//        mLineData.setLines(mLines);
//        mLineData.setAxisXBottom(axisX);
//        mLineData.setAxisYLeft(axisY);
//        column_chart.setLineChartData(mLineData);
//    }


    private void initLine(int days) {
        List<List<SubcolumnValue>> outP = ColumnChartUtils.getP(mDb.queryCustomDateMoney(DateUtils.getDbSaveDay(), days, 0), Color.RED, false);
        List<List<SubcolumnValue>> inP = ColumnChartUtils.getP(mDb.queryCustomDateMoney(DateUtils.getDbSaveDay(), days, 1), Color.GREEN, false);
        List<List<SubcolumnValue>> allP = ColumnChartUtils.getP(mDb.queryCustomDateAllMoney(DateUtils.getDbSaveDay(), days), Color.BLUE, false);
        List<AxisValue> allX = ColumnChartUtils.getX(mDb.queryCustomDateAllMoney(DateUtils.getDbSaveDay(), days), false, true);
        mInLine = ColumnChartUtils.getColumn(inP);
        mOutLine = ColumnChartUtils.getColumn(outP);
        mAllLine = ColumnChartUtils.getColumn(allP);
        Axis axisX = ColumnChartUtils.getAxisX(allX);
        Axis axisY = ColumnChartUtils.getAxisY();
        mLineData = new ColumnChartData();
        mLineData.setColumns(mOutLine);
        mLineData.setAxisXBottom(axisX);
        mLineData.setAxisYLeft(axisY);
        column_chart.setColumnChartData(mLineData);
    }

}
