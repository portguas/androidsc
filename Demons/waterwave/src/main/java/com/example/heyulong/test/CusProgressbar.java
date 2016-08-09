package com.example.heyulong.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by heyulong on 8/5/2016.
 */

public class CusProgressbar extends RelativeLayout {
    LayoutParams lpImageBack;
    private Bitmap bitmap = null;   //  图片位图
    private Paint paint = null; //  画笔
    private ImageView image;

    public CusProgressbar(Context context) {
        this(context, null);
    }

    public CusProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        image = new ImageView(this.getContext());
        image.setBackgroundResource(R.drawable.loading);
        lpImageBack = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpImageBack.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        lpImageBack.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(image, lpImageBack);
    }

    public void start() {
        Animation a = AnimationUtils.loadAnimation(this.getContext(), R.anim.loading);
        a.setDuration(1500);
        image.startAnimation(a);
    }
}
