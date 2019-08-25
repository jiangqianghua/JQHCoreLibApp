package com.xy.updaterapplib.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpNetManager implements INetManager {

    private static OkHttpClient sOkHttpClient;

    private static Handler sHandlet = new Handler(Looper.getMainLooper());

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        sOkHttpClient = builder.build();
    }

    @Override
    public void get(String url, final INetCallBack callback, Object tag) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().tag(tag).build();
        Call call = sOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sHandlet.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String msg = response.body().string();
                    sHandlet.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.success(msg);
                        }
                    });
                }catch (Throwable e){
                    e.printStackTrace();
                    callback.failed(e);
                }
            }
        });

    }
    //  下载
    @Override
    public void download(String url, final File targetFile, final INetDownloadCallBack callback, Object tag) {
        if(!targetFile.exists()){
            targetFile.getParentFile().mkdirs();
        }
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().tag(tag).build();
        Call call = sOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                sHandlet.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                InputStream is = null;
                OutputStream os = null;
                try {
                    is = response.body().byteStream();
                    os = new FileOutputStream(targetFile);

                    final long totallen = response.body().contentLength();
                    byte[] buffer = new byte[8 * 1024];
                    long curLen = 0;

                    int bufferLen = 0;

                    while (!call.isCanceled() && (bufferLen = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bufferLen);
                        os.flush();
                        curLen += bufferLen;
                        final long finalCurLen = curLen;
                        sHandlet.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.progress((int) (finalCurLen * 1.0f / totallen * 100));
                            }
                        });
                    }
                    if(call.isCanceled()){
                        return;
                    }
                    try {
                        // 设置不仅仅是拥有着可执行和读取和写入
                        targetFile.setExecutable(true, false);
                        targetFile.setReadable(true, false);
                        targetFile.setWritable(true, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    sHandlet.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.success(targetFile);
                        }
                    });
                }catch (final Throwable e){
                    if(call.isCanceled()){
                        return;
                    }
                    e.printStackTrace();
                    sHandlet.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.failed(e);
                        }
                    });
                }finally {
                    if(is != null){
                        is.close();
                    }
                    if(os != null){
                        os.close();
                    }
                }
            }
        });
    }

    @Override
    public void cancel(Object tag) {
        // 获取正在下载的call
        List<Call> queuedCalls = sOkHttpClient.dispatcher().queuedCalls();
        if(queuedCalls != null){
            for(Call call : queuedCalls){
                if(tag.equals(call.request().tag())){
                    Log.d("OkHttpNetManager","请求被取消");
                    call.cancel();
                }
            }
        }

        List<Call> runningCalls = sOkHttpClient.dispatcher().runningCalls();
        if(runningCalls != null){
            for(Call call : runningCalls){
                if(tag.equals(call.request().tag())){
                    Log.d("OkHttpNetManager","请求被取消");
                    call.cancel();
                }
            }
        }
    }
}
