package com.ttentau.jianji.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ttentau on 2017/7/3.
 */

public class NotInterceptVp extends ViewPager {
    private float mDownx;
    private float mDowny;

    public NotInterceptVp(Context context) {
        super(context);
    }

    public NotInterceptVp(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownx = ev.getX();
                mDowny = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float movex = ev.getX();
                float movey = ev.getY();
                float x = movex - mDownx;
                float y = movey - mDowny;
                if (Math.abs(x) > Math.abs(y)&&getCurrentItem()==0&&x>0) {
//                    requestDisallowInterceptTouchEvent(true);
                    return false;
                }
//                mDownx = movex;
//                mDowny = movey;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
