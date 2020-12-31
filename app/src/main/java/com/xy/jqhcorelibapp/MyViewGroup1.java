package com.xy.jqhcorelibapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup1 extends ViewGroup {

    private static final String TAG = "zjy";

    public MyViewGroup1(Context context) {
        this(context,null);
    }

    public MyViewGroup1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            view.layout(100,100,getWidth()-100,getHeight()-100);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MyViewGroup1-->dispatchTouchEvent"+ ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MyViewGroup1-->onInterceptTouchEvent"+ ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "MyViewGroup1-->onTouchEvent"+ event.getAction());
        return super.onTouchEvent(event);
    }
}
