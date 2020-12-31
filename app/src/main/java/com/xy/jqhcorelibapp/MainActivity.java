package com.xy.jqhcorelibapp;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
//
//import com.jqh.libannotion.annotion.PermissionDenied;
//import com.jqh.libannotion.annotion.PermissionGrant;
//import com.jqh.libannotion.annotion.PermissionRational;
import com.jqh.record.RecordMainActivity;
import com.xy.updaterapplib.AppUpdater;
import com.xy.updaterapplib.bean.DownloadBean;
import com.xy.updaterapplib.net.INetCallBack;
import com.xy.updaterapplib.net.INetDownloadCallBack;

import java.io.File;

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
}
