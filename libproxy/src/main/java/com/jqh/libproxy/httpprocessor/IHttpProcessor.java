package com.jqh.libproxy.httpprocessor;

import java.util.Map;

public interface IHttpProcessor {

    void post(String url, Map<String, String> params, ICallback callback);
    void get(String url, Map<String, String> params, ICallback callback);
}
