package com.xy.updaterapplib.net;

public interface INetCallBack {
    void success(String response);

    void failed(Throwable throwable);
}
