package com.jqh.record;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.view.TextureView;
import android.view.View;

import com.jqh.liblive.R;

import java.io.File;

public class RecordMainActivity extends AppCompatActivity implements Camera.PreviewCallback {

    private TextureView textureView;

    private CameraHelper cameraHelper;

    private VideoCodec videoCodec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_main);
        textureView = findViewById(R.id.surface_view);

        cameraHelper = new CameraHelper(960, 720);
        cameraHelper.setmPreviewCallback(this);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                cameraHelper.startPreview(surfaceTexture);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                cameraHelper.stopPreview();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });

        videoCodec = new VideoCodec();

    }

    public void startRecord(View view){
        String path = this.getExternalCacheDir().getPath();
        videoCodec.startRecoding(path +"/a.mp4", cameraHelper.getWidth(), cameraHelper.getHeight(),0);
    }

    public void stopRecord(View view){
        videoCodec.stopRecording();
    }

    public void switchCamera(View view){
        cameraHelper.switchCamera();
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        videoCodec.queueEncode(bytes);
    }
}
