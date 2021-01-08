package com.jqh.livescreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

public class ScreenLive implements  Runnable {

    private String url;

    private MediaProjectionManager mediaProjectionManager;

    private MediaProjection mediaProjection;
    static {
        System.loadLibrary("native-lib");
    }

    private void startLive(Activity activity, String url) {
        this.url = url;
        mediaProjectionManager = (MediaProjectionManager)activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent captureIntent = mediaProjectionManager.createScreenCaptureIntent();
        activity.startActivityForResult(captureIntent, 100);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && requestCode == Activity.RESULT_OK) {
            //  拿到屏幕录制流
            mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);

            CachedThreadPool.executorService.execute(this);

        }
    }

    @Override
    public void run() {

    }
}
