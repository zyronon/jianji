package com.ttentau.jianji.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;


import com.ttentau.jianji.R;
import com.ttentau.jianji.adapter.VPAdapter;
import com.ttentau.jianji.base.BaseFragment;
import com.ttentau.jianji.base.BasePager;
import com.ttentau.jianji.base.vp_view.ColumnPager;
import com.ttentau.jianji.base.vp_view.LinePager;
import com.ttentau.jianji.base.vp_view.PiePager;
import com.ttentau.jianji.util.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ttentau on 2017/6/13.
 */

public class ViewFragment extends BaseFragment {

    @BindView(R.id.main_vp2_vp)
    ViewPager mMain_vp2_vp;
    private ArrayList<BasePager> mViews;
    private VPAdapter mAdapter;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.main_vp2);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mViews = new ArrayList<>();
        mViews.add(new LinePager(getActivity()));
        mViews.add(new ColumnPager(getActivity()));
        mViews.add(new PiePager(getActivity()));
//        mViews.floatbutton_ic_add(new LineColumnPager());
    }

    @Override
    public void setData() {
        mAdapter = new VPAdapter();
        mAdapter.setDatas(mViews);
        mMain_vp2_vp.setAdapter(mAdapter);
        mViews.get(0).initData();
        mMain_vp2_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mViews.get(position).initData();
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    @OnClick({R.id.iv_line,R.id.iv_column,R.id.iv_pie})
    public void onViewClicked(View v){
        switch (v.getId()){
            case R.id.iv_line:
                mMain_vp2_vp.setCurrentItem(0,false);
                break;
            case R.id.iv_column:
                mMain_vp2_vp.setCurrentItem(1,false);
                break;
            case R.id.iv_pie:
                mMain_vp2_vp.setCurrentItem(2,false);
                break;
//            case R.id.iv_line_column:
//                mMain_vp2_vp.setCurrentItem(3,false);
//                break;
        }
    }
}
