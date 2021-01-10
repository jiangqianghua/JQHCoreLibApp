package com.jqh.libproxy.httpprocessor.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    /**
     * 网络访问要求singleton
     */
    private static OkHttpUtils instance;
    // 必须要用的okhttpclient实例,在构造器中实例化保证单一实例
    private OkHttpClient mOkHttpClient;
    public static final MediaType JSON = MediaType.
            parse("application/json; charset=utf-8");
    private OkHttpUtils() {
        /**
         * okHttp3中超时方法移植到Builder中
         */
        mOkHttpClient = (new OkHttpClient()).newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }
    public static OkHttpUtils getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils();
                }
            }
        }
        return instance;
    }
    /**
     * 对外提供的Get方法访问
     * @param url
     * @param callBack
     */
    public void Get(String url, MyCallBack callBack) {
        /**
         * 通过url和GET方式构建Request
         */
        Request request = bulidRequestForGet(url);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }
    /**
     * 对外提供的Post方法访问
     * @param url
     * @param parms: 提交内容为表单数据
     * @param callBack
     */
    public void PostWithFormData(String url, Map<String, String> parms, MyCallBack callBack) {
        /**
         * 通过url和POST方式构建Request
         */
        Request request = bulidRequestForPostByForm(url, parms);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }
    /**
     * 对外提供的Post方法访问
     * @param url
     * @param json: 提交内容为json数据
     * @param callBack
     */
    public void PostWithJson(String url, String json, MyCallBack callBack) {
        /**
         * 通过url和POST方式构建Request
         */
        Request request = bulidRequestForPostByJson(url, json);
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack);
    }
    /**
     * POST方式构建Request {json}
     * @param url
     * @param json
     * @return
     */
    private Request bulidRequestForPostByJson(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }
    /**
     * POST方式构建Request {Form}
     * @param url
     * @param parms
     * @return
     */
    private Request bulidRequestForPostByForm(String url, Map<String, String> parms) {
        FormBody.Builder builder = new FormBody.Builder();
        if (parms != null) {
            for (Map.Entry<String, String> entry :
                    parms.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody body = builder.build();
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }
    /**
     * GET方式构建Request
     * @param url
     * @return
     */
    private Request bulidRequestForGet(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }
    private void requestNetWork(Request request, MyCallBack callBack) {
        /**
         * 处理连网逻辑，此处只处理异步操作enqueue
         */
        callBack.onLoadingBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(request, e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response);
                } else {
                    callBack.onError(response);
                }
            }
        });
    }

    public interface MyCallBack {
        void onLoadingBefore(Request request);
        void onSuccess(Response response);
        void onFailure(Request request, Exception e);
        void onError(Response response);
    }
}
