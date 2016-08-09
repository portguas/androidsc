package com.example.hyl.aapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hyl on 2016/7/20.
 */
public class LoginPopWindow extends PopupWindow implements View.OnClickListener{

    private View view;

    // 登录控件
    private TextView tvQQ;
    private TextView tvWeiXin;
    private TextView tvWeiBo;
    private Context mContext;
    public LoginPopWindow(Context context) {
        this(context, null);
    }

    public LoginPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.login_choise_layout, null);
        tvQQ = (TextView) view.findViewById(R.id.tv_qq);
        tvWeiXin = (TextView) view.findViewById(R.id.tv_weixin);
        tvWeiBo = (TextView) view.findViewById(R.id.tv_weibo);
        tvQQ.setOnClickListener(this);
        tvWeiXin.setOnClickListener(this);
        tvWeiBo.setOnClickListener(this);

        this.setOutsideTouchable(true);
        this.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        this.setContentView(this.view);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹窗客店
        this.setFocusable(true);
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.login_anim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qq:
                final Intent intent = new Intent(MainApplication.getContext(), FirstActivity.class);
                MainApplication.getContext().startActivity(intent);
                break;
            case R.id.tv_weixin:
                // createLoadingDialog的第一个参数不能用Main.getContext，因为Diaog对话框必须依赖一个Activity才能添加窗口
                final Dialog loadingDialog = LoadingDialog.createLoadingDialog((Activity)mContext, "Weixin登录中..");
                loadingDialog.show();
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                        Intent intent1 = new Intent(MainApplication.getContext(), FirstActivity.class);
                        // 貌似只要不是在Acticity中启动Activity的话 就必须添加这个Flag
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainApplication.getContext().startActivity(intent1);
                    }
                }, 20000);
                break;
            case R.id.tv_weibo:
                Intent intent2 = new Intent(MainApplication.getContext(), DrawerActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainApplication.getContext().startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
