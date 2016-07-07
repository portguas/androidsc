package com.example.hyl.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyl on 6/19/2016.
 */
public class FlowLayout extends ViewGroup{
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    // 会测量多次 比如把宽高设置为200dp的时候测试的结果就如下
    // hyl.flowlayout D/Tag: 宽度600
    // hyl.flowlayout D/Tag: 高度1533
    // hyl.flowlayout D/Tag: 宽度600
    // hyl.flowlayout D/Tag: 高度600
    // hyl.flowlayout D/Tag: 宽度600
    // hyl.flowlayout D/Tag: 高度1533
    // hyl.flowlayout D/Tag: 宽度600
    // hyl.flowlayout D/Tag: 高度600
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 测试出来的是flowlayout的宽高的像素值px。
        // 如果是精确的话  sizeWidth和sizeHeight即为父控件的宽高
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        Log.d("Tag", "宽度" + sizeWidth);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.d("Tag", "高度" + sizeHeight);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 当前一行的宽高
        int lineWidth = 0;
        int lineHeight = 0;

        // 如果是wrap_content的时候
        // width 是没一行中 最宽的那个
        int width = 0;
        int height = 0;

        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin;

            // 换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else {
            setMeasuredDimension(sizeWidth, sizeHeight);
        }

        setMeasuredDimension(
                widthMode == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingRight() + getPaddingLeft(),
                heightMode == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingBottom() + getPaddingTop());

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private List<List<View>> mAllviews = new ArrayList<List<View>>();

    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllviews.clear();
        mLineHeight.clear();

        // 当前的viewgroup的宽度
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<>();
        int cCount = getChildCount();
        for (int i = 0; i<cCount;i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果需要换行
            if (childWidth + lineWidth + layoutParams.leftMargin + layoutParams.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                mLineHeight.add(lineHeight);
                mAllviews.add(lineViews);


                lineWidth = 0;
                lineHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin;
                lineViews = new ArrayList<>();
            }

            lineViews.add(child);
            lineWidth += childWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + layoutParams.topMargin + layoutParams.bottomMargin);
        }

        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllviews.add(lineViews);

        // 设置子view
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNum = mAllviews.size();
        for (int i =0;i<lineNum;i++) {
            lineViews = mAllviews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j=0; j<lineViews.size();j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    // 父容器生成子view的布局的LayoutParams
    // 因此会影响到子view的布局
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
