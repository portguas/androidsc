package com.example.topbaranimation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by hyl on 2016/7/16.
 */
public class MyScrollView extends ScrollView {

    private BottomListener bottomListener;
    private SrollListener srollListener;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // computeVerticalScrollRange 获取ScrollView中子布局的实际高度，包括超出的屏幕的范围
        // getHeight 获取的是ScrollView的的高度,不包括超出的高度
        Log.d("TAG", "t=:" + t + ",***y" + getScrollY());
        if (getScrollY() + getHeight() >= computeVerticalScrollRange()) {
            if (null != bottomListener) {
                bottomListener.onBottom();
            }
        }
        if (null != srollListener) {
            srollListener.onScroll(l, t, oldl, oldt);
        }
    }

    public interface BottomListener {
        public void onBottom();
    }

    public interface SrollListener {
        public void onScroll(int l, int t, int oldl, int oldt);
    }

    public void setOnBottomListener(BottomListener bottomListener) {
        this.bottomListener = bottomListener;
    }

    public void setOnSrollListener(SrollListener srollListener) {
        this.srollListener = srollListener;
    }
}
