package com.personal.floatwindow;

import java.util.Map;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by heyulong on 8/24/2016.
 */

public class FloatViewController implements View.OnClickListener, View.OnTouchListener, FloatViewContainer.KeyEventHandler {

    private Context context;
    private String mProgress;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    private TextView mTextView;
    private FloatViewContainer mWholeView;

    private boolean inOnclick = true;

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    public FloatViewController(Context context, String progress) {
        this.context = context;
        mProgress = progress;
        mWindowManager = MyApplication.getInstance().getWindowManager();
    }

    public void updateContent(String content) {
        mProgress = content;
        if (null != mTextView) {
            mTextView.setText(mProgress);
        }
    }

    public void show() {
        FloatViewContainer fvc  = (FloatViewContainer) View.inflate(context, R.layout.float_layout, null);
        mTextView = (TextView) fvc.findViewById(R.id.tv_progress);
        mTextView.setText(mProgress);
        mTextView.setOnClickListener(this);

        mWholeView = fvc;
        mWholeView.setOnTouchListener(this);
        mWholeView.setKeyEventHandler(this);

        params = MyApplication.getInstance().getWmParams();
        int type;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.type = type;
        params.flags = flags;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT | Gravity.TOP;;

        params.x = 0;
        params.y = 0;

        mWindowManager.addView(mWholeView, params);

    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(MyApplication.getInstance().getContext(), ((TextView)v).getText().toString(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     *
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getRawX();
        y = event.getRawY() - DensityUtil.dip2px(MyApplication.getInstance().getContext(), 25);

        // 保证每一次的拖动都是在中心点
        if (inOnclick) {
            Log.d("Test", "isOnclick");
            mTouchStartX = v.getWidth() / 2 + v.getLeft();
            mTouchStartY = v.getHeight() / 2 + v.getTop();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX =  event.getX();
                mTouchStartY =  event.getY();
                Log.i("startP","startX"+mTouchStartX+"====startY"+mTouchStartY);
                break;
            case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                inOnclick = false;
                Log.d("Test", "ACTION_MOVE");
                updateViewPosition(mWholeView, mWindowManager, params);
                break;
            case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                updateViewPosition(mWholeView,mWindowManager, params);
                mTouchStartX=mTouchStartY=0;
                inOnclick = true;
                break;
        }
        return false;
    }

    @Override
    public void onKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            Toast.makeText(MyApplication.getInstance().getContext(), "back", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateViewPosition(FloatViewContainer mFloatLayout, WindowManager mWindowManager,
                                    WindowManager.LayoutParams params) {

        params.x=(int)( x-mTouchStartX);
        params.y=(int) (y-mTouchStartY);
        mWindowManager.updateViewLayout(mFloatLayout, params);
    }
}
