package com.xy.jqhcorelibapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class PermissionActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private Button requestPermission ;

    public  final int RC_CAMERA_AND_LOCATION = 1001 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        requestPermission = findViewById(R.id.requestPermission);

        requestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
                if (EasyPermissions.hasPermissions(PermissionActivity.this, perms)) {

                    // 已经申请过权限，做想做的事
                } else {
                    // 没有申请过权限，现在去申请
                    EasyPermissions.requestPermissions(PermissionActivity.this, "申请摄像头和定位",
                            RC_CAMERA_AND_LOCATION, perms);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions, grantResults, this);
//        requestPermissions() 一般用这个四个参数的就可以
//        第一个参数：Context对象
//        第二个参数：权限弹窗上的文字提示语。告诉用户，这个权限用途。
//        第三个参数：这次请求权限的唯一标识请求码，code。
//        第四个参数 : 一些系列的权限。
//        这里说一下第二个参数，不是第一次申请系统默认弹出的提示语，而是，我们拒绝后，再次点击申请弹出的对话框，，显示我们设置的提示语，下面有两个按钮，确认和取消，我就不贴图了。
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "已获取权限 " + requestCode + " "  + perms.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "已拒绝权限" + requestCode + " "  +  perms.toString(), Toast.LENGTH_SHORT).show();
    }
}
