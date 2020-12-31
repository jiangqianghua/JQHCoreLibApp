package com.xy.jqhcorelibapp;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.widget.AppCompatTextView;

public class ScrollImgeItem extends RelativeLayout {
    private Context context;
    private RelativeLayout relativeLayout;
    private AppCompatTextView textView;
    public ScrollImgeItem(Context context, String name, int color) {
        super(context);
        init(context, name, color);
    }

    private void init(Context context, String name, int color) {
        inflate(context, R.layout.layout_scroll_image_item, this);
        relativeLayout = findViewById(R.id.root_ll);
        textView = findViewById(R.id.text_view);
        textView.setText(name);
        relativeLayout.setBackgroundColor(getResources().getColor(color));
    }


}
