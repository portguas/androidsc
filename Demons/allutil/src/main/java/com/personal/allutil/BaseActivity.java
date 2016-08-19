package com.personal.allutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Created by zzz on 8/18/2016.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private View mContextView = null;

    /** 日志输出标志 **/
    protected final String TAG = this.getClass().getSimpleName();


    public abstract void initParms(Bundle parms);

    public abstract int bindLayout();

    public abstract void initView(View v);

    public abstract void setListener();

    public abstract void initOperation(Context context);

    /**
     * 窗口点击
     * @param view
     */
    public  abstract void widgetClick(View view);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);

        setContentView(mContextView);
        initView(mContextView);
        setListener();

        initOperation(this);
    }

    protected <T extends View> T $(int resid) {
        return (T) findViewById(resid);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        widgetClick(v);
    }



    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 带有返回的activity请求
     * @param clz
     * @param bundle
     * @param reqCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle, int reqCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, reqCode);
    }

    public void showToast(int resid) {
        Toast.makeText(this, resid, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String strMsg) {
        Toast.makeText(this, strMsg, Toast.LENGTH_SHORT).show();
    }
}
