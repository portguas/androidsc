package com.personal.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by heyulong on 9/8/2016.
 */

public class MyLayout extends LinearLayout {
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("Demo:", "父View的dispatchTouchEvent方法执行了" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("Demo:", "父View的onInterceptTouchEvent方法执行了" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("Demo:", "父View的onTouchEvent方法执行了" + event.getAction());
        return super.onTouchEvent(event);
    }

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attr) {
        super(context,attr);
    }
}
