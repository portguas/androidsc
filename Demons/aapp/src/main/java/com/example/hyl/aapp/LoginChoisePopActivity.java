package com.example.hyl.aapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import commonClass.BaseActivity;

/**
 * Created by hyl on 7/19/2016.
 */
public class LoginChoisePopActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_choise_layout);

        MainApplication.getmActivitys().add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainApplication.getmActivitys().remove(this);
    }
}
