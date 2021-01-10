package com.jqh.libproxy.httpprocessor;

import java.util.Map;

public class HttpHelper implements IHttpProcessor {

    private static HttpHelper instance;

    public static HttpHelper obtain(){
        synchronized (HttpHelper.class) {
            if (instance == null) {
                instance = new HttpHelper();
            }
        }
        return instance;
    }

    private HttpHelper(){}

    private static IHttpProcessor mIHttpProcessor = null;

    public static void init(IHttpProcessor httpProcessor) {
        mIHttpProcessor = httpProcessor;
    }


    @Override
    public void post(String url, Map<String, String> params, ICallback callback) {

        // 把url 后面的参数存到params上
        mIHttpProcessor.post(url, params, callback);
    }

    @Override
    public void get(String url, Map<String, String> params, ICallback callback) {
        mIHttpProcessor.get(url, params, callback);
    }
}
