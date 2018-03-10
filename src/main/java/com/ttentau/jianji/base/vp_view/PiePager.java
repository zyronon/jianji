package com.ttentau.jianji.base.vp_view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


import com.ttentau.jianji.R;
import com.ttentau.jianji.adapter.SpinnerAdapter;
import com.ttentau.jianji.base.BasePager;
import com.ttentau.jianji.bean.ChartData;
import com.ttentau.jianji.db.NoteDb;
import com.ttentau.jianji.util.DateUtils;
import com.ttentau.jianji.util.PieChartUtils;
import com.ttentau.jianji.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by ttentau on 2017/6/23.
 */

public class PiePager extends BasePager {

    @BindView(R.id.pie_chart)
    PieChartView mPieChart;
    @BindView(R.id.sp_select)
    Spinner mSp_select;
    @BindView(R.id.rg_all)
    RadioGroup mRg_all;
    @BindView(R.id.gb_out)
    RadioButton mGb_out;
    @BindView(R.id.gb_in)
    RadioButton mGb_in;


    private NoteDb mDb;
    private PieChartData mData;
    private List<SliceValue> mOutValues;
    private List<SliceValue> minValues;

    public PiePager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View inflate = UIUtils.inflate(R.layout.main_vp2_pie);
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
                        mData.setValues(mOutValues);
                        break;
                    case R.id.gb_in:
                        mData.setValues(minValues);
                        break;
                }
                mPieChart.setPieChartData(mData);
            }
        });
        mDb = NoteDb.getInstence(mActivity);
        mSp_select.setAdapter(new SpinnerAdapter(UIUtils.getContext(), new String[]{
                "最近7天",
                "最近半月",
                "最近一月",
        }));
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
                }
                mGb_in.setChecked(false);
                mGb_out.setChecked(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        initLine(-7);
    }

    private void initLine(int days) {
        int outMoney = mDb.queryCustomDateAllMoneyToPie(DateUtils.getDbSaveDay(), days,0);
        ArrayList<ChartData> outList = mDb.queryCustomDateTypeMoneyToPie(DateUtils.getDbSaveDay(), days,0);
        int inMoney = mDb.queryCustomDateAllMoneyToPie(DateUtils.getDbSaveDay(), days,1);
        ArrayList<ChartData> inList = mDb.queryCustomDateTypeMoneyToPie(DateUtils.getDbSaveDay(), days,1);
        mOutValues = PieChartUtils.getValues(outMoney, outList);
        minValues = PieChartUtils.getValues(inMoney, inList);
        mData = new PieChartData();
        mData.setValues(mOutValues);
        mData.setHasLabels(true);
        mData.setHasLabelsOnlyForSelected(false);
        mData.setHasLabelsOutside(false);
        mData.setHasCenterCircle(true);
        mPieChart.setPieChartData(mData);
    }
}
