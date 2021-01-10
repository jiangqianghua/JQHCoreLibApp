package com.xy.jqhcorelibapp;

import android.app.Application;

import com.jqh.libproxy.httpprocessor.HttpHelper;
import com.jqh.libproxy.httpprocessor.OkHttpProcessor;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpHelper.init(new OkHttpProcessor());
    }
}
