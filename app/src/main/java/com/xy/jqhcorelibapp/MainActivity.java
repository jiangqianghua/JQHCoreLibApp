package com.xy.jqhcorelibapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
//
//import com.jqh.libannotion.annotion.PermissionDenied;
//import com.jqh.libannotion.annotion.PermissionGrant;
//import com.jqh.libannotion.annotion.PermissionRational;
import com.jqh.libgpuimage.SimpleActivity;
import com.jqh.record.RecordMainActivity;
import com.xy.updaterapplib.AppUpdater;

public class MainActivity extends AppCompatActivity {

    // 更新app按钮
    private AppCompatButton updateAppBtn;

    private static final int RESULT_CODE_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 100 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateAppBtn = findViewById(R.id.update_app_btn);

        initEvent();

        //checkPermission();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void initEvent(){
        updateAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater.getInstance().startUpdate(MainActivity.this);
            }
        });
    }


    public void enterRecord(View view){
        Intent intent = new Intent(this, RecordMainActivity.class);
        startActivity(intent);
    }

    public void enterGpuImage(View view) {
        Intent intent = new Intent(this, SimpleActivity.class);
        startActivity(intent);
    }

    public void enterGpuImage1(View view) {
        Intent intent = new Intent(this, jp.co.cyberagent.android.gpuimage.sample.activity.MainActivity.class);
        startActivity(intent);
    }
}
