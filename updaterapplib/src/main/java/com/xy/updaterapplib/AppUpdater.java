package com.xy.updaterapplib;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.xy.updaterapplib.bean.DownloadBean;
import com.xy.updaterapplib.net.INetCallBack;
import com.xy.updaterapplib.net.INetDownloadCallBack;
import com.xy.updaterapplib.net.INetManager;
import com.xy.updaterapplib.net.OkHttpNetManager;
import com.xy.updaterapplib.ui.UpdateVersionShowDialog;
import com.xy.updaterapplib.utils.AppUtils;

import java.io.File;

/**
 * app 升级对外接口
 */
public class AppUpdater {

    private Context context ;
    private static AppUpdater instance = new AppUpdater();

    private INetManager mNetManager = new OkHttpNetManager();

    public void setNetManager(INetManager mNetManager) {
        this.mNetManager = mNetManager;
    }

    public INetManager getNetManager() {
        return mNetManager;
    }

    public static AppUpdater getInstance(){
        return instance;
    }

    public void startUpdate(final Context context){
        this.context = context ;
        AppUpdater.getInstance().getNetManager().get("http://www.52res.cn/appupdate/update.json", new INetCallBack() {
            @Override
            public void success(String response) {
                Log.d("MainActivity", response);
                DownloadBean downloadBean = DownloadBean.parse(response);
                if(downloadBean == null){
                    Toast.makeText(context, "版本检测接口返回数据异常", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    long versionCode = Long.parseLong(downloadBean.versionCode);
                    long localVersionCode = AppUtils.getVersionCode(context);
                    if(versionCode <= localVersionCode){
                        // 已经是最新版本
                        Toast.makeText(context, "已经是最新版本", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    Toast.makeText(context, "版本检测接口返回版本号异常", Toast.LENGTH_SHORT).show();
                    return;
                }

                UpdateVersionShowDialog.show((FragmentActivity) context,downloadBean);

            }

            @Override
            public void failed(Throwable throwable) {
                Log.d("MainActivity", throwable.getMessage());
            }
        },context);
    }

    public void cancel(Object tag){
        AppUpdater.getInstance().getNetManager().cancel(tag);
    }

}
