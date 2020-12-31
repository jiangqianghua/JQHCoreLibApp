package com.xy.jqhcorelibapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup2 extends ViewGroup {

    private static final String TAG = "zjy";

    public MyViewGroup2(Context context) {
        this(context,null);
    }

    public MyViewGroup2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
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
        Log.d(TAG, "MyViewGroup2--->dispatchTouchEvent"+ ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "MyViewGroup2--->onInterceptTouchEvent"+ ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "MyViewGroup2--->onTouchEvent"+ event.getAction());
        return super.onTouchEvent(event);
    }
}
