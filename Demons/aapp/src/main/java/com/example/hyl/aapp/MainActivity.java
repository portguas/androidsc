package com.example.hyl.aapp;


import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import commonClass.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 设置状态栏的颜色
        tintManager.setTintColor(Color.parseColor("#00000000"));
        MainApplication.getmActivitys().add(this);

        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainApplication.getmActivitys().remove(this);
    }

    private void initViews() {
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                break;
            default:
                break;
        }
    }
}
