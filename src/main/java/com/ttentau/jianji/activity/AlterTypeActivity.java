package com.ttentau.jianji.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ttentau.jianji.R;
import com.ttentau.jianji.util.SpUtils;
import com.ttentau.jianji.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ttentau on 2017/6/28.
 */

public class AlterTypeActivity extends AppCompatActivity {
    @BindView(R.id.et_in)
    EditText mEt_in;
    @BindView(R.id.et_out)
    EditText mEt_out;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altertype);
        ButterKnife.bind(this);
        String typein = SpUtils.getType("typein", "");
        String typeout = SpUtils.getType("typeout", "");
        mEt_in.setText(typein);
        mEt_out.setText(typeout);
    }
    @OnClick(R.id.btn_commit)
    public void onClick(View v){
       SpUtils.putType("typein",mEt_in.getText().toString());
       SpUtils.putType("typeout",mEt_out.getText().toString());
        UIUtils.Toast("ok");
    }
}
