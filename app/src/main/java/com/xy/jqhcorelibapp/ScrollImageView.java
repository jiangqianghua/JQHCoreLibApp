package com.xy.jqhcorelibapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class ScrollImageView extends AppCompatActivity {

    public final String TAG = ScrollImageView.class.getName();
    private ScrollView scrollView ;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_image_view);
        scrollView = findViewById(R.id.scrollView_id);
        linearLayout = findViewById(R.id.ll_layout);


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    public void testClick(View view) {
        Log.d(TAG, scrollView.getHeight() + " " + linearLayout.getMeasuredHeight());
        scrollView.smoothScrollBy(0, Dp2Px(this, 1200));
    }

//    public void addClick(View view) {
//        for (int i = 0 ; i < 20; i++) {
//            int color = i % 2 == 0 ? R.color.red : R.color.blue2;
//            ScrollImgeItem scrollImgeItem = new ScrollImgeItem(this, i + "", color);
//            linearLayout.addView(scrollImgeItem);
//        }
//    }

    public void addClick(View view) {
        for (int i = 0 ; i < 20; i++) {
            int color = i % 2 == 0 ? R.color.red : R.color.blue2;
            View layoutView  = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_scroll_image_item, null) ;
            RelativeLayout relativeLayout = layoutView.findViewById(R.id.root_ll);
            AppCompatTextView textView = layoutView.findViewById(R.id.text_view);
            textView.setText(i + "");
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Dp2Px(this, 400));
            layoutView.setLayoutParams(layoutParams);
            relativeLayout.setBackgroundColor(getResources().getColor(color));
            linearLayout.addView(layoutView);
        }
    }

    public void textViewClick(View view) {
        Log.d(TAG, "textViewClick");
    }

    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
