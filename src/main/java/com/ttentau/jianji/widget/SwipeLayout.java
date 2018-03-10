package com.ttentau.jianji.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ttentau on 2017/7/1.
 */

public class SwipeLayout extends FrameLayout {

    private String TAG = getClass().getName();
    private View mContent;
    private View mDelete;
    private int mDeletewidth;
    private int mHeight;
    private int mContentWidth;
    private ViewDragHelper mHelper;
    private float mDownx;
    private float mDowny;

    enum SwipeState {
        Open, Close;
    }

    private SwipeState currentState = SwipeState.Close;

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHelper = ViewDragHelper.create(this, mCallBack);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = mDelete.getMeasuredHeight();
        mDeletewidth = mDelete.getMeasuredWidth();
        mContentWidth = mContent.getMeasuredWidth();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContent = getChildAt(0);
        mDelete = getChildAt(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!SwipeLayoutManager.getInstance().isShouldSweipe(this)) {
            requestDisallowInterceptTouchEvent(true);
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownx = event.getX();
                mDowny = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float movex = event.getX();
                float movey = event.getY();
                float x = movex - mDownx;
                float y = movey - mDowny;
                if (Math.abs(x) > Math.abs(y)) {
                    requestDisallowInterceptTouchEvent(true);
                }
                mDownx = movex;
                mDowny = movey;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        requestDisallowInterceptTouchEvent(true);
        boolean result = mHelper.shouldInterceptTouchEvent(ev);
        if (!SwipeLayoutManager.getInstance().isShouldSweipe(this)) {
            SwipeLayoutManager.getInstance().close();
            result = true;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mContent.layout(0, 0, mContentWidth, mHeight);
        mDelete.layout(-mDeletewidth, 0, 0, mHeight);
    }

    ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mDeletewidth;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mContent) {
                if (left > mDeletewidth) left = mDeletewidth;
                if (left < 0) left = 0;
            }
            if (child == mDelete) {
                if (left < -mDeletewidth) left = -mDeletewidth;
                if (left > 0) left = 0;
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == mContent) {
                mDelete.layout(mDelete.getLeft() +dx, 0, mDelete.getRight()+ dx, mHeight);
            }
            if (changedView == mDelete) {
                mContent.layout(mContent.getLeft() + dx, 0, mContent.getRight() + dx, mHeight);
            }
            if (mContent.getLeft() == 0 && currentState != SwipeState.Close) {
                currentState = SwipeState.Close;
                SwipeLayoutManager.getInstance().clearSwipeLayout();
            }
            if (mContent.getLeft() == mDeletewidth && currentState != SwipeState.Open) {
                currentState = SwipeState.Open;
                SwipeLayoutManager.getInstance().setSwipeLayout(SwipeLayout.this);
            }

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mContent.getLeft() > mDeletewidth / 2) {
                open();
            } else {
                close();
            }
        }
    };

    public void open() {
        mHelper.smoothSlideViewTo(mContent, mDeletewidth, 0);
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
    }

    public void close() {
        mHelper.smoothSlideViewTo(mContent, 0, 0);
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
    }

    @Override
    public void computeScroll() {
        if (mHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
        }
    }
}
