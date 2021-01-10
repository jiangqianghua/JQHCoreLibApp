package com.jqh.libproxy.httpprocessor;

import android.os.Handler;
import android.os.Looper;

import com.jqh.libproxy.httpprocessor.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpProcessor  implements IHttpProcessor {

    private Handler mHandler;

    public OkHttpProcessor() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(String url, Map<String, String> params, ICallback callback) {
        OkHttpUtils.getInstance().PostWithFormData(url, params, new OkHttpUtils.MyCallBack() {
            @Override
            public void onLoadingBefore(Request request) {

            }

            @Override
            public void onSuccess(Response response) {

                try
                {
                    String responseStr = response.body().string();
                    if (callback != null) mHandler.post(() -> callback.onSuccess(responseStr));
                }catch (IOException e) {
                    if (callback != null) mHandler.post(() -> callback.onFailure(e.getMessage()));
                }

            }

            @Override
            public void onFailure(Request request, Exception e) {
                if (callback != null) mHandler.post(() -> callback.onFailure(e.getMessage()));
            }

            @Override
            public void onError(Response response) {
                if (callback != null) mHandler.post(() -> callback.onFailure("error"));
            }
        });
    }

    @Override
    public void get(String url, Map<String, String> params, ICallback callback) {
        // 拼接参数
        url += appendParams(params);
        OkHttpUtils.getInstance().Get(url, new OkHttpUtils.MyCallBack() {

            @Override
            public void onLoadingBefore(Request request) {

            }

            @Override
            public void onSuccess(Response response) {

                try
                {
                    String responseStr = response.body().string();
                    if (callback != null) mHandler.post(() -> callback.onSuccess(responseStr));
                }catch (IOException e) {
                    if (callback != null) mHandler.post(() -> callback.onFailure(e.getMessage()));
                }

            }

            @Override
            public void onFailure(Request request, Exception e) {
                if (callback != null) mHandler.post(() -> callback.onFailure(e.getMessage()));
            }

            @Override
            public void onError(Response response) {
                if (callback != null) mHandler.post(() -> callback.onFailure("error"));
            }
        });
    }

    private String appendParams(Map<String, String> params) {
        if (params == null) return "";
        String paramsStr = "?";
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        int i = 0 ;
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if (i > 0) {
                paramsStr += "&";
            }
            paramsStr += entry.getKey() + "=" + entry.getValue();
        }
        return paramsStr;
    }

}
