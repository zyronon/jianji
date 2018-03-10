package com.ttentau.jianji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttentau.jianji.R;
import com.ttentau.jianji.adapter.MainFragmentAdapter;
import com.ttentau.jianji.base.BaseFragment;
import com.ttentau.jianji.db.DbManager;
import com.ttentau.jianji.fragment.NoteFragment;
import com.ttentau.jianji.fragment.ViewFragment;
import com.ttentau.jianji.util.ActivityCollector;
import com.ttentau.jianji.util.DateUtils;
import com.ttentau.jianji.util.SpUtils;
import com.ttentau.jianji.util.UIUtils;
import com.ttentau.jianji.widget.NotInterceptVp;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<BaseFragment> mViews;
    @BindView(R.id.main_vp)
    NotInterceptVp mMain_vp;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.root)
    FrameLayout mRoot;
    @BindView(R.id.main_iv_open)
    ImageView mMain_iv_open;
    @BindView(R.id.main_tv_date)
    TextView mMain_tv_date;
    private View mInflate;
    private GuillotineAnimation mGuill;
    private boolean isOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        String lockCode = SpUtils.getLock("patternlock", "");
        if (!UIUtils.isEmpty(lockCode)) {
            Intent intent = new Intent(this, LockPatternActivity.class);
            intent.putExtra("from",0);
            startActivity(intent);
//            finish();
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
        setData();
    }

    private void initData() {
        mViews = new ArrayList<>();
        mViews.add(new NoteFragment());
        mViews.add(new ViewFragment());
    }

    private void setData() {
        mMain_tv_date.setText(DateUtils.getCurrentDay());
        mRoot.addView(mInflate);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(DateUtils.getCurrentDay());
        mMain_vp.setAdapter(new MainFragmentAdapter(getSupportFragmentManager(),mViews));
        mGuill = new GuillotineAnimation
                .GuillotineBuilder(mInflate, mInflate.findViewById(R.id.setting_iv_open), mMain_iv_open)
                .setActionBarViewForAnimation(mToolbar)
                .setClosedOnStart(true)
                .setGuillotineListener(new GuillotineListener() {
                    @Override
                    public void onGuillotineOpened() {
                        isOpen=true;
                    }

                    @Override
                    public void onGuillotineClosed() {
                        isOpen=false;
                    }
                })
                .build();

    }
    private void initView() {
        mInflate = UIUtils.inflate(R.layout.activity_main_guillotine);
        mInflate.findViewById(R.id.ll_addpaw).setOnClickListener(this);
        mInflate.findViewById(R.id.ll_backup).setOnClickListener(this);
        mInflate.findViewById(R.id.ll_alter_type).setOnClickListener(this);
        mInflate.findViewById(R.id.ll_alter_theme).setOnClickListener(this);
        mInflate.findViewById(R.id.ll_default_setting).setOnClickListener(this);
        mInflate.findViewById(R.id.ll_product).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_addpaw:
                Intent intent = new Intent(this, LockPatternActivity.class);
                intent.putExtra("from",1);
                startActivity(intent);
                break;
            case R.id.ll_backup:
                new DbManager(this).execute("ic_backup");
                break;
            case R.id.ll_restore:
                new DbManager(this).execute("restore");
            case R.id.ll_alter_type:
                startActivity(new Intent(MainActivity.this,AlterTypeActivity.class));
                break;
            case R.id.ll_alter_theme:
                break;
            case R.id.ll_default_setting:
                break;
            case R.id.ll_product:
                startActivity(new Intent(MainActivity.this,AboutProductActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isOpen){
            mGuill.close();
        }else {
            super.onBackPressed();
        }
    }
}
