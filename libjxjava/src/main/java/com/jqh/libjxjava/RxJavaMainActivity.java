package com.jqh.libjxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaMainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private AppCompatImageView imageView;

    private final String PATH = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fa4.att.hudong.com%2F27%2F67%2F01300000921826141299672233506.jpg&refer=http%3A%2F%2Fa4.att.hudong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1612845177&t=84a8ed30079756a9e80809166c6938fa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_main);

        imageView = findViewById(R.id.image_view);
    }

    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Bitmap bitmap = (Bitmap) message.obj;
            imageView.setImageBitmap(bitmap);
            progressDialog.dismiss();
            return false;
        }
    });
    // 不使用RXJava
    public void normaldownLoadClick(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("download run ...");
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    URL url = new URL(PATH);
                    URLConnection urlConnection = url.openConnection();
                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                    httpURLConnection.setConnectTimeout(5000);;
                    int responseCode = httpURLConnection.getResponseCode();
                    if (HttpURLConnection.HTTP_OK == responseCode) {
                        Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                        Message message = handler.obtainMessage();
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void rxJavaldownLoadClick(View view) {
        // 找起点和终点 把 String --- 下载----bitmap
        // 2
        Observable.just(PATH) // 事件源
                // 3
                // String 上游的类型path，  bitmap是下游要转化的类型
                .map(new Function<String, Bitmap>(){
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        // 下载操作
                        URL url = new URL(PATH);
                        URLConnection urlConnection = url.openConnection();
                        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                        httpURLConnection.setConnectTimeout(5000);;
                        int responseCode = httpURLConnection.getResponseCode();
                        Bitmap bitmap = null;
                        if (HttpURLConnection.HTTP_OK == responseCode) {
                            bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                        }
                        return bitmap;
                    }
                }).map(new Function<Bitmap, Bitmap>() {
                    // 可以给图片添加水印等操作
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        Paint paint = new Paint();
                        paint.setColor(Color.RED);
                        paint.setTextSize(88);
                        bitmap = drawTextToBitmap(bitmap, "我爱中国", paint, 100, 100);
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io()) // 给上游下载逻辑分配异步线程
                .observeOn(AndroidSchedulers.mainThread()) // 给下游更新界面逻辑分配主线程
                .subscribe(
                new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // 1
                        progressDialog = new ProgressDialog(RxJavaMainActivity.this);
                        progressDialog.setTitle("download run ...");
                        progressDialog.show();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) { // 从上游分发下来的事件
                        // 4
                        imageView.setImageBitmap(bitmap);

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 执行onError后，不会再执行onComplete
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {  // 终点
                        // 5
                        progressDialog.dismiss();
                    }
                }
        );


    }


    private final Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();
        paint.setDither(true); // 获取清晰图像采样
        paint.setFilterBitmap(true); // 过滤
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    public void otherUseClick(View view) {

    }
}
