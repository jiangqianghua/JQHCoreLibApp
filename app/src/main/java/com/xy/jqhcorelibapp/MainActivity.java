package com.xy.jqhcorelibapp;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.xy.updaterapplib.AppUpdater;
import com.xy.updaterapplib.bean.DownloadBean;
import com.xy.updaterapplib.net.INetCallBack;
import com.xy.updaterapplib.net.INetDownloadCallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // 更新app按钮
    private AppCompatButton updateAppBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateAppBtn = findViewById(R.id.update_app_btn);

        initEvent();
    }


    private void initEvent(){
        updateAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater.getInstance().startUpdate(MainActivity.this);
            }
        });
    }
}
