package com.jqh.libproxy.httpprocessor;

public interface ICallback {

    void onSuccess(String str);
    void onFailure(String e);
}
