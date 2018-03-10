package com.ttentau.jianji.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ttentau.jianji.R;
import com.ttentau.jianji.activity.MainActivity;

/**
 * Created by ttentau on 2017/6/27.
 */

public abstract class BasePager {

    public Activity mActivity;
    public View mRootView;// 当前页面的布局对象

    public BasePager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    // 初始化布局
    public abstract View initView();
    // 初始化数据
    public void initData() {
    }
}