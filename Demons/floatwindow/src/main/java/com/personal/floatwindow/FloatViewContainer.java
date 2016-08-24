package com.personal.floatwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by heyulong on 8/24/2016.
 */

public class FloatViewContainer extends FrameLayout {

    public KeyEventHandler mKeyEventHandler;

    public FloatViewContainer(Context context) {
        super(context);
    }

    public FloatViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * {@inheritDoc}
     *
     * @param event
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (null != mKeyEventHandler) {
            mKeyEventHandler.onKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    public void setKeyEventHandler(KeyEventHandler handler) {
        mKeyEventHandler = handler;
    }

    public interface KeyEventHandler {
        void onKeyEvent(KeyEvent event);
    }

    /**
    NCEL event, and no further
     * messages will be delivered here.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:    //点击事件可以传递 子view可以响应click
                return false;
            case MotionEvent.ACTION_MOVE:    // 拦截此事件, layout可以用拖动
                return true;
            case MotionEvent.ACTION_UP:      // 不拦截点击事件
                return false;
            default:
                return false;
        }
    }
}
