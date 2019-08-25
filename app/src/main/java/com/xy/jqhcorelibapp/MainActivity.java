package com.xy.jqhcorelibapp;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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


    private void initEvent(){
        updateAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater.getInstance().startUpdate(MainActivity.this);
            }
        });
    }

//    @PermissionGrant(RESULT_CODE_PERMISSIONS_WRITE_EXTERNAL_STORAGE)
//    public void onRequestWriteExternalStorageGranted(String[] grantPermissions){
//    }
//
//    @PermissionDenied(RESULT_CODE_PERMISSIONS_WRITE_EXTERNAL_STORAGE)
//    public void onRequestWriteExternalStorageDenied(String[] deniedPermissions){
//
//    }
//
//    @PermissionRational(RESULT_CODE_PERMISSIONS_WRITE_EXTERNAL_STORAGE)
//    public void onRequestWriteExternalStorageRational(String[] permissions){
//        new AlertDialog.Builder(this).setTitle("权限授权提示")
//                            .setMessage("请授予一下权限，以继续功能的使用\n\n" + permissions.toString())
//                            .setPositiveButton("好的", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            }).create().show();
//    }

//    private void checkPermission(){
//        //  申请写的权限
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//            // 只有当用户同时点选了拒绝开启权限和不再提醒后才会true
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//                //你自己写提醒用户的逻辑,引导用户手动去设置里开启权限
//                Toast.makeText(MainActivity.this, "您权限被拒绝了,需要手动开启哦", Toast.LENGTH_SHORT).show();
//            } else {
//                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_CODE_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case RESULT_CODE_PERMISSIONS_WRITE_EXTERNAL_STORAGE:
//                StringBuilder builder = new StringBuilder();
//                for(int i = 0 , len = grantResults.length; i<len; i++){
//                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
//                        builder.append(permissions[i]);
//                    }
//                }
//                if(builder.length() > 0){
//                    new AlertDialog.Builder(this).setTitle("权限授权提示")
//                            .setMessage("请授予一下权限，以继续功能的使用\n\n" + builder.toString())
//                            .setPositiveButton("好的", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            }).create().show();
//                }
//                break;
//        }
//    }
}
