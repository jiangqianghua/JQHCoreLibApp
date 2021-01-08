package com.xy.libuimodule.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private int mHorizontalSpacing = 10;
    private int mVerticalSpacing = 10;

    private List<List<View>> allLines = new ArrayList<>(); // 记录所有行，一行行存储，用户layout
    List<Integer> lineHeights = new ArrayList<>(); // 记录每一行的高，用户layout
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 度量孩子
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec); // viewGroup解析是父亲给我的宽高
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec); // ViewGroup解析是父亲给我的宽高

        List<View> lineViews = new ArrayList<>(); // 保存一行中所有的view

        int lineWidthUsed = 0; // 记录这行使用了多宽的size
        int lineHeight = 0 ; // 一行的行高

        int parentNeededWidth = 0 ;
        int parentNeededHeight = 0 ; // measure过程中， 子view要求父viewGroup的高度
        allLines = new ArrayList<>();
        lineHeights = new ArrayList<>();
        for (int i = 0 ; i < childCount; i++){
            View childView = getChildAt(i);
            LayoutParams childP = childView.getLayoutParams();
            //  获取孩子的宽高
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                    paddingLeft + paddingRight,
                    childP.width);

            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                    paddingTop + paddingBottom,
                    childP.height);
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            // 执行完该代码，才可以获取到孩子的真实宽高
            int childMesaureWidth = childView.getMeasuredWidth();
            int childMeasureHeight = childView.getMeasuredHeight();

            // 判断是否换行
            if (childMesaureWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {
                allLines.add(lineViews);
                lineHeights.add(lineHeight);
                lineViews = new ArrayList<>();
                lineWidthUsed = 0 ;
                lineHeight = 0 ;
                parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                parentNeededWidth = Math.max(parentNeededWidth, lineHeight + mHorizontalSpacing);
            }
            // View是分行的layout的，要记录每一行那些view，这样可以方便layout布局
            lineViews.add(childView);
            // 每行都会有自己的宽高
            lineWidthUsed = lineWidthUsed + childMesaureWidth + mHorizontalSpacing;
            lineHeight = Math.max(lineHeight, childMeasureHeight);


        }

        // 再度量自己， 宽高自适应
        // 根据子View的度量结果，重新来度量自己的ViewGroup
        // 作为一个ViewGroup，他自己也是一个View，他的大小也需要根据他父亲提供的宽高来度量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth: parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight: parentNeededHeight;
        // 开始度量自己的宽高
        setMeasuredDimension(realWidth, realHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int linecount = allLines.size();
        int curL = getPaddingLeft(); // 父亲父亲的padding
        int curT = getPaddingTop();
        for (int i = 0 ; i < linecount; i++){
            List<View> lineViews = allLines.get(i);
            int lineHeght = lineHeights.get(i);
            for (int j = 0 ; j < lineViews.size(); j++){
                View view = lineViews.get(j);
                int left = curL;
                int top = curT;

                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom );
                curL = right + mHorizontalSpacing;
            }
            curL = getPaddingLeft();
            curT = curT + lineHeght + mVerticalSpacing;
        }
    }
}
