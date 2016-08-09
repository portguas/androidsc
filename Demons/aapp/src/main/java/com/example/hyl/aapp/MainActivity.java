package com.example.hyl.aapp;


import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import commonClass.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Button mBtnLogin;
    private LoginBottomTabClass mLlLogin;

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
//        mLlLogin = (LoginBottomTabClass) findViewById(R.id.ll_login_icon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                showLoginPopWindow();

                break;
            default:
                break;
        }
    }

    private void showLoginPopWindow() {
        LoginPopWindow popLogin = new LoginPopWindow(MainActivity.this);
        popLogin.showAtLocation(findViewById(R.id.btn_login), Gravity.BOTTOM, 0, 0);
    }
}
