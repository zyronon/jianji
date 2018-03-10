package com.ttentau.jianji.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ttentau.jianji.R;
import com.ttentau.jianji.util.DateUtils;
import com.ttentau.jianji.util.InputSoftUtils;
import com.ttentau.jianji.util.TypeDataUtils;
import com.ttentau.jianji.util.UIUtils;

import java.util.ArrayList;

/**
 * Created by ttentau on 2017/6/14.
 */

public class DialogAdapter extends PagerAdapter {

    private final Activity mActivity;
    private SpinnerAdapter mAdapter;
    private ArrayList<View> mList;

    public DialogAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mList.get(position);

        final TextView dialog_tv_minute = (TextView) view.findViewById(R.id.dialog_tv_minute);
        final TextView dialog_tv_day = (TextView) view.findViewById(R.id.dialog_tv_day);
        final EditText dialog_et_comment = (EditText) view.findViewById(R.id.dialog_et_comment);
        EditText dialog_et_money = (EditText) view.findViewById(R.id.dialog_et_money);
        dialog_et_money.setFocusableInTouchMode(true);
        dialog_et_money.requestFocus();
        InputSoftUtils.showSoftInput(mActivity,dialog_et_money);//显示键盘
        dialog_tv_day.setText(DateUtils.getCurrentDay());
        dialog_tv_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        String day = DateUtils.formatDayOrMinuteToDisplay(year, monthOfYear + 1, dayOfMonth, true);
                        dialog_tv_day.setText(day);
                    }
                }, DateUtils.getYear(), DateUtils.getMouth() - 1, DateUtils.getDay()).show();
            }
        });
        dialog_tv_minute.setText(DateUtils.getCurrentMinute());
        dialog_tv_minute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(mActivity, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String minute1 = DateUtils.formatDayOrMinuteToDisplay(hourOfDay, minute, 13, false);
                        dialog_tv_minute.setText(minute1);
                    }
                }, DateUtils.getHour(), DateUtils.getMinute(), true).show();
            }
        });
        Spinner dialog_vp_sp_type = (Spinner) view.findViewById(R.id.dialog_vp_sp_type);
        Spinner dialog_vp_sp_from = (Spinner) view.findViewById(R.id.dialog_vp_sp_from);
        dialog_vp_sp_from.setAdapter(new SpinnerAdapter(UIUtils.getContext(), new String[]{"现金", "微信", "支付宝"}));
        if (position == 0)
            mAdapter = new SpinnerAdapter(UIUtils.getContext(), TypeDataUtils.getTypeOut());
        else
            mAdapter = new SpinnerAdapter(UIUtils.getContext(), TypeDataUtils.getTypeIn());
        dialog_vp_sp_type.setAdapter(mAdapter);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setDatas(ArrayList<View> list) {
        mList = list;
    }
}
