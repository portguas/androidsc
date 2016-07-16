package com.example.topbaranimation;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MyScrollView.BottomListener,
        MyScrollView.SrollListener, View.OnTouchListener{

    private static final String TAG = "TAG";

    // 顶部布局隐藏的检测距离
    private static final int TOP_DISTANCE_Y = 120;

    // 默认的动画时间
    private static final int ANIMATE_TIME = 300;

    // 是否在顶部布局的滑动范围内
    private boolean bIsOnTopDistance = true;

    // control
    private ImageView img_bar;
    private TextView tv_title;
    private ImageView img_tools;
    private ImageView img_author;
    private MyScrollView mScroller;
    private FrameLayout fl_top;
    private TextView tv_content;

    private GestureDetectorCompat mDetectorCompat;

    private float viewSlop;
    //按下的y坐标
    private float lastY;
    //记录手指是否向上滑动
    private boolean isUpSlide;
    //工具栏是否是隐藏状态
    private boolean isToolHide;
    //上部布局是否是隐藏状态
    private boolean isTopHide = false;
    //动画是否结束
    private boolean isAnimationFinish = true;
    //是否已经完成测量
    private boolean isMeasured = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        img_bar = (ImageView) findViewById(R.id.img_bar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        img_tools = (ImageView) findViewById(R.id.img_tools);
        img_author = (ImageView) findViewById(R.id.img_author);
        mScroller = (MyScrollView) findViewById(R.id.scroller);
        fl_top = (FrameLayout) findViewById(R.id.ll_top);

        viewSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        mScroller.setOnBottomListener(this);
        mScroller.setOnSrollListener(this);
        mScroller.setOnTouchListener(this);

        mDetectorCompat = new GestureDetectorCompat(this, new DetailGestor());
        //获取Bar和Title的高度，完成auther布局的margenTop设置
        ViewTreeObserver observer = fl_top.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!isMeasured) {
                    // 重新布局作者信息的位置
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout
                            .LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, img_bar.getHeight() + tv_title.getHeight(), 0, 0);
                    img_author.setLayoutParams(layoutParams);
                    isMeasured = true;
                }
                return true;  // 一定要返回为true 否则什么都不会显示
            }
        });
    }

    public void showTools() {

        int startY = getWindow().getDecorView().getHeight() - getStatusHeight(this);
        ObjectAnimator animator = ObjectAnimator.ofFloat(img_tools, "y", startY, startY - img_tools.getHeight());
        animator.setDuration(ANIMATE_TIME);
        animator.start();
        isToolHide = false;
    }

    public void hideTools() {
        int startY = getWindow().getDecorView().getHeight() - getStatusHeight(this);
        ObjectAnimator animator = ObjectAnimator.ofFloat(img_tools, "y", startY - img_tools.getHeight(), startY);
        animator.setDuration(ANIMATE_TIME);
        animator.start();
        isToolHide = true;
    }

    @Override
    public void onBottom() {
        if (isToolHide) {
            showTools();
        }
    }

    // 显示上部的布局
    public void showTop() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(img_bar, "y", img_bar.getY(), 0);
        animator.setDuration(ANIMATE_TIME);
        animator.start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(tv_title, "y", tv_title.getY(), img_bar.getHeight());
        animator1.setDuration(ANIMATE_TIME);
        animator1.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(fl_top, "y", fl_top.getY(), 0);
        animator2.setDuration(ANIMATE_TIME);
        animator2.start();

        isTopHide = false;
    }

    public void hideTop() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(img_bar, "y", 0, -img_bar.getHeight());
        animator.setDuration(ANIMATE_TIME);
        animator.start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(tv_title, "y", tv_title.getY(), -tv_title.getHeight());
        animator1.setDuration(ANIMATE_TIME);
        animator1.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(fl_top, "y", 0, -(img_bar.getHeight() + tv_title.getHeight()));
        animator2.setDuration(ANIMATE_TIME);
        animator2.start();

        isTopHide = true;

    }

    @Override
    public void onScroll(int l, int t, int oldl, int oldt) {
        Log.d("TAG", "onScroll");
        if (t < dp2px(TOP_DISTANCE_Y)) {
            bIsOnTopDistance = true;
        } else {
            bIsOnTopDistance = false;
        }

        if (t <= dp2px(TOP_DISTANCE_Y) && isTopHide) {
            showTop();
        } else if (t > dp2px(TOP_DISTANCE_Y) && !isTopHide) {
            hideTop();
        }
    }


    private int dp2px(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("TAG", "MOVE");
                float disY = event.getY() - lastY;
                // viewSlop是系统设置的最小的滑动距离, 如果小于这个距离就不认为是滑动
                if (Math.abs(disY) > viewSlop) {
                    isUpSlide = disY < 0;

                    // 向上活动的话 就判断是否隐藏
                    if (isUpSlide) {
                        if (!isToolHide) {
                            hideTools();
                        }
                    } else {     // 向下滑动的判断是否显示
                        if (isToolHide) {
                            showTools();
                        }
                    }
                }
                lastY = event.getY();
                break;
        }

        mDetectorCompat.onTouchEvent(event);
        return false;
    }

    private class DetailGestor extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (isTopHide && isToolHide) {
                showTools();
                showTop();
            } else if (!isToolHide && isTopHide) {
                showTop();
            } else if (!isTopHide && isToolHide) {
                showTools();
            } else {
                hideTools();
                if (!bIsOnTopDistance) {
                    hideTop();
                }
            }
            return super.onSingleTapConfirmed(e);
        }
    }

    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        // getWindowVisibleDisplayFrame 获取程序的显示区域,但是不包括其中的状态栏, 因此可以获取其中的状态栏的高度
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return statusHeight;
    }
}
