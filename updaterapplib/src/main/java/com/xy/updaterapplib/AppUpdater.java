package com.xy.updaterapplib;

import com.xy.updaterapplib.net.INetManager;
import com.xy.updaterapplib.net.OkHttpNetManager;

/**
 * app 升级对外接口
 */
public class AppUpdater {
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
}
