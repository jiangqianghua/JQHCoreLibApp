package com.xy.libuimodule.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.xy.libuimodule.R;

public class JTextView extends View {

    private String text;

    private Paint mPaint;
    private Rect mTextBounds ;

    private int mHeight , mWidth;
    public JTextView(Context context) {
        this(context, null);
    }

    public JTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.JTextView);
        text = ta.getString(R.styleable.JTextView_jtext_text);
        ta.recycle();

        mPaint = new Paint();
        // 确定文字区域
        mTextBounds = new Rect();

        mPaint.setTextSize(50);
        // 确定区域
        mPaint.getTextBounds(text, 0, text.length(), mTextBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specWidth;
        } else if (specMode == MeasureSpec.AT_MOST) {
            mWidth = mTextBounds.width() + getPaddingLeft() + getPaddingRight();
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            mHeight = specHeight;
        } else {
            mHeight = mTextBounds.height() + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, getPaddingLeft() + 0, getPaddingTop() +  mTextBounds.height(), mPaint);
    }
}
