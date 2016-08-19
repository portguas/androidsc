package com.personal.allutil;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends BaseActivity {

    private Button button;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main2;
    }

    @Override
    public void initView(View v) {
        button = $(R.id.button);
    }

    @Override
    public void setListener() {
        button.setOnClickListener(this);
    }

    /**
     * 窗口点击
     *
     * @param view
     */
    @Override
    public void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                showToast("123");
                break;
            default:
                break;
        }
    }

    @Override
    public void initOperation(Context context) {

    }
}
