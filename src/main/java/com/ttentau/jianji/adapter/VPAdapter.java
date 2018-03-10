package com.ttentau.jianji.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ttentau.jianji.base.BasePager;

import java.util.ArrayList;

/**
 * Created by ttentau on 2017/6/27.
 */

public class VPAdapter extends PagerAdapter{
    private ArrayList<BasePager> mList;

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager pager = mList.get(position);
        container.addView(pager.mRootView);
        return pager.mRootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setDatas(ArrayList<BasePager> list) {
        mList = list;
    }
}
