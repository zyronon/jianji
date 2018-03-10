package com.ttentau.jianji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.ttentau.jianji.R;
import com.ttentau.jianji.util.ActivityCollector;
import com.ttentau.jianji.util.SpUtils;
import com.ttentau.jianji.util.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ttentau on 2017/6/27.
 */

public class LockPatternActivity extends AppCompatActivity {
    @BindView(R.id.l)
    PatternLockView mLock;
    @BindView(R.id.tv_hint)
    TextView mTv_hint;
    @BindView(R.id.btn_cancel_pwd)
    Button mBtn_cancel_pwd;
    private String mFristPwd;
    private String mDefaultPwd;
    private boolean mIsReSet=false;
    private int mFrom;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            if (msg.what==0){
//                finish();
//            }
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pattern);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        Intent intent = getIntent();
        mFrom = intent.getIntExtra("from",-1);
        mDefaultPwd = SpUtils.getLock("patternlock", "");
        if (mFrom==0)mBtn_cancel_pwd.setVisibility(View.GONE);
        mLock.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                Log.d(getClass().getName(), "Pattern drawing started");
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
                Log.d(getClass().getName(), "Pattern progress: " +
                        PatternLockUtils.patternToString(mLock, progressPattern));
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                Log.d(getClass().getName(), "Pattern complete: " +
                        PatternLockUtils.patternToString(mLock, pattern));
                if (UIUtils.isEmpty(mDefaultPwd)){
                    if (UIUtils.isEmpty(mFristPwd)){
                        mFristPwd = PatternLockUtils.patternToString(mLock, pattern);
                        mTv_hint.setText("请再输入一次密码");
                    }else {
                        String tempPwd = PatternLockUtils.patternToString(mLock, pattern);
                        if (tempPwd.equals(mFristPwd)){
                            mTv_hint.setText("密码设置成功,即将跳转...");
                            SpUtils.setLock("patternlock",mFristPwd);
                            Message msg = new Message();
                            msg.what=0;
                            mHandler.sendEmptyMessageDelayed(0,2000);
                        }else {
                            mTv_hint.setText("与上次绘制的密码不一样");
                        }
                    }
                }else {
                    if (mFrom ==0){
                        String tempPwd = PatternLockUtils.patternToString(mLock, pattern);
                        if (mDefaultPwd.equals(tempPwd)){
//                            startActivity(new Intent(LockPatternActivity.this,MainActivity.class));
                            finish();
                        }else {
                            mTv_hint.setText("密码错误");
                        }
                    }else if (mFrom ==1){
                        String tempPwd = PatternLockUtils.patternToString(mLock, pattern);
                        if (mIsReSet){
                            if (UIUtils.isEmpty(mFristPwd)){
                                mFristPwd = PatternLockUtils.patternToString(mLock, pattern);
                                mTv_hint.setText("请再输入一次密码");
                            }else {
                                String tempPwd1 = PatternLockUtils.patternToString(mLock, pattern);
                                if (tempPwd1.equals(mFristPwd)){
                                    SpUtils.setLock("patternlock",mFristPwd);
                                    mTv_hint.setText("密码设置成功,即将跳转...");
                                    Message msg = new Message();
                                    msg.what=0;
                                    mHandler.sendMessageDelayed(msg,2000);
                                }else {
                                    mTv_hint.setText("与上次绘制的密码不一样");
                                }
                            }
                        }else {
                            if (mDefaultPwd.equals(tempPwd)){
                                mTv_hint.setText("请设置密码");
                                mIsReSet = true;
                            }else {
                                mTv_hint.setText("密码错误");
                            }
                        }
                    }
                }
                mLock.clearPattern();
            }
            @Override
            public void onCleared() {
                Log.d(getClass().getName(), "Pattern has been cleared");
            }
        });
    }
    @OnClick(R.id.btn_cancel_pwd)
    public void OnClick(View v){
        SpUtils.setLock("patternlock","");
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mFrom==1){
            finish();
        }else if (mFrom==0){
            ActivityCollector.finishAll();
        }
    }
}
