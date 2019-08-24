package com.xy.updaterapplib.net;

import java.io.File;

/**
 * 网络请求接口
 */
public interface INetManager {
    void get(String url, INetCallBack callback);
    void download(String url, File targetFile, INetDownloadCallBack callback);
}
