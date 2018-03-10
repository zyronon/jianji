package com.ttentau.jianji.widget;

/**
 * Created by ttentau on 2017/7/2.
 */

public class SwipeLayoutManager {

    private SwipeLayoutManager(){
    }
    private static SwipeLayoutManager sManager=new SwipeLayoutManager();
    public static SwipeLayoutManager getInstance(){
        return sManager;
    }
    private SwipeLayout mLayout;
    public void setSwipeLayout(SwipeLayout mLayout){
        this.mLayout=mLayout;
    }
    public void clearSwipeLayout(){
        mLayout=null;
    }
    public boolean isShouldSweipe(SwipeLayout mLayout){
        if (this.mLayout==null){
            return true;
        }else {
            return this.mLayout==mLayout;
        }
    }
    public void close(){
        if (mLayout!=null){
            mLayout.close();
        }
    }
    public void open(){
        if (mLayout!=null){
            mLayout.open();
        }
    }
}
