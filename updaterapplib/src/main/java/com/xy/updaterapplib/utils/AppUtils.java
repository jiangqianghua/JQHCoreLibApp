package com.xy.updaterapplib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import java.io.File;

public class AppUtils {
    public static long getVersionCode(Context context){
        PackageManager packageManager = context.getPackageManager();
        try{
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.getLongVersionCode();
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 安装app
     * @param activity
     * @param apkFile
     */
    public static void installApk(Activity activity , File apkFile){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(apkFile);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        activity.startActivity(intent);

    }
}
